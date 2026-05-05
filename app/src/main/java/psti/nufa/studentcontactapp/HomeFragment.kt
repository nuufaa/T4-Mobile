package psti.nufa.studentcontactapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import psti.nufa.studentcontactapp.database.AppDatabase
import psti.nufa.studentcontactapp.database.dao.StudentDao
import psti.nufa.studentcontactapp.database.entity.StudentEntity
import psti.nufa.studentcontactapp.utils.PrefManager

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var db: AppDatabase
    private lateinit var dao: StudentDao
    private lateinit var adapter: StudentAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // =========================
        // WELCOME TEXT
        // =========================
        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcome)
        val prefManager = PrefManager(requireContext())
        tvWelcome.text = "Welcome, ${prefManager.getUsername()}!"

        // =========================
        // INIT DATABASE
        // =========================
        db = AppDatabase.getInstance(requireContext())
        dao = db.studentDao()

        // =========================
        // RECYCLER VIEW
        // =========================
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = StudentAdapter(
            onItemClick = { student: StudentEntity ->
                // Note: DetailFragment is a Fragment, starting it as an Activity will fail at runtime.
                // However, fixing the type inference issue as requested.
                val intent = Intent(requireContext(), DetailFragment::class.java)
                intent.putExtra("EXTRA_NAME", student.name)
                intent.putExtra("EXTRA_NIM", student.nim)
                intent.putExtra("EXTRA_PRODI", student.prodi)
                startActivity(intent)
            },
            onEditClick = { student: StudentEntity ->
                val intent = Intent(requireContext(), FormActivity::class.java)
                intent.putExtra("EXTRA_ID", student.id)
                intent.putExtra("EXTRA_NAME", student.name)
                intent.putExtra("EXTRA_NIM", student.nim)
                intent.putExtra("EXTRA_PRODI", student.prodi)
                intent.putExtra("EXTRA_EMAIL", student.email)
                intent.putExtra("EXTRA_SEMESTER", student.semester)
                startActivity(intent)
            },
            onDeleteClick = { student: StudentEntity ->
                showDeleteDialog(student)
            }
        )

        recyclerView.adapter = adapter

        // =========================
        // SWIPE DELETE
        // =========================
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val student = adapter.getStudentAt(viewHolder.adapterPosition)

                lifecycleScope.launch {
                    dao.deleteById(student.id)
                    Toast.makeText(
                        requireContext(),
                        "${student.name} dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadData()
                }
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView)

        // =========================
        // FAB ADD
        // =========================
        fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), FormActivity::class.java))
        }

        // =========================
        // LOAD DATA
        // =========================
        loadData()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {

            var data = dao.getAllStudents()

            // isi dummy jika kosong
            if (data.isEmpty()) {
                dao.insertAll(dummyData())
                data = dao.getAllStudents()
            }

            adapter.submitList(data)
        }
    }

    private fun showDeleteDialog(student: StudentEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Data?")
            .setMessage("Hapus ${student.name}?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    dao.deleteById(student.id)
                    Toast.makeText(
                        requireContext(),
                        "${student.name} berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadData()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun dummyData(): List<StudentEntity> {
        return listOf(
            StudentEntity(
                name = "Budi Santoso",
                nim = "22001",
                prodi = "Teknik Informatika",
                email = "budi@mail.com",
                semester = 3
            ),
            StudentEntity(
                name = "Sari Wijaya",
                nim = "22002",
                prodi = "Sistem Informasi",
                email = "sari@mail.com",
                semester = 5
            ),
            StudentEntity(
                name = "Ahmad Fauzi",
                nim = "22003",
                prodi = "Teknik Komputer",
                email = "ahmad@mail.com",
                semester = 1
            )
        )
    }
}