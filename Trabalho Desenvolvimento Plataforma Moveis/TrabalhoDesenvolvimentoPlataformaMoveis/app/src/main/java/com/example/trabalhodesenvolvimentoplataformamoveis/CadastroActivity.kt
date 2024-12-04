package com.example.trabalhodesenvolvimentoplataformamoveis

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CadastroActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_cadastro)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val nome = findViewById<EditText>(R.id.adicionarTitulo)
        val endereco = findViewById<EditText>(R.id.adicionarNomeDoArtista)
        val senha = findViewById<EditText>(R.id.cadastroSenha)
        val confirmarSenha = findViewById<EditText>(R.id.cadastroConfirmarSenha)
        val botaoConfirmar = findViewById<Button>(R.id.botaoConfirmar)

        botaoConfirmar.setOnClickListener {
            val nomeUsuario = nome.text.toString().trim()
            val enderecoUsuario = endereco.text.toString().trim()
            val senhaUsuario = senha.text.toString()
            val confirmarSenhaUsuario = confirmarSenha.text.toString()

            if (nomeUsuario.isEmpty() || enderecoUsuario.isEmpty() || senhaUsuario.isEmpty() || confirmarSenhaUsuario.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senhaUsuario != confirmarSenhaUsuario) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(enderecoUsuario, senhaUsuario)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid

                        val user = hashMapOf(
                            "nome" to nomeUsuario,
                            "email" to enderecoUsuario
                        )

                        firestore.collection("usuarios").document(userId!!)
                            .set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Erro ao salvar dados: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}