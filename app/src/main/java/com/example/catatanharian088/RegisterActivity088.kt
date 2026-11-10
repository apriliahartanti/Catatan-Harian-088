package com.example.catatanharian088

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.catatanharian088.data.AppDatabase088
import com.example.catatanharian088.data.User088
import kotlinx.coroutines.launch

class RegisterActivity088 : AppCompatActivity() {

    private lateinit var database: AppDatabase088

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_088)

        database = AppDatabase088.getDatabase(this)

        val etUsername = findViewById<EditText>(R.id.etUsername088)
        val etPassword = findViewById<EditText>(R.id.etPassword088)
        val etConfirmPassword =
            findViewById<EditText>(R.id.etConfirmPassword088)

        val btnRegister = findViewById<Button>(R.id.btnRegister088)
        val tvBackLogin = findViewById<TextView>(R.id.tvBackLogin088)

        btnRegister.setOnClickListener {

            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (username.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Semua kolom harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(
                    this,
                    "Password minimal 6 karakter",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(
                    this,
                    "Konfirmasi password tidak cocok",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val existingUser =
                        database.userDao().getUserByUsername(username)

                    if (existingUser != null) {
                        Toast.makeText(
                            this@RegisterActivity088,
                            "Username sudah digunakan",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        database.userDao().registerUser(
                            User088(
                                username = username,
                                password = password
                            )
                        )

                        Toast.makeText(
                            this@RegisterActivity088,
                            "Registrasi berhasil",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(
                                this@RegisterActivity088,
                                LoginActivity088::class.java
                            )
                        )
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@RegisterActivity088,
                        "Registrasi gagal: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        tvBackLogin.setOnClickListener {
            finish()
        }
    }
}