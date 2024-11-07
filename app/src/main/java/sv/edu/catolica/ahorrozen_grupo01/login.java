package sv.edu.catolica.ahorrozen_grupo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Inicio(View view) {
        Intent inicio = new Intent(login.this, MainActivity.class);
        startActivity(inicio);
    }

    public void cambiarContra(View view) {
        Intent cambiarContra = new Intent(login.this, cambiarContra.class);
        startActivity(cambiarContra);
    }

    public void Registro(View view) {
        Intent Registro = new Intent(login.this, registrarse.class);
        startActivity(Registro);
    }
}