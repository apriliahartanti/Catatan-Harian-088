package com.example.catatanharian088

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.catatanharian088.data.AppDatabase088
import com.example.catatanharian088.data.Note088
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteFormActivity088 : AppCompatActivity() {

    private lateinit var database: AppDatabase088

    private var noteId = -1
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_form_088)

        database = AppDatabase088.getDatabase(this)

        username = intent.getStringExtra("USERNAME") ?: ""
        noteId = intent.getIntExtra("NOTE_ID", -1)

        val tvFormTitle =
            findViewById<TextView>(R.id.tvFormTitle088)

        val etNoteTitle =
            findViewById<EditText>(R.id.etNoteTitle088)

        val etNoteContent =
            findViewById<EditText>(R.id.etNoteContent088)

        val btnSaveNote =
            findViewById<Button>(R.id.btnSaveNote088)

        if (noteId != -1) {
            tvFormTitle.text = "Edit Catatan"

            lifecycleScope.launch {
                val note = database.noteDao().getNoteById(noteId)

                if (note != null && note.username == username) {
                    etNoteTitle.setText(note.title)
                    etNoteContent.setText(note.content)
                } else {
                    Toast.makeText(
                        this@NoteFormActivity088,
                        "Catatan tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }

        btnSaveNote.setOnClickListener {

            val title = etNoteTitle.text.toString().trim()
            val content = etNoteContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(
                    this,
                    "Judul dan isi catatan harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    if (noteId == -1) {

                        val currentDate = SimpleDateFormat(
                            "dd/MM/yyyy HH:mm",
                            Locale.getDefault()
                        ).format(Date())

                        val newNote = Note088(
                            username = username,
                            title = title,
                            content = content,
                            date = currentDate
                        )

                        database.noteDao().insertNote(newNote)

                        Toast.makeText(
                            this@NoteFormActivity088,
                            "Catatan berhasil disimpan",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        val oldNote =
                            database.noteDao().getNoteById(noteId)

                        if (oldNote == null || oldNote.username != username) {
                            Toast.makeText(
                                this@NoteFormActivity088,
                                "Catatan tidak ditemukan",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }

                        val updatedNote = oldNote.copy(
                            title = title,
                            content = content
                        )

                        database.noteDao().updateNote(updatedNote)

                        Toast.makeText(
                            this@NoteFormActivity088,
                            "Catatan berhasil diperbarui",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    finish()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@NoteFormActivity088,
                        "Gagal menyimpan catatan: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}