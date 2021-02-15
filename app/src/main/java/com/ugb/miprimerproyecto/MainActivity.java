package com.ugb.miprimerproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import java.util.List;
import java.util.Vector;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity {
    PieChartView pieChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChartView = findViewById(R.id.grpEstadistica);
        List listData = new ArrayList<>();
        listData.add(new SliceValue(25, Color.rgb(0, 73, 253  )).setLabel("P1: $25"));
        listData.add(new SliceValue(15, Color.parseColor("#00822B")).setLabel("P2: $15"));
        listData.add(new SliceValue(35, Color.RED).setLabel("P3: $35"));

        PieChartData pieChartData = new PieChartData(listData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);

        pieChartView.setPieChartData(pieChartData);
    }
}