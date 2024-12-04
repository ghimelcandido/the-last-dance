package com.example.trabalhodesenvolvimentoplataformamoveis

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AdicionarArteActivity : ComponentActivity() {
    private var imageUri: Uri? = null
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_arte)

        val adicionarNomeDoArtista = findViewById<EditText>(R.id.adicionarNomeDoArtista)
        val adicionarDescricao = findViewById<EditText>(R.id.adicionarDescricao)
        val adicionarTitulo = findViewById<EditText>(R.id.adicionarTitulo)
        val botaoConfirmar = findViewById<Button>(R.id.botaoConfirmar)
        imageView = findViewById(R.id.imageView)

        imageView.setOnClickListener { selecionarImagem() }
        botaoConfirmar.setOnClickListener {
            val titulo = adicionarTitulo.text.toString().trim()
            val artista = adicionarNomeDoArtista.text.toString().trim()
            val descricao = adicionarDescricao.text.toString().trim()
            if (titulo.isEmpty() || artista.isEmpty() || descricao.isEmpty() || imageUri == null) {
                Toast.makeText(this, "Preencha todos os campos e selecione uma imagem!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            salvarArteNoFirebase(titulo, artista, descricao)
        }
    }

    private fun selecionarImagem() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun salvarArteNoFirebase(titulo: String, artista: String, descricao: String) {
        val storageRef = FirebaseStorage.getInstance().reference.child("artes/${UUID.randomUUID()}.jpg")
        val databaseRef = FirebaseDatabase.getInstance().reference.child("artes")
        storageRef.putFile(imageUri!!).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val arteId = databaseRef.push().key ?: return@addOnSuccessListener
                val arte = mapOf("id" to arteId, "titulo" to titulo, "artista" to artista, "descricao" to descricao, "imagemUrl" to uri.toString())
                databaseRef.child(arteId).setValue(arte).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Arte adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Erro ao salvar dados!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Erro ao fazer upload!", Toast.LENGTH_SHORT).show()
        }
    }
}