package psti.nufa.studentcontactapp

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNotes = view.findViewById<RecyclerView>(R.id.rvNotes)
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        rvNotes.layoutManager = LinearLayoutManager(requireContext())

        val allFiles = requireContext().fileList()
        val noteFiles = allFiles.filter { it.startsWith("note_") }.map { fileName ->
            val file = File(requireContext().filesDir, fileName)
            Pair(fileName, "${file.length()} bytes")
        }

        rvNotes.adapter = NoteAdapter(noteFiles)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}