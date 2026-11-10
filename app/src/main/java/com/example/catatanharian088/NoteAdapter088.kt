package com.example.catatanharian088

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catatanharian088.data.Note088

class NoteAdapter088(
    private val onEditClick: (Note088) -> Unit,
    private val onDeleteClick: (Note088) -> Unit
) : ListAdapter<Note088, NoteAdapter088.NoteViewHolder>(DIFF_CALLBACK) {

    class NoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView =
            itemView.findViewById(R.id.tvNoteTitle088)

        val tvDate: TextView =
            itemView.findViewById(R.id.tvNoteDate088)

        val tvContent: TextView =
            itemView.findViewById(R.id.tvNoteContent088)

        val btnEdit: Button =
            itemView.findViewById(R.id.btnEditNote088)

        val btnDelete: Button =
            itemView.findViewById(R.id.btnDeleteNote088)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note_088, parent, false)

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {

        val note = getItem(position)

        holder.tvTitle.text = note.title
        holder.tvDate.text = note.date
        holder.tvContent.text = note.content

        holder.btnEdit.setOnClickListener {
            onEditClick(note)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(note)
        }
    }

    companion object {

        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Note088>() {

                override fun areItemsTheSame(
                    oldItem: Note088,
                    newItem: Note088
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Note088,
                    newItem: Note088
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}