package sv.edu.catolica.ahorrozen_grupo01;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class cambiarContra extends AppCompatActivity {
    private EditText etcorreo, etContrasena, etNuevacontra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cambiar_contra);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cambiarContrasena), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void actualizarContrasena(View view) {
        String correo = etcorreo.getText().toString().trim();
        String nuevaContrasena = etContrasena.getText().toString();
        String confirmarContrasena = etNuevacontra.getText().toString();

        if (correo.isEmpty() || nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT idUsuario, contrasena FROM Usuario WHERE correo = ?", new String[]{correo});

        if (cursor.moveToFirst()) {
            long idUsuario = cursor.getLong(0);
            String contrasenaBD = cursor.getString(1);

            if (!nuevaContrasena.equals(confirmarContrasena)) {
                Toast.makeText(this, "La nueva contraseña y la confirmación no coinciden", Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
                return;
            }
            if (nuevaContrasena.equals(contrasenaBD)) {
                Toast.makeText(this, "La nueva contraseña no puede ser igual a la actual", Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
                return;
            }
            if (!validarContrasena(nuevaContrasena)) {
                Toast.makeText(this, "La nueva contraseña no cumple con los requisitos de seguridad", Toast.LENGTH_LONG).show();
                cursor.close();
                db.close();
                return;
            }

            ContentValues valores = new ContentValues();
            valores.put("contrasena", nuevaContrasena);
            int resultado = db.update("Usuario", valores, "idUsuario = ?", new String[]{String.valueOf(idUsuario)});

            if (resultado != -1) {
                Toast.makeText(this, "Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show();
                etContrasena.setText("");
                etNuevacontra.setText("");
            } else {
                Toast.makeText(this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Correo o contraseña actual incorrectos", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
    private boolean validarContrasena(String contrasena) {
        if (contrasena.length() < 8) return false;

        if (!contrasena.matches(".*[A-Z].*")) return false;

        if (!contrasena.matches(".*[a-z].*")) return false;

        if (!contrasena.matches(".*\\d.*")) return false;

        if (!contrasena.matches(".*[!@#\\$%\\^&\\*].*")) return false;

        if (contrasena.contains(" ")) return false;

        return true;
    }

}