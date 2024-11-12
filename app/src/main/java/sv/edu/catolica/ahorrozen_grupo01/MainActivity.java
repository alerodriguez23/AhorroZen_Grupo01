package sv.edu.catolica.ahorrozen_grupo01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PieChart pieChart;
    private double totalIngresos;
    private double totalGastos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pieChart = findViewById(R.id.pieChart);

        SharedPreferences preferences = getSharedPreferences("miAppPrefs", MODE_PRIVATE);
        long idUsuario = preferences.getLong("idUsuario", -1);
        if (idUsuario != -1) {
            obtenerTotales(idUsuario);
            configurarPieChart();
        }
    }

    public void irPerfil(View view) {
        Intent perfil = new Intent(MainActivity.this, miCuenta.class);
        startActivity(perfil);
    }

    public void Gastos(View view) {
        Intent gastos = new Intent(MainActivity.this, miCuenta.class);
        startActivity(gastos);
    }

    public void ingrsoH(View view) {
        Intent ingrsoH = new Intent(MainActivity.this, IngresosActivity.class);
        startActivity(ingrsoH);
    }
    private double obtenerTotalIngresos(long idUsuario, SQLiteDatabase db) {
        double totalIngreso = 0.0;
        Cursor cursor = db.rawQuery("SELECT SUM(monto) AS totalIngreso FROM Ingreso WHERE idUsuario = ?", new String[]{String.valueOf(idUsuario)});
        if (cursor.moveToFirst()) {
            totalIngreso = cursor.getDouble(0);
        }
        cursor.close();
        return totalIngreso;
    }

    private double obtenerTotalEgresos(long idUsuario, SQLiteDatabase db) {
        double totalEgreso = 0.0;
        Cursor cursor = db.rawQuery("SELECT SUM(monto) AS totalEgreso FROM Egreso WHERE idUsuario = ?", new String[]{String.valueOf(idUsuario)});
        if (cursor.moveToFirst()) {
            totalEgreso = cursor.getDouble(0);
        }
        cursor.close();
        return totalEgreso;
    }

    private void obtenerTotales(long idUsuario) {
        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        totalIngresos = obtenerTotalIngresos(idUsuario, db);
        totalGastos = obtenerTotalEgresos(idUsuario, db);

        db.close();
    }
    private void configurarPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        double total = totalIngresos + totalGastos;

        if (total > 0) {
            entries.add(new PieEntry((float) (totalIngresos / total * 100), "Ingresos: $" + totalIngresos));
            entries.add(new PieEntry((float) (totalGastos / total * 100), "Gastos: $" + totalGastos));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Ingresos vs Gastos");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(16f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setCenterText("Ingresos vs Gastos");
        pieChart.setCenterTextSize(18f);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);

        pieChart.invalidate();
    }
}