package com.ugb.miprimerproyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

public class chats extends AppCompatActivity {
    ImageView imgTemp;
    TextView tempVal;
    String to="", from="", user="", msg = "", urlPhoto = "", urlPhotoFirestore = "";
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats);

        tempVal = findViewById(R.id.lblToChats);
        imgTemp = findViewById(R.id.imgAtras);
        imgTemp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), lista_usuarios.class);
            startActivity(intent);
        });
        Bundle parametros = getIntent().getExtras();
        if( parametros.getString("to")!=null && parametros.getString("to")!="" ){
            to = parametros.getString("to");
            from = parametros.getString("from");
            user = parametros.getString("user");
            urlPhoto = parametros.getString("urlPhoto");
            urlPhotoFirestore = parametros.getString("urlPhotoFirestore");
            tempVal.setText(user);
        }
        mostrarFoto();
    }
    void mostrarFoto(){
        imgTemp = findViewById(R.id.imgPhotoChat);
        Glide.with(getApplicationContext()).load(urlPhotoFirestore).into(imgTemp);
    }
}
