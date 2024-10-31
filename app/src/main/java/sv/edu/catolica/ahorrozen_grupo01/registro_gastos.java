package sv.edu.catolica.ahorrozen_grupo01;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registro_gastos extends AppCompatActivity {

    private EditText etFechaGasto;

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

        etFechaGasto.setOnClickListener(v -> showDatePickerDialog());
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
}