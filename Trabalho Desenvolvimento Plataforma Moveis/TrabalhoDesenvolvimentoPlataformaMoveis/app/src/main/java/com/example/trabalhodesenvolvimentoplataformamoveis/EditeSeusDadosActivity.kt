package com.example.trabalhodesenvolvimentoplataformamoveis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class EditeSeusDadosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edite_seus_dados)

        val botaoAdicionarArte = findViewById<Button>(R.id.botaoAdicionarArte)
        val botaoMinhasArtes = findViewById<Button>(R.id.botaoMinhasArtes)

        botaoAdicionarArte.setOnClickListener {
            val intent = Intent(this, AdicionarArteActivity::class.java)
            startActivity(intent)
        }

        botaoMinhasArtes.setOnClickListener {
            val intent = Intent(this, MinhasArtesActivity::class.java)
            startActivity(intent)
        }
    }
}