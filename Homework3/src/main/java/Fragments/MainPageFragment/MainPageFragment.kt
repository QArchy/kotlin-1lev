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
import com.example.homework3.Gif.GifsViewModel
import com.example.homework3.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainPageFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var mGifsViewModel: GifsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        mGifsViewModel = ViewModelProvider(this)[GifsViewModel::class.java]
//        for (i in 1..100)
//            mNoteViewModel.addNote(
//                Note(
//                    id = 0,
//                    notebook = "$i Notebook",
//                    title = "$i Title",
//                    content = "$i Content $i Content $i Content $i Content $i Content $i Content",
//                    date = LocalDateTime.now()
//                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
//                )
//            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)

        // set hello
        view.findViewById<TextView>(R.id.fragment_main_page_tvGoodAfternoon).text = getDayTime(LocalDateTime.now().hour)
        view.findViewById<TextView>(R.id.fragment_main_page_tvDate).text = getDate(LocalDateTime.now())

        val rv = view.findViewById<RecyclerView>(R.id.fragment_main_page_RV)
        val eText = view.findViewById<EditText>(R.id.fragment_main_page_etScratchPad)
        val overlay = view.findViewById<ConstraintLayout>(R.id.fragment_main_page_overlay)
        view.findViewById<ImageView>(R.id.fragment_main_page_btnThreeLines).setOnClickListener {
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
        view.findViewById<LinearLayout>(R.id.fragment_main_page_btnAdd).setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_editNoteFragment)
        }
        val notes = view.findViewById<TextView>(R.id.fragment_main_page_overlayNotes)
        notes.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_notesListFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set rv
        val rv = view.findViewById<RecyclerView>(R.id.fragment_main_page_RV)
        rv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = RecentNoteListAdapter()
        }

        mNoteViewModel.readAllData.observe(viewLifecycleOwner) { note ->
            viewLifecycleOwner.lifecycleScope.launch {
                delay(2000)
                (rv.adapter as RecentNoteListAdapter).setData(note)

                view.findViewById<ProgressBar>(R.id.fragment_main_page_progressBar).visibility = View.INVISIBLE
                if ((rv.adapter as RecentNoteListAdapter).itemCount == 0) {
                    view.findViewById<TextView>(R.id.fragment_main_page_emptyRecentNotes).visibility = View.VISIBLE
                } else {
                    view.findViewById<TextView>(R.id.fragment_main_page_emptyRecentNotes).visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val imageView = requireView().findViewById<ImageView>(R.id.fragment_main_page_ivBG)
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            val gif = mGifsViewModel.getGif()
            if (gif != null)
                Glide.with(imageView).load(gif.images.ogImage.url).into(imageView)
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