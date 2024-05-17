package com.anass.jplus.Activities.ADcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.anass.jplus.R;

public class WelcomPage extends AppCompatActivity {

    TextView textoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_page);

        textoo = findViewById(R.id.textoo);
        textoo.setText(getIntent().getStringExtra("text"));
    }
}