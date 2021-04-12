package com.ugb.miprimerproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn;
    DB miBD;
    ListView ltsAmigos;
    Cursor datosAmigosCursor = null;
    ArrayList<amigos> amigosArrayList=new ArrayList<amigos>();
    ArrayList<amigos> amigosArrayListCopy=new ArrayList<amigos>();
    amigos misAmigos;
    JSONArray jsonArrayDatosAmigos;
    JSONObject jsonObjectDatosAmigos;
    utilidades u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnAgregarAmigos);
        btn.setOnClickListener(v->{
           agregarAmigos("nuevo", new String[]{});
        });
        obtenerDatosAmigos();
        buscarAmigos();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_amigos, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        datosAmigosCursor.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(datosAmigosCursor.getString(1));
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.mnxAgregar:
                    agregarAmigos("nuevo", new String[]{});
                    break;
                case R.id.mnxModificar:
                    String[] datos = {
                            datosAmigosCursor.getString(0),//idAmigo
                            datosAmigosCursor.getString(1),//nombre
                            datosAmigosCursor.getString(2),//telefono
                            datosAmigosCursor.getString(3),//direccion
                            datosAmigosCursor.getString(4), //email
                            datosAmigosCursor.getString(5) //url photo
                    };
                    agregarAmigos("modificar", datos);
                    break;
                case R.id.mnxEliminar:
                    eliminarAmigo();
                    break;
            }
        }catch (Exception ex){
            mostrarMsgToask(ex.getMessage());
        }
        return super.onContextItemSelected(item);
    }
    private void eliminarAmigo(){
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
            confirmacion.setTitle("Esta seguro de eliminar el registro?");
            confirmacion.setMessage(datosAmigosCursor.getString(1));
            confirmacion.setPositiveButton("Si", (dialog, which) -> {
                miBD = new DB(getApplicationContext(), "", null, 1);
                datosAmigosCursor = miBD.administracion_amigos("eliminar", new String[]{datosAmigosCursor.getString(0)});//idAmigo
                obtenerDatosAmigos();
                mostrarMsgToask("Registro Eliminado con exito...");
                dialog.dismiss();//cerrar el cuadro de dialogo
            });
            confirmacion.setNegativeButton("No", (dialog, which) -> {
                mostrarMsgToask("Eliminacion cancelada por el usuario...");
                dialog.dismiss();
            });
            confirmacion.create().show();
        }catch (Exception ex){
            mostrarMsgToask(ex.getMessage());
        }
    }
    private void buscarAmigos() {
        TextView tempVal = findViewById(R.id.txtBuscarAmigos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    amigosArrayList.clear();
                    if( tempVal.getText().toString().trim().length()<1 ){//si no esta escribiendo, mostramos todos los registros
                        amigosArrayList.addAll(amigosArrayListCopy);
                    } else {//si esta buscando entonces filtramos los datos
                        for (amigos am : amigosArrayListCopy){
                            String nombre = am.getNombre();
                            String tel = am.getTelefono();
                            String email = am.getEmail();
                            String direccion = am.getDireccion();

                            String buscando = tempVal.getText().toString().trim().toLowerCase();//escribe en la caja de texto...

                            if(nombre.toLowerCase().trim().contains(buscando) ||
                                tel.trim().contains(buscando) ||
                                email.trim().toLowerCase().contains(buscando) ||
                                    direccion.trim().toLowerCase().contains(buscando)
                            ){
                                amigosArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), amigosArrayList);
                    ltsAmigos.setAdapter(adaptadorImagenes);
                }catch (Exception e){
                    mostrarMsgToask(e.getMessage());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void agregarAmigos(String accion, String[] datos){
        try {
            Bundle parametrosAmigos = new Bundle();
            parametrosAmigos.putString("accion", accion);
            parametrosAmigos.putStringArray("datos", datos);

            Intent agregarAmigos = new Intent(getApplicationContext(), AgregarAmigos.class);
            agregarAmigos.putExtras(parametrosAmigos);
            startActivity(agregarAmigos);
        }catch (Exception e){
            mostrarMsgToask(e.getMessage());
        }
    }
    private void obtenerDatosAmigosOffLine(){
        miBD = new DB(getApplicationContext(),"",null,1);
        datosAmigosCursor = miBD.administracion_amigos("consultar",null);
        if( datosAmigosCursor.moveToFirst() ){//si hay datos que mostrar
            mostrarDatosAmigosOffLine();
        } else {//sino que llame para agregar nuevos amigos...
            mostrarMsgToask("No hay datos de amigos que mostrar, por favor agregue nuevos amigos...");
            agregarAmigos("nuevo", new String[]{});
        }
    }
    private void obtenerDatosAmigosOnLine(){
        try {
            ConexionServer conexionServer = new ConexionServer();
            String resp = conexionServer.execute(u.urlServer, "GET").get();

            jsonObjectDatosAmigos=new JSONObject(resp);
            jsonArrayDatosAmigos = jsonObjectDatosAmigos.getJSONArray("rows");
            mostrarDatosAmigosOnLine();
        }catch (Exception ex){
            mostrarMsgToask(ex.getMessage());
        }
    }
    private void obtenerDatosAmigos(){
        //si tengo internet obtener datos amigos online, sino, obtener datos amigos offline
        obtenerDatosAmigosOnLine();
    }
    private void mostrarDatosAmigosOnLine(){
        try{
            if(jsonArrayDatosAmigos.length()>0){
                ltsAmigos = findViewById(R.id.ltsamigos);
                amigosArrayList.clear();
                amigosArrayListCopy.clear();

                JSONObject jsonObject;
                for(int i=0; i<jsonArrayDatosAmigos.length(); i++){
                    jsonObject=jsonArrayDatosAmigos.getJSONObject(i).getJSONObject("value");
                    misAmigos = new amigos(
                            jsonObject.getString("_id"),
                            jsonObject.getString("nombre"),
                            jsonObject.getString("telefono"),
                            jsonObject.getString("direccion"),
                            jsonObject.getString("email"),
                            jsonObject.getString("urlPhoto")
                    );
                    amigosArrayList.add(misAmigos);
                }
                adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), amigosArrayList);
                ltsAmigos.setAdapter(adaptadorImagenes);

                registerForContextMenu(ltsAmigos);

                amigosArrayListCopy.addAll(amigosArrayList);
            }else{
                mostrarMsgToask("NO Hay Registro que mostrar");
                agregarAmigos("nuevo", new String[]{});
            }
        }catch (Exception e){
            mostrarMsgToask(e.getMessage());
        }
    }
    private void mostrarDatosAmigosOffLine(){
        ltsAmigos = findViewById(R.id.ltsamigos);
        amigosArrayList.clear();
        amigosArrayListCopy.clear();
        do{
            misAmigos = new amigos(
                    datosAmigosCursor.getString(0),//idAmigo
                    datosAmigosCursor.getString(1),//nombre
                    datosAmigosCursor.getString(2),//telefono
                    datosAmigosCursor.getString(3),//direccion
                    datosAmigosCursor.getString(4),//email
                    datosAmigosCursor.getString(5) //urlPhoto
            );
            amigosArrayList.add(misAmigos);
        }while(datosAmigosCursor.moveToNext());
        adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), amigosArrayList);
        ltsAmigos.setAdapter(adaptadorImagenes);

        registerForContextMenu(ltsAmigos);

        amigosArrayListCopy.addAll(amigosArrayList);
    }
    private void mostrarMsgToask(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    private class ConexionServer extends AsyncTask<String, String, String>{
        HttpURLConnection urlConnection;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder result = new StringBuilder();
            try{
                String uri = parametros[0];
                String metodo = parametros[1];
                URL url = new URL(uri);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod(metodo);

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String linea;
                while( (linea=bufferedReader.readLine())!=null ){
                    result.append(linea);
                }
            }catch (Exception e){
                Log.i("GET", e.getMessage());
            }
            return result.toString();
        }
    }
}

class amigos{
    String idAmigo;
    String nombre;
    String telefono;
    String direccion;
    String email;
    String urlImg;

    public amigos(String idAmigo, String nombre, String telefono, String direccion, String email, String urlImg) {
        this.idAmigo = idAmigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.urlImg = urlImg;
    }

    public String getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(String idAmigo) {
        this.idAmigo = idAmigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}