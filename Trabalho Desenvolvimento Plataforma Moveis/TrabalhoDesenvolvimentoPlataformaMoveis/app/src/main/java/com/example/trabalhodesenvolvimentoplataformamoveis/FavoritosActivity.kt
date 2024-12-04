package com.example.trabalhodesenvolvimentoplataformamoveis

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoritosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritos)

        val favoritosLayout: LinearLayout = findViewById(R.id.favoritosLayout)
        val db = Firebase.firestore
        val userId = "usuario_logado" // Substituir pelo ID do usuário logado.

        db.collection("usuarios").document(userId).collection("favoritos")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val titulo = doc.getString("titulo")
                    val descricao = doc.getString("descricao")

                    val favoritoView = TextView(this).apply {
                        text = "$titulo - $descricao"
                        textSize = 18f
                    }
                    favoritosLayout.addView(favoritoView)
                }
            }
    }
}

