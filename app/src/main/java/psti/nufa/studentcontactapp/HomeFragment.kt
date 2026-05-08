package psti.nufa.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var db: AppDatabase
    private lateinit var dao: StudentDao
    private lateinit var adapter: StudentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())
        dao = db.studentDao()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)

        adapter = StudentAdapter(
            onItemClick = { student ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("student_nim", student.nim)
                intent.putExtra("student_name", student.name)
                intent.putExtra("student_prodi", student.prodi)
                startActivity(intent)
            },
            onEditClick = { student ->
                val intent = Intent(requireContext(), FormActivity::class.java)
                intent.putExtra("EXTRA_ID", student.id)
                intent.putExtra("EXTRA_NAME", student.name)
                intent.putExtra("EXTRA_NIM", student.nim)
                intent.putExtra("EXTRA_PRODI", student.prodi)
                intent.putExtra("EXTRA_EMAIL", student.email)
                intent.putExtra("EXTRA_SEMESTER", student.semester)
                startActivity(intent)
            },
            onDeleteClick = { student ->
                showDeleteDialog(student)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        setupSwipeDelete(recyclerView)

        fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), FormActivity::class.java)
            startActivity(intent)
        }

        loadData()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            var data = dao.getAllStudents()

            // Isi dengan data awal jika masih kosong
            if (data.isEmpty()) {
                dao.insertAll(dummyData())
                data = dao.getAllStudents()
            }

            adapter.submitList(data)
        }
    }

    private fun showDeleteDialog(student: StudentEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Data")
            .setMessage("Yakin ingin menghapus ${student.name}?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    dao.deleteById(student.id)
                    Toast.makeText(requireContext(), "Data dihapus", Toast.LENGTH_SHORT).show()
                    loadData()
                }
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
                loadData()
            }
            .show()
    }

    private fun setupSwipeDelete(recyclerView: RecyclerView) {
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
                val position = viewHolder.adapterPosition
                val student = adapter.getStudentAt(position)
                showDeleteDialog(student)
            }
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView)
    }

    private fun dummyData(): List<StudentEntity> {
        return listOf(
            StudentEntity(name = "Ahmad Fauzi", nim = "2024001", prodi = "Teknik Informatika", email = "ahmad@mail.com", semester = 3),
            StudentEntity(name = "Budi Santoso", nim = "2024002", prodi = "Sistem Informasi", email = "budi@mail.com", semester = 5),
            StudentEntity(name = "Clara Wijaya", nim = "2024003", prodi = "Teknik Komputer", email = "clara@mail.com", semester = 1)
        )
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}