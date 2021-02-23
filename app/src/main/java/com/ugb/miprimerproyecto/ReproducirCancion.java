package com.ugb.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ReproducirCancion extends AppCompatActivity {
    Uri uri; //Uri -> Identificador Unico de Recursos.
    long idCancion;
    Button btnAcciones;
    TextView tempVal;
    MediaPlayer mp;
    ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir_cancion);
        try {
            Bundle recibirDatos = getIntent().getExtras();
            idCancion = recibirDatos.getLong("idCancion");
            if (idCancion>0) {
                tempVal = findViewById(R.id.lblTitulo);
                tempVal.setText( recibirDatos.getString("titulo") );

                preparCancion();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
            btnAcciones = findViewById(R.id.btnPlay);
            btnAcciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mp==null ){
                        preparCancion();
                    }
                    mp.start();
                }
            });
            btnAcciones = findViewById(R.id.btnPause);
            btnAcciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp.pause();
                }
            });
            btnAcciones = findViewById(R.id.btnStop);
            btnAcciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp.stop();
                    mp = null;
                }
            });

            btnAtras = findViewById(R.id.btn_Atras);
            btnAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent principal = new Intent(ReproducirCancion.this, MainActivity.class);
                    startActivity(principal);
                }
            });

    }
    private void preparCancion(){
        mp = new MediaPlayer();
        uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, idCancion);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource(getApplicationContext(), uri);
            mp.prepare();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}