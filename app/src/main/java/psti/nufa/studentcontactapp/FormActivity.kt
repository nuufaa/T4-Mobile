package psti.nufa.studentcontactapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import psti.nufa.studentcontactapp.database.AppDatabase
import psti.nufa.studentcontactapp.database.dao.StudentDao
import psti.nufa.studentcontactapp.database.entity.StudentEntity

class FormActivity : AppCompatActivity() {
    private lateinit var dao: StudentDao
    private var studentId: Int = 0
    private var isEditMode: Boolean = false
    private val prodiOptions = arrayOf("Teknik Informatika", "Sistem Informasi", "Teknik Komputer", "Teknik Elektro")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        dao = AppDatabase.getInstance(this).studentDao()

        val etName = findViewById<EditText>(R.id.etName)
        val etNim = findViewById<EditText>(R.id.etNim)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSemester = findViewById<EditText>(R.id.etSemester)
        val spinnerProdi = findViewById<Spinner>(R.id.spinnerProdi)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val tvFormTitle = findViewById<TextView>(R.id.tvFormTitle)

        val adapterProdi = ArrayAdapter(this, android.R.layout.simple_spinner_item, prodiOptions)
        adapterProdi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProdi.adapter = adapterProdi

        btnBack.setOnClickListener {
            finish()
        }

        studentId = intent.getIntExtra("EXTRA_ID", 0)
        if (studentId != 0) {
            isEditMode = true
            tvFormTitle.text = "Edit Mahasiswa" // Judul dinamis
            btnSave.text = "UPDATE DATA"

            etName.setText(intent.getStringExtra("EXTRA_NAME"))
            etNim.setText(intent.getStringExtra("EXTRA_NIM"))
            etEmail.setText(intent.getStringExtra("EXTRA_EMAIL"))
            etSemester.setText(intent.getIntExtra("EXTRA_SEMESTER", 1).toString())

            val pos = prodiOptions.indexOf(intent.getStringExtra("EXTRA_PRODI"))
            if (pos >= 0) spinnerProdi.setSelection(pos)
        } else {
            tvFormTitle.text = "Tambah Mahasiswa" // Judul default
            btnSave.text = "SIMPAN"
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val nim = etNim.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val semStr = etSemester.text.toString().trim()
            val prodi = spinnerProdi.selectedItem.toString()

            if (name.isEmpty()) {
                etName.error = "Nama wajib diisi"
                etName.requestFocus()
                return@setOnClickListener
            }
            if (nim.isEmpty()) {
                etNim.error = "NIM wajib diisi"
                etNim.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Format email tidak valid"
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if (semStr.isEmpty()) {
                etSemester.error = "Semester wajib diisi"
                etSemester.requestFocus()
                return@setOnClickListener
            }

            val student = StudentEntity(
                id = if (isEditMode) studentId else 0,
                name = name,
                nim = nim,
                email = email,
                semester = semStr.toInt(),
                prodi = prodi
            )

            lifecycleScope.launch {
                try {
                    if (isEditMode) {
                        dao.update(student)
                        Toast.makeText(this@FormActivity, "Data diperbarui", Toast.LENGTH_SHORT).show()
                    } else {
                        dao.insert(student)
                        Toast.makeText(this@FormActivity, "Data ditambahkan", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@FormActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}