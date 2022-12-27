package com.example.homework3

import Fragments.NotesListFragment.NotesListFragmentDirections
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.Database.Note
import kotlinx.android.synthetic.main.note.view.*
import kotlinx.android.synthetic.main.recent_note.view.*

class NoteListAdapter: RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private var userList = emptyList<Note>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.note_title.text = currentItem.title
        holder.itemView.note_content.text = currentItem.content
        holder.itemView.note_date.text = currentItem.date
        holder.itemView.setOnClickListener {
            val action = NotesListFragmentDirections.actionNotesListFragmentToEditNoteFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<Note>){
        this.userList = user
        notifyDataSetChanged()
    }
}