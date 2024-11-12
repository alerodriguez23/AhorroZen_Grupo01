package sv.edu.catolica.ahorrozen_grupo01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NuevaCategoria extends AppCompatActivity {
    private EditText etCategoryName;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_categoria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nuevaCategoria), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etCategoryName = findViewById(R.id.category_name);
        btnSave = findViewById(R.id.btn_save);
    }
    public void irPerfilCat(View view) {
        Intent perfil = new Intent(NuevaCategoria.this, miCuenta.class);
        startActivity(perfil);
    }
    public void GastosCat(View view) {
        Intent gastos = new Intent(NuevaCategoria.this, miCuenta.class);
        startActivity(gastos);
    }
    public void agregarCategoria(View view) {
        String nombreCategoria = etCategoryName.getText().toString().trim();

        if (nombreCategoria.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un nombre para la categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombreCategoria);

        long resultado = db.insert("Categoria", null, registro);
        db.close();

        if (resultado != -1) {
            Toast.makeText(this, "Categoría guardada con éxito", Toast.LENGTH_SHORT).show();
            etCategoryName.setText("");
        } else {
            Toast.makeText(this, "Error al guardar la categoría", Toast.LENGTH_SHORT).show();
        }
    }
    public void CalendarioCat(View view) {
        Intent calendario = new Intent(NuevaCategoria.this, CalendarioV.class);
        startActivity(calendario);
    }
    public void verCategorias(View view) {
        Intent categorias = new Intent(NuevaCategoria.this, categorias.class);
        startActivity(categorias);
    }

    public void ingresoCat(View view) {
        Intent ingresoCat = new Intent(NuevaCategoria.this, IngresosActivity.class);
        startActivity(ingresoCat);
    }
}