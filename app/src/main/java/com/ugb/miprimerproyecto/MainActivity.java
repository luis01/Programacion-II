package com.ugb.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn;
    DB miBD;
    ListView ltsAmigos;
    Cursor datosAmigosCursor = null;
    ArrayList<amigos> amigosArrayList=new ArrayList<amigos>();
    amigos misAmigos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnAgregarAmigos);
        btn.setOnClickListener(v->{
           agregarAmigos();
        });
        obtenerDatosAmigos();
    }
    private void agregarAmigos(){
        Intent agregarAmigos = new Intent(getApplicationContext(), AgregarAmigos.class);
        startActivity(agregarAmigos);
    }
    private void obtenerDatosAmigos(){
        miBD = new DB(getApplicationContext(),"",null,1);
        datosAmigosCursor = miBD.administracion_amigos("consultar",null);
        if( datosAmigosCursor.moveToFirst() ){//si hay datos que mostrar
            mostrarDatosAmigos();
        } else {//sino que llame para agregar nuevos amigos...
            mostrarMsgToask("No hay datos de amigos que mostrar, por favor agregue nuevos amigos...");
            agregarAmigos();
        }
    }
    private void mostrarDatosAmigos(){
        ltsAmigos = findViewById(R.id.ltsamigos);
        amigosArrayList.clear();
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
            mostrarMsgToask(datosAmigosCursor.getString(5));
        }while(datosAmigosCursor.moveToNext());
        adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), amigosArrayList);
        ltsAmigos.setAdapter(adaptadorImagenes);

        registerForContextMenu(ltsAmigos);
    }
    private void mostrarMsgToask(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
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