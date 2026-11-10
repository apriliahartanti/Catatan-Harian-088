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
import kotlinx.coroutines.launch

class LoginActivity088 : AppCompatActivity() {

    private lateinit var database: AppDatabase088

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_088)

        database = AppDatabase088.getDatabase(this)

        val etUsername = findViewById<EditText>(R.id.etUsername088)
        val etPassword = findViewById<EditText>(R.id.etPassword088)
        val btnLogin = findViewById<Button>(R.id.btnLogin088)
        val tvGoRegister = findViewById<TextView>(R.id.tvGoRegister088)

        btnLogin.setOnClickListener {

            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username dan password harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val user = database.userDao()
                        .loginUser(username, password)

                    if (user != null) {
                        val intent = Intent(
                            this@LoginActivity088,
                            DashboardActivity088::class.java
                        )
                        intent.putExtra("USERNAME", user.username)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity088,
                            "Username atau password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@LoginActivity088,
                        "Login gagal: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        tvGoRegister.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity088::class.java)
            )
        }
    }
}