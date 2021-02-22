package com.ugb.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    TextView tempVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempVal = findViewById(R.id.lblSensorLuz);
        activarSensorProximidad();
    }

    @Override
    protected void onResume() {
        iniciar();
        super.onResume();
    }

    @Override
    protected void onPause() {
        detener();
        super.onPause();
    }
    private void activarSensorProximidad(){
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if( sensor==null ){
            Toast.makeText(getApplicationContext(), "No dispones de sensor de Luz", Toast.LENGTH_LONG).show();
            finish();
        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                tempVal.setText( "Valor: Luz: "+event.values[0] );
                if(event.values[0]<=10){
                    getWindow().getDecorView().setBackgroundColor(Color.parseColor("#000000"));
                } else if( event.values[0]<=20){
                    getWindow().getDecorView().setBackgroundColor(Color.parseColor("#A3A3A2"));
                } else if( event.values[0]<=30){
                    getWindow().getDecorView().setBackgroundColor(Color.parseColor("#DADADA"));
                } else{
                    getWindow().getDecorView().setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    private void iniciar(){
        sensorManager.registerListener(sensorEventListener,sensor,2000*1000);
    }
    private void detener(){
        sensorManager.unregisterListener(sensorEventListener);
    }
}