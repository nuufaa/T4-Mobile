package psti.nufa.studentcontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notes: List<Pair<String, String>>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNoteIcon: TextView = v.findViewById(R.id.tvNoteIcon)
        val tvNoteTitle: TextView = v.findViewById(R.id.tvNoteTitle)
        val tvNoteSize: TextView = v.findViewById(R.id.tvNoteSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val (rawFileName, size) = notes[position]

        val cleanName = rawFileName
            .replace("note_", "")
            .replace(".txt", "")

        holder.tvNoteTitle.text = "Catatan NIM: $cleanName"
        holder.tvNoteSize.text = "Ukuran file: $size"

        holder.tvNoteIcon.background?.setTint(android.graphics.Color.parseColor("#F48FB1"))
    }

    override fun getItemCount() = notes.size
}