package Fragments.MainPageFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework3.Database.NoteViewModel
import com.example.homework3.Gif.GiphyViewModel
import com.example.homework3.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainPageFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var mGiphyViewModel: GiphyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)

        // set hello
        view.findViewById<TextView>(R.id.good_afternoon).text = getDayTime(LocalDateTime.now().hour)
        view.findViewById<TextView>(R.id.date).text = getDate(LocalDateTime.now())

        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewRecentNotes)
        val eText = view.findViewById<EditText>(R.id.editTextTextPersonName)
        val overlay = view.findViewById<ConstraintLayout>(R.id.overlay)
        view.findViewById<ImageView>(R.id.three_lines).setOnClickListener {
            if (overlay.isVisible) {
                overlay.visibility = View.INVISIBLE

                rv.visibility = View.VISIBLE
                eText.visibility = View.VISIBLE
            } else {
                overlay.visibility = View.VISIBLE

                rv.visibility = View.GONE
                eText.visibility = View.GONE
            }
        }
        view.findViewById<LinearLayout>(R.id.add).setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_editNoteFragment)
        }
        view.findViewById<TextView>(R.id.notes).setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_notesListFragment)
        }

        mGiphyViewModel = ViewModelProvider(this)[GiphyViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            val gif = mGiphyViewModel.getGif()
            if (gif != null)
                Glide.with(
                    view.findViewById<ImageView>(R.id.main_page_bg)
                ).load(gif.images.ogImage.url)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set rv
        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewRecentNotes)
        rv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = RecentNoteListAdapter()
        }

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        mNoteViewModel.readAllData.observe(viewLifecycleOwner) { note ->
            viewLifecycleOwner.lifecycleScope.launch {
                delay(2000)
                (rv.adapter as RecentNoteListAdapter).setData(note)

                view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                if ((rv.adapter as RecentNoteListAdapter).itemCount == 0) {
                    view.findViewById<TextView>(R.id.emptyRecentNotes).visibility = View.VISIBLE
                } else {
                    view.findViewById<TextView>(R.id.emptyRecentNotes).visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun getDayTime(hour: Int) =
        when (hour) {
            in 0..11 -> {
                "Good Morning, Username!"
            }
            in 12..15 -> {
                "Good Afternoon, Username!"
            }
            in 16..20 -> {
                "Good Evening, Username!"
            }
            in 21..23 -> {
                "Good Night, Username!"
            }
            else -> {
                "Good Day, Username!"
            }
        }

    private fun getDate(current: LocalDateTime): String {
        return current.dayOfWeek.name + ", " +
                current.month.name + " " +
                current.dayOfMonth.toString() + ", " +
                current.year.toString()
    }
}