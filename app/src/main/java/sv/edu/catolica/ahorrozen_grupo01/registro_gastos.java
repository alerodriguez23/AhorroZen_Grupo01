package sv.edu.catolica.ahorrozen_grupo01;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class registro_gastos extends AppCompatActivity {

    private EditText etFechaGasto;
    private Spinner spCategoria, spMetodoPago;
    private EditText etMontoGasto;
    private Button btnGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_gastos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registro_gastos), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFechaGasto = findViewById(R.id.et_fecha_gasto);
        spCategoria = findViewById(R.id.sp_categoria);
        spMetodoPago = findViewById(R.id.sp_metodo_pago);
        etMontoGasto = findViewById(R.id.et_monto_gasto);
        btnGuardar = findViewById(R.id.btn_guardar);
        etFechaGasto.setOnClickListener(v -> showDatePickerDialog());
        cargarCategorias();
        cargarMetodosPago();
    }

    private void showDatePickerDialog() {
        // Obtiene la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog
                ( getApplicationContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Ajusta el formato de fecha y muestra en el EditText
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etFechaGasto.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void cargarCategorias() {
        List<String> categorias = new ArrayList<>();
        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nombre FROM Categoria", null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);
    }
    private void cargarMetodosPago() {
        List<String> metodosPago = new ArrayList<>();
        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT metodo FROM MetodoPago", null);
        if (cursor.moveToFirst()) {
            do {
                metodosPago.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metodosPago);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMetodoPago.setAdapter(adapter);
    }
    private long obtenerIdCategoria(String nombreCategoria, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT idCategoria FROM Categoria WHERE nombre = ?", new String[]{nombreCategoria});
        long idCategoria = -1;
        if (cursor.moveToFirst()) {
            idCategoria = cursor.getLong(0);
        }
        cursor.close();
        return idCategoria;
    }

    private long obtenerIdMetodoPago(String nombreMetodo, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT idMetodoPago FROM MetodoPago WHERE metodo = ?", new String[]{nombreMetodo});
        long idMetodoPago = -1;
        if (cursor.moveToFirst()) {
            idMetodoPago = cursor.getLong(0);
        }
        cursor.close();
        return idMetodoPago;
    }

    public void guardarEgreso(View view) {
        String categoriaSeleccionada = spCategoria.getSelectedItem().toString();
        String metodoPagoSeleccionado = spMetodoPago.getSelectedItem().toString();
        String montoStr = etMontoGasto.getText().toString();
        String fecha = etFechaGasto.getText().toString();

        if (montoStr.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double monto = Double.parseDouble(montoStr);

        SharedPreferences preferences = getSharedPreferences("miAppPrefs", MODE_PRIVATE);
        long idUsuario = preferences.getLong("idUsuario", -1);
        if (idUsuario == -1) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        FinanzasBD admin = new FinanzasBD(this, "FinanzasDB", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        long idCategoria = obtenerIdCategoria(categoriaSeleccionada, db);
        long idMetodoPago = obtenerIdMetodoPago(metodoPagoSeleccionado, db);

        ContentValues registro = new ContentValues();
        registro.put("idUsuario", idUsuario);
        registro.put("idCategoria", idCategoria);
        registro.put("idMetodoPago", idMetodoPago);
        registro.put("monto", monto);
        registro.put("fecha", fecha);

        long resultado = db.insert("Egreso", null, registro);
        db.close();

        if (resultado != -1) {
            Toast.makeText(this, "Egreso registrado con éxito", Toast.LENGTH_SHORT).show();
            etMontoGasto.setText("");
            etFechaGasto.setText("");
            spCategoria.setSelection(0);
            spMetodoPago.setSelection(0);
        } else {
            Toast.makeText(this, "Error al registrar el egreso", Toast.LENGTH_SHORT).show();
        }
    }
}