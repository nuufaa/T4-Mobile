package psti.nufa.studentcontactapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import psti.nufa.studentcontactapp.databinding.ActivityDetailBinding
import psti.nufa.studentcontactapp.utils.FileHelper

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var currentStudentNim: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentStudentNim = intent.getStringExtra("student_nim")
        val studentName = intent.getStringExtra("student_name") ?: "Nama Mahasiswa"
        val studentProdi = intent.getStringExtra("student_prodi") ?: "T. Informatika"

        binding.tvDetailName.text = studentName
        binding.tvDetailNimProdi.text = "$currentStudentNim • $studentProdi"

        val initials = studentName.split(" ")
            .take(2)
            .map { it.first() }
            .joinToString("")
            .uppercase()
        binding.tvDetailAvatar.text = initials

        binding.btnBack.setOnClickListener {
            finish()
        }

        loadExistingNote()

        binding.btnSaveNote.setOnClickListener {
            val noteContent = binding.etNote.text.toString().trim()
            if (currentStudentNim != null && noteContent.isNotEmpty()) {
                FileHelper.saveNote(this, currentStudentNim!!, noteContent)
                updateStatus()
                Toast.makeText(this, "Catatan berhasil disimpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Catatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLoadNote.setOnClickListener {
            loadExistingNote()
            Toast.makeText(this, "Catatan dimuat ulang", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadExistingNote() {
        currentStudentNim?.let { nim ->
            if (FileHelper.isNoteExists(this, nim)) {
                val note = FileHelper.loadNote(this, nim)
                binding.etNote.setText(note)
                updateStatus()
            } else {
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = "Status: Belum ada catatan"
            }
        }
    }

    private fun updateStatus() {
        currentStudentNim?.let { nim ->
            val size = FileHelper.getSize(this, nim)
            if (size > 0) {
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = "✓ Tersimpan ($size bytes)"
                binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50")) // Hijau
            } else {
                binding.tvStatus.text = "Belum ada catatan"
                binding.tvStatus.setTextColor(android.graphics.Color.GRAY)
            }
        }
    }
}