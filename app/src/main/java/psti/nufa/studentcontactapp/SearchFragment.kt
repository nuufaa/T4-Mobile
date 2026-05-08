package psti.nufa.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import psti.nufa.studentcontactapp.database.AppDatabase
import psti.nufa.studentcontactapp.database.dao.StudentDao

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var adapter: StudentAdapter
    private lateinit var dao: StudentDao
    private val db by lazy { AppDatabase.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = db.studentDao()

        val rvSearch = view.findViewById<RecyclerView>(R.id.rvSearch)
        val searchView = view.findViewById<SearchView>(R.id.searchView)

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
                lifecycleScope.launch {
                    dao.deleteById(student.id)
                    searchData(searchView.query.toString())
                }
            }
        )

        rvSearch.adapter = adapter
        rvSearch.layoutManager = LinearLayoutManager(requireContext())

        searchData("")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                searchData(newText.orEmpty())
                return true
            }
        })
    }

    private fun searchData(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            val results = if (query.trim().isEmpty()) {
                dao.getAllStudents()
            } else {
                dao.searchStudents("%$query%")
            }
            adapter.submitList(results)
        }
    }
}