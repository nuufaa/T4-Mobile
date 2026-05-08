package psti.nufa.studentcontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import psti.nufa.studentcontactapp.database.entity.StudentEntity
import kotlin.math.abs

class StudentAdapter(
    private val onEditClick: (StudentEntity) -> Unit,
    private val onItemClick: (StudentEntity) -> Unit,
    private val onDeleteClick: (StudentEntity) -> Unit
) : ListAdapter<StudentEntity, StudentAdapter.StudentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student, onEditClick, onDeleteClick, onItemClick)
    }

    fun getStudentAt(position: Int): StudentEntity {
        return getItem(position)
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvNim: TextView = itemView.findViewById(R.id.tvNim)
        private val tvProdi: TextView = itemView.findViewById(R.id.tvProdi)
        private val btnEdit: TextView = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: TextView = itemView.findViewById(R.id.btnDelete)

        fun bind(
            student: StudentEntity,
            onEdit: (StudentEntity) -> Unit,
            onDelete: (StudentEntity) -> Unit,
            onItemClick: (StudentEntity) -> Unit
        ) {
            tvName.text = student.name
            tvNim.text = "NIM: ${student.nim}"
            tvProdi.text = "Prodi: ${student.prodi}"
            tvAvatar.text = student.name.take(1).uppercase()

            val pinkColors = intArrayOf(
                0xFFF48FB1.toInt(),
                0xFFC48B9F.toInt(),
                0xFFF06292.toInt(),
                0xFFF8BBD0.toInt()
            )

            val color = pinkColors[abs(student.id % pinkColors.size)]
            tvAvatar.background?.setTint(color)

            itemView.setOnClickListener { onItemClick(student) }
            btnEdit.setOnClickListener { onEdit(student) }
            btnDelete.setOnClickListener { onDelete(student) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<StudentEntity>() {
        override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity) = oldItem == newItem
    }
}