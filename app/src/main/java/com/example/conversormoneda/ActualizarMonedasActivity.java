package com.example.conversormoneda;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class ActualizarMonedasActivity extends Activity {

    MonedasDb db;
    LinearLayout layoutMonedas;
    ArrayList<EditText> editTextValores = new ArrayList<>();
    ArrayList<String> nombresMonedas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_monedas);

        db = new MonedasDb(this, "monedas.db", null, 1);
        layoutMonedas = findViewById(R.id.layoutMonedas);

        mostrarMonedas();

        Button btnGuardar = findViewById(R.id.btnGuardarCambios);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });
    }

    private void mostrarMonedas() {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT moneda, valor FROM monedas", null);


        while (cursor.moveToNext()) {
            String nombre = cursor.getString(0);
            double valor = cursor.getDouble(1);

            TextView tv = new TextView(this);
            tv.setText(nombre);
            layoutMonedas.addView(tv);

            EditText et = new EditText(this);
            et.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et.setText(String.valueOf(valor));

            // Asignar el fondo redondeado
            et.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_edittext));

            // Opcional: agregar padding para que el texto no quede pegado al borde
            int paddingDp = (int) (16 * getResources().getDisplayMetrics().density); // 16dp en píxeles
            et.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);

            layoutMonedas.addView(et);

            nombresMonedas.add(nombre);
            editTextValores.add(et);
        }


        cursor.close();
        database.close();
    }

    private void guardarCambios() {
        SQLiteDatabase database = db.getWritableDatabase();
        for (int i = 0; i < nombresMonedas.size(); i++) {
            String nombre = nombresMonedas.get(i);
            String nuevoValorTexto = editTextValores.get(i).getText().toString();
            try {
                double nuevoValor = Double.parseDouble(nuevoValorTexto);
                ContentValues values = new ContentValues();
                values.put("valor", nuevoValor);
                database.update("monedas", values, "moneda = ?", new String[]{nombre});
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido para " + nombre, Toast.LENGTH_SHORT).show();
            }
        }
        database.close();
        Toast.makeText(this, "Valores actualizados correctamente", Toast.LENGTH_SHORT).show();

        // 👇 Cierra esta actividad para regresar al MainActivity
        finish();
    }

}

