package com.example.trabalhodesenvolvimentoplataformamoveis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArteAdapter extends RecyclerView.Adapter<ArteAdapter.ArteViewHolder> {

    private Context context;
    private List<Arte> arteList;

    public ArteAdapter(Context context, List<Arte> arteList) {
        this.context = context;
        this.arteList = arteList;
    }

    @NonNull
    @Override
    public ArteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arte, parent, false);
        return new ArteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArteViewHolder holder, int position) {
        Arte arte = arteList.get(position);
        holder.tituloArte.setText(arte.getTitulo());
        holder.descricaoArte.setText(arte.getDescricao());
    }

    @Override
    public int getItemCount() {
        return arteList.size();
    }

    public static class ArteViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemArte;
        TextView tituloArte, descricaoArte;

        public ArteViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemArte = itemView.findViewById(R.id.imagemArte);
            tituloArte = itemView.findViewById(R.id.tituloArte);
            descricaoArte = itemView.findViewById(R.id.descricaoArte);
        }
    }
}

