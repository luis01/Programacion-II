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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class ReproducirCancion extends AppCompatActivity {
    Uri uri; //Uri -> Identificador Unico de Recursos.
    Vector<Canciones> cancionesVector = new Vector<Canciones>();
    int posicion;
    Button btnAcciones;
    TextView tempVal;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir_cancion);
        try {
            Bundle recibirPosicion = getIntent().getExtras();
            posicion = recibirPosicion.getInt("posicion");
            if (posicion >= 0) {
                tempVal = findViewById(R.id.lblTitulo);
                tempVal.setText(cancionesVector.get(posicion).getTitle());

                mp = new MediaPlayer();
                uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cancionesVector.get(posicion).getId());
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mp.setDataSource(getApplicationContext(), uri);
                    mp.prepare();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
            btnAcciones = findViewById(R.id.btnPlay);
            btnAcciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }
            });

    }
}