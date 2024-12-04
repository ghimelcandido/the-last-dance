package com.example.trabalhodesenvolvimentoplataformamoveis

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PaginaArteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_arte)

        val buttonFavorito: Button = findViewById(R.id.buttonFavorito)
        val db = Firebase.firestore
        val userId = "usuario_logado"
        val arteId = "arte_um"

        buttonFavorito.setOnClickListener {
            db.collection("usuarios").document(userId).collection("favoritos").document(arteId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        db.collection("usuarios").document(userId).collection("favoritos").document(arteId)
                            .delete()
                            .addOnSuccessListener {
                                buttonFavorito.text = "Favoritar"
                            }
                    } else {
                        val favorito = mapOf(
                            "id" to arteId,
                            "titulo" to "Arte Um",
                            "descricao" to "Descrição da arte.",
                            "imagem" to "url_da_imagem"
                        )

                        db.collection("usuarios").document(userId).collection("favoritos").document(arteId)
                            .set(favorito)
                            .addOnSuccessListener {
                                buttonFavorito.text = "Remover dos Favoritos"
                            }
                    }
                }
        }
    }
}