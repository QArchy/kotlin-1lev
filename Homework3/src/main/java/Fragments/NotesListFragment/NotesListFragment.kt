package Fragments.NotesListFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.Database.NoteViewModel
import com.example.homework3.NoteListAdapter
import com.example.homework3.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotesListFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        val rv = view.findViewById<RecyclerView>(R.id.notes)
        rv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = NoteListAdapter()
        }

        mNoteViewModel.readAllData.observe(viewLifecycleOwner) { note ->
            viewLifecycleOwner.lifecycleScope.launch {
                delay(2000)
                (rv.adapter as NoteListAdapter).setData(note)

                view.findViewById<TextView>(R.id.notes_quantity).text =
                    "${(rv.adapter as NoteListAdapter).itemCount} Notes"
                view.findViewById<ProgressBar>(R.id.progressBarNotes).visibility = View.INVISIBLE
                if ((rv.adapter as NoteListAdapter).itemCount == 0) {
                    view.findViewById<TextView>(R.id.emptyNotes).visibility = View.VISIBLE
                } else {
                    view.findViewById<TextView>(R.id.emptyNotes).visibility = View.INVISIBLE
                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): NotesListFragment {
            return NotesListFragment()
        }
    }
}