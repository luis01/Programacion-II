package com.ugb.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnAgregarAmigos);
        btn.setOnClickListener(v->{
           agregarAmigos();
        });
    }
    private void agregarAmigos(){
        Intent agregarAmigos = new Intent(getApplicationContext(), AgregarAmigos.class);
        startActivity(agregarAmigos);
    }
}