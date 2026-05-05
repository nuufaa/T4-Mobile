package psti.nufa.studentcontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import psti.nufa.studentcontactapp.database.entity.StudentEntity

class StudentAdapter(
    private val onEditClick: (StudentEntity) -> Unit,
    private val onItemClick: (StudentEntity) -> Unit,
    private val onDeleteClick: (StudentEntity) -> Unit
) :
    ListAdapter<StudentEntity, StudentAdapter.StudentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
        holder.itemView.setOnClickListener { onItemClick(student) }
        holder.itemView.setOnLongClickListener {
            onEditClick(student)
            true
        }
    }

    fun getStudentAt(position: Int): StudentEntity {
        return getItem(position)
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvNim: TextView = itemView.findViewById(R.id.tvNim)
        private val tvProdi: TextView = itemView.findViewById(R.id.tvProdi)

        fun bind(student: StudentEntity) {

            tvName.text = student.name
            tvNim.text = "NIM: ${student.nim}"
            tvProdi.text = "Prodi: ${student.prodi}"

            // ambil inisial nama
            val initials = student.name
                .split(" ")
                .take(2)
                .joinToString("") { it.first().toString() }
                .uppercase()

            tvAvatar.text = initials

            // warna avatar (lebih stabil pakai id)
            val color = when (student.id % 3) {
                0 -> 0xFF3F51B5.toInt()
                1 -> 0xFFE91E63.toInt()
                else -> 0xFF009688.toInt()
            }

            tvAvatar.setBackgroundColor(color)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<StudentEntity>() {
        override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
            return oldItem == newItem
        }
    }
}