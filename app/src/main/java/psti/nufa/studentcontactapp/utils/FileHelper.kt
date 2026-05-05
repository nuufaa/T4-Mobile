package psti.nufa.studentcontactapp.utils

import android.content.Context
import java.io.File

object FileHelper {

    fun saveNote(context: Context, nim: String, content: String) {
        val file = File(context.filesDir, "note_$nim.txt")
        file.writeText(content)
    }

    fun loadNote(context: Context, nim: String): String {
        val file = File(context.filesDir, "note_$nim.txt")
        return if (file.exists()) file.readText() else ""
    }

    fun isNoteExists(context: Context, nim: String): Boolean {
        val file = File(context.filesDir, "note_$nim.txt")
        return file.exists()
    }

    fun deleteNote(context: Context, nim: String) {
        val file = File(context.filesDir, "note_$nim.txt")
        if (file.exists()) file.delete()
    }

    fun getSize(context: Context, nim: String): Long {
        val file = File(context.filesDir, "note_$nim.txt")
        return if (file.exists()) file.length() else 0
    }
}