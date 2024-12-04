package com.example.trabalhodesenvolvimentoplataformamoveis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MinhasArtesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.minhas_artes)

        val db = Firebase.firestore
        val userId = "usuario_logado"
        val layoutContainer = findViewById<LinearLayout>(R.id.layoutContainer)

        db.collection("usuarios").document(userId).collection("artes").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val arteId = document.id
                val titulo = document.getString("titulo") ?: "Sem Título"
                val descricao = document.getString("descricao") ?: "Sem Descrição"
                val arteView = layoutInflater.inflate(R.layout.item_arte, null)
                val tituloView = arteView.findViewById<TextView>(R.id.tituloArte)
                val descricaoView = arteView.findViewById<TextView>(R.id.descricaoArte)
                val btnEditar = arteView.findViewById<Button>(R.id.buttonEditar)
                val btnRemover = arteView.findViewById<Button>(R.id.buttonRemover)

                tituloView.text = titulo
                descricaoView.text = descricao
                btnEditar.setOnClickListener {
                    val intent = Intent(this, EditarArteActivity::class.java)
                    intent.putExtra("arteId", arteId)
                    startActivity(intent)
                }
                btnRemover.setOnClickListener {
                    db.collection("usuarios").document(userId).collection("artes").document(arteId).delete().addOnSuccessListener {
                        layoutContainer.removeView(arteView)
                    }
                }
                layoutContainer.addView(arteView)
            }
        }
    }
}