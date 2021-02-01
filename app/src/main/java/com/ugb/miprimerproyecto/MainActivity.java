package com.ugb.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TabHost tbhConversores;
    Button btnConvertir;
    TextView tempVal;
    Spinner spnOpcionDe, spnOpcionA;
    conversores miConversor = new conversores();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbhConversores = findViewById(R.id.tbhConversores);
        tbhConversores.setup();

        tbhConversores.addTab(tbhConversores.newTabSpec("Monedas").setContent(R.id.tabMonedas).setIndicator("M"));
        tbhConversores.addTab(tbhConversores.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("L"));
        tbhConversores.addTab(tbhConversores.newTabSpec("Masa").setContent(R.id.tabMasa).setIndicator("P"));

        btnConvertir = findViewById(R.id.btnCalcular);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempVal = (TextView)findViewById(R.id.txtcantidad);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                spnOpcionDe = findViewById(R.id.cboDe);
                spnOpcionA = findViewById(R.id.cboA);

                tempVal = findViewById(R.id.lblRespuesta);
                tempVal.setText( "Respuesta: "+ miConversor.convertir(0, spnOpcionDe.getSelectedItemPosition(),spnOpcionA.getSelectedItemPosition(), cantidad) );
            }
        });

    }
}

class conversores{
    double[][] conversor = {
        {1.0,8.75,7.77, 24.03,34.8,611.10},/*Monedas*/
        {1.0},/*Longitud*/
            {1.0}/*Masa*/
    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return conversor[opcion][a] / conversor[opcion][de] * cantidad;
    }
}