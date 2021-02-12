package com.ugb.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    ListView ltsReproductorMusica;
    ArrayList<String> listaCancionesArrayList;
    Uri uri; //Uri -> Identificador Unico de Recursos.
    Vector<Canciones> cancionesVector = new Vector<Canciones>();
    Canciones canciones;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ltsReproductorMusica = findViewById(R.id.ltsReproductorMusica);
        mostrarListaCanciones();
    }
    private void mostrarListaCanciones() {
        listaCancionesArrayList = new ArrayList<String>();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listaCancionesArrayList);
        ltsReproductorMusica.setAdapter(stringArrayAdapter);

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor listaCanciones = getContentResolver().query(uri,null,null,null, null);
        if( listaCanciones!=null && listaCanciones.moveToFirst()){
            int columnTitle = listaCanciones.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int columnId = listaCanciones.getColumnIndex(MediaStore.Audio.Media._ID);

            do{
                String title = listaCanciones.getString(columnTitle);
                Long id = Long.parseLong(listaCanciones.getString(columnId));
                stringArrayAdapter.add(title);
                canciones = new Canciones(title,id);
                cancionesVector.addElement(canciones);
            }while (listaCanciones.moveToNext());
            stringArrayAdapter.notifyDataSetChanged();
        } else{
            Toast.makeText(getApplicationContext(), "No hay canciones en tu telefono para reproducir.", Toast.LENGTH_LONG).show();
        }
        ltsReproductorMusica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this, ReproducirCancion.class);
                Bundle enviarDatos = new Bundle();
                enviarDatos.putInt("posicion",position);
                //enviarDatos.putStringArrayList("canciones",);
                intent.putExtras(enviarDatos);
                startActivity(intent);
            }
        });
    }
}