package com.example.trabalhodesenvolvimentoplataformamoveis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArteAdapter arteAdapter;
    private List<Arte> arteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_inicial);

        recyclerView = findViewById(R.id.recyclerArtes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arteList = new ArrayList<>();
        arteAdapter = new ArteAdapter(this, arteList);
        recyclerView.setAdapter(arteAdapter);

        TextView loginButton = findViewById(R.id.loginButton);
        TextView favoritosButton = findViewById(R.id.favoritosButton);
        TextView perfilButton = findViewById(R.id.perfilButton);

        loginButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, TelaLoginActivity.class);
        startActivity(intent);
    });

        favoritosButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, FavoritosActivity.class);
        startActivity(intent);
    });

        perfilButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, EditeSeusDadosActivity.class);
        startActivity(intent);
    });

        carregarArtesDoFirebase();
    }

    private void carregarArtesDoFirebase() {
        FirebaseDatabase.getInstance().getReference("artes")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arteList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Arte arte = dataSnapshot.getValue(Arte.class);
                    if (arte != null) {
                        arteList.add(arte);
                    }
                }
                    arteAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Erro ao carregar dados!", Toast.LENGTH_SHORT).show();
                }
            });
    }
}