package com.anass.jplus.Activities;

import static com.anass.jplus.Activities.MyList.Mylist.changestatucolor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anass.jplus.R;

public class ANOUCEPAGE extends AppCompatActivity {

    TextView message;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anoucepage);
        changestatucolor(this);


        message = findViewById(R.id.message);

        String text = getIntent().getStringExtra("text");
        message.setText(text);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}