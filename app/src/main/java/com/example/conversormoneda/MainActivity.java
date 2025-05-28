package com.example.conversormoneda;

import java.util.Locale;
import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    MonedasDb db;
    EditText etMonto, etResultado;
    Button btnDolar, btnEuro, btnPesoMexicano, btnActualizarMonedas;
    RadioButton rbAColombianos, rbDesdeColombianos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MonedasDb(this, "monedas.db", null, 1);

        etMonto = findViewById(R.id.etMonto);
        etResultado = findViewById(R.id.etResultado);
        btnDolar = findViewById(R.id.btnDolar);
        btnEuro = findViewById(R.id.btnEuro);
        btnPesoMexicano = findViewById(R.id.btnPesoMexicano);
        rbAColombianos = findViewById(R.id.rbAColombianos);
        rbDesdeColombianos = findViewById(R.id.rbDesdeColombianos);
        btnActualizarMonedas = findViewById(R.id.btnActualizarMonedas);

        btnDolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double moneda = db.getValor("USD");
                convertirMoneda(moneda, "$", "Dólares");
            }
        });

        btnEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double moneda = db.getValor("EUR");
                convertirMoneda(moneda, "€", "Euros");
            }
        });

        btnPesoMexicano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double moneda = db.getValor("MXN");
                convertirMoneda(moneda, "$", "Pesos Mexicanos");
            }
        });

        btnActualizarMonedas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActualizarMonedasActivity.class);
                startActivity(intent);
            }
        });
    }

    private void convertirMoneda(double tasaCambio, String simbolo, String nombreMoneda) {
        String montoTexto = etMonto.getText().toString();
        if (montoTexto.isEmpty()) {
            Toast.makeText(this, "Ingrese un monto", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            double resultado;

            if (rbAColombianos.isChecked()) {
                resultado = monto * tasaCambio;
                simbolo = "$";
                nombreMoneda = "Pesos Colombianos";
            } else {
                resultado = monto / tasaCambio;
            }

            NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CO"));
            String resultadoFormateado = formato.format(resultado);
            etResultado.setText(simbolo + " " + resultadoFormateado + " " + nombreMoneda);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato inválido", Toast.LENGTH_SHORT).show();
        }
    }
}
