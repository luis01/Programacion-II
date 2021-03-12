package com.ugb.miprimerproyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarAmigos extends AppCompatActivity {
    FloatingActionButton btnAtras;
    ImageView imgFotoAmigo;
    Intent tomarFotoIntent;
    String urlCompletaImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_amigos);

        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v->{
            mostrarVistaPrincipal();
        });
        imgFotoAmigo = findViewById(R.id.imgFotoAmigo);
        imgFotoAmigo.setOnClickListener(v->{
            tomarFotoAmigo();
        });
    }
    private void mostrarVistaPrincipal(){
        Intent iprincipal = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(iprincipal);
    }
    private void tomarFotoAmigo(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if( tomarFotoIntent.resolveActivity(getPackageManager())!=null ){
            File photoAmigo = null;
            try{
                photoAmigo = crearImagenAmigo();
            }catch (Exception e){
                mostrarMsgToast(e.getMessage());
            }
            if( photoAmigo!=null ){
                try{
                    Uri uriPhotoAmigo = FileProvider.getUriForFile(AgregarAmigos.this, "com.ugb.miprimerproyecto.fileprovider",photoAmigo);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoAmigo);
                    startActivityForResult(tomarFotoIntent,1);
                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }
            } else {
                mostrarMsgToast("No fue posible tomar la foto");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if( requestCode==1 && resultCode==RESULT_OK ){
                Bitmap imagenBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoAmigo.setImageBitmap(imagenBitmap);
            }
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }
    private File crearImagenAmigo() throws Exception {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreImagen = "imagen_"+ timeStamp +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( dirAlmacenamiento.exists()==false ){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(nombreImagen,".jpg",dirAlmacenamiento);
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
}