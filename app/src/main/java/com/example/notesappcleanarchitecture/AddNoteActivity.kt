package com.example.notesappcleanarchitecture

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesappcleanarchitecture.databinding.ActivityAddNoteBinding
import com.example.notesappcleanarchitecture.models.Note
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {

    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }

    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            old_note = intent.getSerializableExtra("current_note") as Note
            binding.editTextTitle.setText(old_note.title)
            binding.editTextNote.setText(old_note.note)
            isUpdated = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imageViewCheck.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val note_description = binding.editTextNote.text.toString()

            if (title.isNotEmpty() || note_description.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdated) {
                    note = Note(
                        old_note.id, title, note_description, formatter.format(Date())
                    )
                } else {
                    note = Note(
                        null, title, note_description, formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddNoteActivity, "Please enter some data", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }
        binding.imageViewBackArrow.setOnClickListener {
            onBackPressed()
        }
    }
}