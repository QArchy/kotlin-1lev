package Fragments.EditNoteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homework3.Database.Note
import com.example.homework3.Database.NoteViewModel
import com.example.homework3.R
import kotlinx.android.synthetic.main.fragment_edit_note.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditNoteFragment : Fragment() {

    private lateinit var mNoteViewModel : NoteViewModel
    private val args by navArgs<EditNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_edit_note, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        view.findViewById<ImageView>(R.id.fragment_edit_note_tvSave).setOnClickListener {
            insertDataToDatabase()
        }

        if (args.currentItem != null) {
            view.findViewById<EditText>(R.id.fragment_edit_note_etNotebookName).setText(args.currentItem!!.notebook)
            view.findViewById<EditText>(R.id.fragment_edit_note_etNoteTitle).setText(args.currentItem!!.title)
            view.findViewById<EditText>(R.id.fragment_edit_note_etNoteContent).setText(args.currentItem!!.content)
        }

        return view
    }

    private fun insertDataToDatabase() {
        val notebookName = fragment_edit_note_etNotebookName.text.toString()
        val noteTitle = fragment_edit_note_etNoteTitle.text.toString()
        val noteContent = fragment_edit_note_etNoteContent.text.toString()

        if (inputCheck(notebookName, noteTitle, noteContent)) {
            val newNote = Note(0, notebookName, noteTitle, noteContent,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
            )

            if (args.currentItem != null) {
                val change = somethingChanged(newNote)
                if (change.first || change.second || change.third) {
                    if (change.first) {
                        mNoteViewModel.alterNoteNotebook(args.currentItem!!.id, newNote.notebook)
                    }
                    if (change.second) {
                        mNoteViewModel.alterNoteTitle(args.currentItem!!.id, newNote.title)
                    }
                    if (change.third) {
                        mNoteViewModel.alterNoteContent(args.currentItem!!.id, newNote.content)
                    }

                    mNoteViewModel.alterNoteDate(args.currentItem!!.id,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                    )

                    Toast.makeText(requireContext(), "Successfully changed", Toast.LENGTH_SHORT).show()
                }
            } else {
                mNoteViewModel.addNote(newNote)
                Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
            }

            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun somethingChanged(newNote: Note): Triple<Boolean, Boolean, Boolean> {
        var bool = Triple(false, false,false)
        if (newNote.notebook != args.currentItem!!.notebook)
            bool = bool.copy(first = true)
        if (newNote.title != args.currentItem!!.title)
            bool = bool.copy(second = true)
        if (newNote.content != args.currentItem!!.content)
            bool = bool.copy(third = true)
        return bool
    }

    private fun inputCheck(notebookName: String, noteTitle: String, noteContent: String): Boolean {
        return notebookName.isNotEmpty() && noteTitle.isNotEmpty() && noteContent.isNotEmpty()
    }

    companion object {
        @JvmStatic
        fun newInstance(): EditNoteFragment {
            return EditNoteFragment()
        }
    }
}