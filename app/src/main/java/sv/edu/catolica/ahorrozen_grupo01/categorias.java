package sv.edu.catolica.ahorrozen_grupo01;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class categorias extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoriaAdapter categoriaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categorias);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorias), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarCategorias();
    }

    public void Calendario(View view) {
        Intent calendario = new Intent(categorias.this, CalendarioV.class);
        startActivity(calendario);
    }

    public void Categoria(View view) {
        Intent categorias = new Intent(categorias.this, categorias.class);
        startActivity(categorias);
    }
    private void cargarCategorias() {
        List<String> categorias = new ArrayList<>();

        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nombre FROM Categoria ORDER BY nombre ASC", null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(0));
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No hay categorías disponibles", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();

        categoriaAdapter = new CategoriaAdapter(categorias);
        recyclerView.setAdapter(categoriaAdapter);
    }
}