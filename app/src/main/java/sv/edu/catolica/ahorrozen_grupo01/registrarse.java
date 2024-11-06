package sv.edu.catolica.ahorrozen_grupo01;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class registrarse extends AppCompatActivity {


    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrarse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
    }

    public void Registrarse(View view) {
        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String nombreCompleto = etName.getText().toString();
        String correo = etEmail.getText().toString();
        String contrasena = etPassword.getText().toString();
        String confirmarContrasena = etConfirmPassword.getText().toString();

        if (!contrasena.equals(confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombreCompleto);
        registro.put("correo", correo);
        registro.put("contrasena", contrasena);

        long resultado = bd.insert("Usuarios", null, registro);
        bd.close();

        if (resultado != -1) {
            // Guardar el ID del usuario en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("miAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("idUsuario", resultado);
            editor.apply();

            etName.setText("");
            etEmail.setText("");
            etPassword.setText("");
            etConfirmPassword.setText("");
            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
            Intent home = new Intent(registrarse.this, MainActivity.class);
            startActivity(home);
            finish();
        } else {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        Intent login = new Intent(registrarse.this,login.class);
        startActivity(login);
    }
}