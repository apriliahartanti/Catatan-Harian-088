package com.example.catatanharian088

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catatanharian088.data.AppDatabase088
import com.example.catatanharian088.data.Note088
import kotlinx.coroutines.launch

class DashboardActivity088 : AppCompatActivity() {

    private lateinit var database: AppDatabase088
    private lateinit var noteAdapter: NoteAdapter088

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_088)

        database = AppDatabase088.getDatabase(this)

        username = intent.getStringExtra("USERNAME") ?: ""

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome088)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty088)
        val rvNotes = findViewById<RecyclerView>(R.id.rvNotes088)

        val btnAddNote = findViewById<Button>(R.id.btnAddNote088)
        val btnLogout = findViewById<Button>(R.id.btnLogout088)

        tvWelcome.text = "Halo, $username!"

        noteAdapter = NoteAdapter088(
            onEditClick = { note ->
                val intent = Intent(
                    this,
                    NoteFormActivity088::class.java
                )
                intent.putExtra("NOTE_ID", note.id)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            },

            onDeleteClick = { note ->
                showDeleteDialog(note)
            }
        )

        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = noteAdapter

        lifecycleScope.launch {
            database.noteDao()
                .getNotesByUser(username)
                .collect { notes ->

                    noteAdapter.submitList(notes)

                    if (notes.isEmpty()) {
                        tvEmpty.visibility = View.VISIBLE
                        rvNotes.visibility = View.GONE
                    } else {
                        tvEmpty.visibility = View.GONE
                        rvNotes.visibility = View.VISIBLE
                    }
                }
        }

        btnAddNote.setOnClickListener {
            val intent = Intent(
                this,
                NoteFormActivity088::class.java
            )
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Keluar")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    val intent = Intent(
                        this,
                        LoginActivity088::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun showDeleteDialog(note: Note088) {

        AlertDialog.Builder(this)
            .setTitle("Hapus Catatan")
            .setMessage("Apakah kamu yakin ingin menghapus catatan ini?")
            .setPositiveButton("Hapus") { _, _ ->

                lifecycleScope.launch {
                    try {
                        database.noteDao().deleteNote(note)

                        Toast.makeText(
                            this@DashboardActivity088,
                            "Catatan berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()

                    } catch (e: Exception) {
                        Toast.makeText(
                            this@DashboardActivity088,
                            "Gagal menghapus catatan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}