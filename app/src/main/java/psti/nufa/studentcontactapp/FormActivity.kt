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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        dao = AppDatabase.getInstance(this).studentDao()

        val etName = findViewById<EditText>(R.id.etName)
        val etNim = findViewById<EditText>(R.id.etNim)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSemester = findViewById<EditText>(R.id.etSemester)
        val etProdi = findViewById<EditText>(R.id.etProdi)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            val student = StudentEntity(
                name = etName.text.toString(),
                nim = etNim.text.toString(),
                email = etEmail.text.toString(),
                semester = etSemester.text.toString().toInt(),
                prodi = etProdi.text.toString()
            )

            lifecycleScope.launch {
                dao.insert(student)
                finish()
            }
        }
    }
}