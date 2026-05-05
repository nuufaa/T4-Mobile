package psti.nufa.studentcontactapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import psti.nufa.studentcontactapp.utils.FileHelper

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var nim: String = "2024001" // nanti bisa dari klik Home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etNote = view.findViewById<EditText>(R.id.etNote)
        val btnSave = view.findViewById<Button>(R.id.btnSaveNote)
        val btnLoad = view.findViewById<Button>(R.id.btnLoadNote)
        val tvStatus = view.findViewById<TextView>(R.id.tvStatus)

        // ✅ AUTO LOAD SAAT MASUK
        if (FileHelper.isNoteExists(requireContext(), nim)) {
            val content = FileHelper.loadNote(requireContext(), nim)
            etNote.setText(content)

            val size = FileHelper.getSize(requireContext(), nim)
            tvStatus.text = "✓ Tersimpan (${size} bytes)"
        } else {
            tvStatus.text = "Belum ada catatan"
        }

        // ✅ SIMPAN
        btnSave.setOnClickListener {
            val content = etNote.text.toString()

            FileHelper.saveNote(requireContext(), nim, content)

            val size = FileHelper.getSize(requireContext(), nim)
            tvStatus.text = "✓ Tersimpan (${size} bytes)"
        }

        // ✅ MUAT
        btnLoad.setOnClickListener {
            val content = FileHelper.loadNote(requireContext(), nim)
            etNote.setText(content)

            val size = FileHelper.getSize(requireContext(), nim)
            tvStatus.text = "✓ Tersimpan (${size} bytes)"
        }
    }
}