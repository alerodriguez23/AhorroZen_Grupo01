package sv.edu.catolica.ahorrozen_grupo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IngresosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingresos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void GastosI(View view) {
        Intent GastosI = new Intent(IngresosActivity.this, registro_gastos.class);
        startActivity(GastosI);
    }

    public void PerfilI(View view) {
        Intent PerfilI = new Intent(IngresosActivity.this, miCuenta.class);
        startActivity(PerfilI);
    }

    public void calenI(View view) {
        Intent calenI = new Intent(IngresosActivity.this, CalendarioV.class);
        startActivity(calenI);
    }

    public void catI(View view) {
        Intent catI = new Intent(IngresosActivity.this, categorias.class);
        startActivity(catI);
    }

    public void ingresos(View view) {
        Intent ingresos = new Intent(IngresosActivity.this, IngresosActivity.class);
        startActivity(ingresos);
    }
}