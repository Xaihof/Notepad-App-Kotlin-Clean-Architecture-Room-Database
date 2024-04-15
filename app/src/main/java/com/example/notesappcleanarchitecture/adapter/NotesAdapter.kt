package com.example.notesappcleanarchitecture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappcleanarchitecture.R
import com.example.notesappcleanarchitecture.models.Note
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NotesClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.note_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = notesList.size

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        notesList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()
                    ?.contains(search.lowercase()) == true || item.note?.lowercase()
                    ?.contains(search.lowercase()) == true
            ) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }


    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.note_color_1)
        list.add(R.color.note_color_2)
        list.add(R.color.note_color_3)
        list.add(R.color.note_color_4)
        list.add(R.color.note_color_5)
        list.add(R.color.note_color_6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote = notesList[position]

        holder.textViewTitle.text = currentNote.title
        holder.textViewTitle.isSelected = true

        holder.textViewNote.text = currentNote.note

        holder.textViewDate.text = currentNote.date
        holder.textViewDate.isSelected = true

        holder.noteItemLayout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )

        holder.noteItemLayout.setOnClickListener {
            listener.onItemClicked(notesList[holder.adapterPosition])
        }

        holder.noteItemLayout.setOnLongClickListener {
            listener.onLongItemClicked(notesList[holder.adapterPosition], holder.noteItemLayout)
            true
        }

    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val noteItemLayout = itemView.findViewById<CardView>(R.id.noteItemLayout)
        val textViewTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
        val textViewNote = itemView.findViewById<TextView>(R.id.textViewNote)
        val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)

    }

    interface NotesClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }

}