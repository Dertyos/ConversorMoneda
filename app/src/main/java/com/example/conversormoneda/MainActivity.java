package com.example.conversormoneda;

import java.util.Locale;
import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText etMonto, etResultado;
    Button btnDolar, btnEuro, btnPesoMexicano;
    RadioButton rbAColombianos, rbDesdeColombianos;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMonto = (EditText) findViewById(R.id.etMonto);
        etResultado = (EditText) findViewById(R.id.etResultado);
        btnDolar = (Button) findViewById(R.id.btnDolar);
        btnEuro = (Button) findViewById(R.id.btnEuro);
        btnPesoMexicano = (Button) findViewById(R.id.btnPesoMexicano);
        rbAColombianos = (RadioButton) findViewById(R.id.rbAColombianos);
        rbDesdeColombianos = (RadioButton) findViewById(R.id.rbDesdeColombianos);



        btnDolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertirMoneda(4177.56,"$", "Dólares");
            }
        });

        btnEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertirMoneda(4681.70, "€", "Euros");
            }
        });

        btnPesoMexicano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertirMoneda(216.16, "$", "Pesos Mexicanos");
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
                // De moneda extranjera a pesos colombianos
                resultado = monto * tasaCambio;
                simbolo = "$"; // COP siempre es peso colombiano
                nombreMoneda = "Pesos Colombianos";
            } else {
                // De pesos colombianos a moneda extranjera
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