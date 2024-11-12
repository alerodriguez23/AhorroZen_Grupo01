package sv.edu.catolica.ahorrozen_grupo01;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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

public class IngresosActivity extends AppCompatActivity {
    private EditText etFechaIngreso;
    private Spinner spCategoria, spMetodoPago;
    private EditText etMontoIngreso;
    private Button btnGuardar;

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
        etFechaIngreso = findViewById(R.id.et_fecha_ingreso);
        spCategoria = findViewById(R.id.sp_categoria);
        spMetodoPago = findViewById(R.id.sp_metodo_pago);
        etMontoIngreso = findViewById(R.id.et_monto_ingreso);
        btnGuardar = findViewById(R.id.btn_guardar);
        etFechaIngreso.setOnClickListener(v -> showDatePickerDialog());
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
                            etFechaIngreso.setText(selectedDate);
                        },
                        year, month, day
                );

        datePickerDialog.show();
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

    public void guardarIngreso(View view) {
        String categoriaSeleccionada = spCategoria.getSelectedItem().toString();
        String metodoPagoSeleccionado = spMetodoPago.getSelectedItem().toString();
        String montoStr = etMontoIngreso.getText().toString();
        String fecha = etFechaIngreso.getText().toString();

        if (montoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un monto", Toast.LENGTH_SHORT).show();
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
        ContentValues registroIngreso = new ContentValues();
        registroIngreso.put("idUsuario", idUsuario);
        registroIngreso.put("idCategoria", obtenerIdCategoria(categoriaSeleccionada, db));
        registroIngreso.put("idMetodoPago", obtenerIdMetodoPago(metodoPagoSeleccionado, db));
        registroIngreso.put("monto", monto);
        registroIngreso.put("fecha", fecha);

        long resultadoIngreso = db.insert("Ingreso", null, registroIngreso);
        if (resultadoIngreso == -1) {
            Toast.makeText(this, "Error al guardar el ingreso", Toast.LENGTH_SHORT).show();
            db.close();
            return;
        }

        Cursor cursorSaldo = db.rawQuery("SELECT saldoTotal FROM Saldo WHERE idUsuario = ?", new String[]{String.valueOf(idUsuario)});
        if (cursorSaldo.moveToFirst()) {
            double saldoActual = cursorSaldo.getDouble(0);
            double nuevoSaldo = saldoActual + monto;

            ContentValues valoresSaldo = new ContentValues();
            valoresSaldo.put("saldoTotal", nuevoSaldo);
            valoresSaldo.put("fechaActualizacion", fecha);

            int resultadoSaldo = db.update("Saldo", valoresSaldo, "idUsuario = ?", new String[]{String.valueOf(idUsuario)});

            if (resultadoSaldo != -1) {
                Toast.makeText(this, "Ingreso guardado y saldo actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar el saldo", Toast.LENGTH_SHORT).show();
            }

        } else {
            ContentValues nuevoSaldo = new ContentValues();
            nuevoSaldo.put("idUsuario", idUsuario);
            nuevoSaldo.put("saldoTotal", monto);
            nuevoSaldo.put("fechaActualizacion", fecha);

            long resultadoNuevoSaldo = db.insert("Saldo", null, nuevoSaldo);

            if (resultadoNuevoSaldo != -1) {
                Toast.makeText(this, "Ingreso guardado y saldo inicial registrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar el saldo inicial", Toast.LENGTH_SHORT).show();
            }
        }
        cursorSaldo.close();
        db.close();
    }
}