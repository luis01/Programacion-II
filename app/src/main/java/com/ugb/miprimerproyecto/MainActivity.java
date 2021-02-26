package com.ugb.miprimerproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button btnConvertir;
    TextView tempVal;
    Spinner spnTipo, spnOpcionDe, spnOpcionA;
    conversores miConversor = new conversores();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnx_conversor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getApplicationContext(),"Index: "+ item, Toast.LENGTH_LONG).show();
        int idMenu = item.getItemId();
        switch (idMenu){
            case R.id.mnxMoneda:

                break;

            case R.id.mnxLongitud:

                break;

            case R.id.mnxMasa:

                break;

            case R.id.mnxAlmacenamiento:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnOpcionDe = findViewById(R.id.cboDeL);
        spnOpcionA = findViewById(R.id.cboAL);
        spnTipo = findViewById(R.id.cboTipo);
        spnTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnOpcionDe.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,miConversor.obtenerTipo(position)));
                spnOpcionA.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,miConversor.obtenerTipo(position)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConvertir = findViewById(R.id.btnCalcularL);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempVal = (TextView) findViewById(R.id.txtCantidadL);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                spnOpcionDe = findViewById(R.id.cboDeL);
                spnOpcionA = findViewById(R.id.cboAL);
                tempVal = findViewById(R.id.lblRespuestaL);
                tempVal.setText("Respuesta: " + miConversor.convertir(spnTipo.getSelectedItemPosition(), spnOpcionDe.getSelectedItemPosition(), spnOpcionA.getSelectedItemPosition(), cantidad));
            }
        });
    }
}

class conversores{
    String [][] etiquetas = {
            {"Dolar","Colon SV","Quetzal","Lempira","Cordoba","Colon CR"}, /*Monedas*/
            {"Mts","CM","Pulgadas","Pies"}, /*Longitud*/
            {"Libra","Miligramos","Gramos","Kilogramos","Onzas"}, /*Masa*/
            {"Litros","Tazas", "Galones","Cucharadas"}, /*Volumen*/
            {"Megabyte","Bits","Byte","Kilobyte","Gigabyte","Terabyte"}, /*Almacenamiento*/
            {"Hora","Milisegundo","Segundo","Minutos","Dia","Semana"}, /*Tiempo*/
    };
    double[][] conversor = {
        {1.0,8.75,7.77, 24.03,34.8,611.10},/*Monedas*/
        {1.0, 100.0,39.37,3.28},/*Longitud*/
            {1.0,453592.4,453.5924,0.453592,16}, /*Masa*/
            {1.0,4.226753,0.264172,56.31213}, /*Volumen*/
            {1.0,8000000,1000000,1024,0.001,0.000001}, /*Almacenamiento*/
            {1.0,3600000,3600,60,0.041667,0.005952} /*Tiempo*/
    };
    public String[] obtenerTipo(int opcion){
        return etiquetas[opcion];
    }
    public double convertir(int opcion, int de, int a, double cantidad){
        return conversor[opcion][a] / conversor[opcion][de] * cantidad;
    }
}