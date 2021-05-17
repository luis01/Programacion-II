package com.ugb.miprimerproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    TextView tempVal;
    Button btnGuardar;
    DatabaseReference databaseReference;
    String miToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerToken();

        btnGuardar = findViewById(R.id.btnGuardarUsuario);
        btnGuardar.setOnClickListener(v -> {
            guardarUsuario();
        });
    }
    private void guardarUsuario(){
        try{
            databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

            mostrarMsgToast(miToken);
        }catch (Exception ex){
            mostrarMsgToast(ex.getMessage());
        }
    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void obtenerToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if( !task.isSuccessful() ){
                return;
            }
            miToken = task.getResult();
        });
    }
}