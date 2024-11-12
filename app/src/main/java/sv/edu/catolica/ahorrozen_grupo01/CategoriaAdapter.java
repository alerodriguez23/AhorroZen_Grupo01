package sv.edu.catolica.ahorrozen_grupo01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {
    private List<String> categorias;

    // Constructor que recibe la lista de categorías
    public CategoriaAdapter(List<String> categorias) {
        this.categorias = categorias;
    }

    // Crear una nueva vista para un elemento de la lista
    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    // Enlazar los datos con la vista
    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        String categoria = categorias.get(position);
        holder.tvCategoriaNombre.setText(categoria);
    }

    // Número de elementos en la lista
    @Override
    public int getItemCount() {
        return categorias.size();
    }

    // ViewHolder para cada elemento de categoría
    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoriaNombre;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoriaNombre = itemView.findViewById(R.id.tvCategoriaNombre);
        }
    }
}
