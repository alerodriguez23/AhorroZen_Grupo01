package sv.edu.catolica.ahorrozen_grupo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GraficaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grafica);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void ingresoI(View view) {
        Intent ingresoGra = new Intent(GraficaActivity.this, IngresosActivity.class);
        startActivity(ingresoGra);
    }

    public void GastosGra(View view) {
        Intent GastosGra = new Intent(GraficaActivity.this, registro_gastos.class);
        startActivity(GastosGra);
    }

    public void PerfilGra(View view) {
        Intent PerfilGra = new Intent(GraficaActivity.this, miCuenta.class);
        startActivity(PerfilGra);
    }

    public void calenGra(View view) {
        Intent calenGra = new Intent(GraficaActivity.this, CalendarioV.class);
        startActivity(calenGra);
    }

    public void catGra(View view) {
        Intent catGra = new Intent(GraficaActivity.this, categorias.class);
        startActivity(catGra);
    }

    public void Graficas(View view) {
        Intent Graficas = new Intent(GraficaActivity.this, GraficaActivity.class);
        startActivity(Graficas);
    }
}