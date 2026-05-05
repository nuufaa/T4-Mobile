package psti.nufa.studentcontactapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import psti.nufa.studentcontactapp.database.AppDatabase
import psti.nufa.studentcontactapp.database.entity.StudentEntity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        db = AppDatabase.getInstance(this)

        val etName = findViewById<EditText>(R.id.etName)
        val etNim = findViewById<EditText>(R.id.etNim)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etProdi = findViewById<EditText>(R.id.etProdi)
        val etSemester = findViewById<EditText>(R.id.etSemester)

        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            val student = StudentEntity(
                name = etName.text.toString(),
                nim = etNim.text.toString(),
                email = etEmail.text.toString(),
                prodi = etProdi.text.toString(),
                semester = etSemester.text.toString().toIntOrNull() ?: 1
            )

            lifecycleScope.launch {
                db.studentDao().insert(student)
                finish() // balik ke home
            }
        }
    }
}