package com.example.trabalhodesenvolvimentoplataformamoveis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

class TelaLoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.campoEmail)
        val senha = findViewById<EditText>(R.id.campoSenha)
        val botaoLogin = findViewById<Button>(R.id.buttonLogin)
        val textoCadastrar = findViewById<TextView>(R.id.cadastro)

        botaoLogin.setOnClickListener {
            val emailUsuario = email.text.toString().trim()
            val senhaUsuario = senha.text.toString()

            if (emailUsuario.isEmpty() || senhaUsuario.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(emailUsuario, senhaUsuario)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        textoCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}