package Fragments.MainPageFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.Database.Note
import com.example.homework3.R
import kotlinx.android.synthetic.main.recent_note.view.*

class RecentNoteListAdapter: RecyclerView.Adapter<RecentNoteListAdapter.ViewHolder>() {

    private var userList = emptyList<Note>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recent_note, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.fragment_main_page_tvTitle.text = currentItem.title
        holder.itemView.fragment_main_page_tvContent.text = currentItem.content
        holder.itemView.fragment_main_page_tvDate.text = currentItem.date
        holder.itemView.setOnClickListener {
            val action = MainPageFragmentDirections.actionMainPageFragmentToEditNoteFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<Note>){
        this.userList = user
        notifyDataSetChanged()
    }
}