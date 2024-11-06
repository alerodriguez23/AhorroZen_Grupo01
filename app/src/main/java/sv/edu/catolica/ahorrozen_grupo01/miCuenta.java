package sv.edu.catolica.ahorrozen_grupo01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class miCuenta extends AppCompatActivity {
    private TextView tvNombreCompleto;
    private EditText etCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mi_cuenta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.micuenta), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvNombreCompleto = findViewById(R.id.tv_nombre_completo);
        etCorreo = findViewById(R.id.et_correo);

        // Cargar los datos del usuario
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        SharedPreferences preferences = getSharedPreferences("miAppPrefs", MODE_PRIVATE);
        long idUsuario = preferences.getLong("idUsuario", -1);
        if (idUsuario == -1) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("SELECT nombre, correo FROM Usuario WHERE correo = ?", new String[]{String.valueOf(idUsuario)});

        if (fila.moveToFirst()) {
            tvNombreCompleto.setText(fila.getString(1));
            etCorreo.setText(fila.getString(2));
        } else {
            Toast.makeText(this, "No se encontraron los datos del usuario", Toast.LENGTH_SHORT).show();
        }

        fila.close();
        db.close();
    }
    public void CambiarContraseña(View view) {
        Intent cambiarContra = new Intent(miCuenta.this, cambiarContra.class);
        startActivity(cambiarContra);
    }
}