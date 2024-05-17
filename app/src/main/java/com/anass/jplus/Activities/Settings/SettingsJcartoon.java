package com.anass.jplus.Activities.Settings;


import static com.anass.jplus.Activities.MyList.Mylist.changestatucolor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;


import com.anass.jplus.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class SettingsJcartoon extends AppCompatActivity {

    public static String FACEBOOK_URL = "https://www.facebook.com/jycartoon";
    public static String FACEBOOK_PROFI = "https://www.facebook.com/elkadianass";
    public static final String SETTINGS_NAME = "SETTINGS_COMICS";


    //Save the setting
    private SharedPreferences sp;

    int choseplacesaveselect;

    final String[] ss = {"en","es"};
    ImageView gobback;
    RelativeLayout myaccanass,showfacebookpage,callus;
    Spinner choseplacesave;


    ArrayList<String> storageDirectories = new ArrayList<>();
    ArrayList<String> listlang;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);
        changestatucolor(this);
        setContentView(R.layout.settings_jcartoon);

        myaccanass = findViewById(R.id.myaccanass);
        showfacebookpage = findViewById(R.id.showfacebookpage);
        callus = findViewById(R.id.callus);
        gobback = findViewById(R.id.gobback);
        choseplacesave = findViewById(R.id.choseplacesave);

        gobback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //saved setting
        choseplacesaveselect = sp.getInt("choseplacesaveselect",0);







        setupSpinners();



        myaccanass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = FACEBOOK_PROFI;
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);

            }
        });
        showfacebookpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlString = FACEBOOK_URL;
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);

            }
        });
        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String urlString = FACEBOOK_URL;
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);


            }
        });
    }



    public void GetListofStoreage() {

        File[] externalDirs = getExternalFilesDirs(null);

        for (File file : externalDirs) {
            storageDirectories.add(file.getPath());
        }

    }


    private void setupSpinners() {



        listlang = new ArrayList<>();
        listlang.add("English");
        listlang.add("Spanish");

        ArrayAdapter<String>  choseplacesaveaf = new ArrayAdapter<>(SettingsJcartoon.this, R.layout.custom_spinner,listlang);
        choseplacesaveaf.setDropDownViewResource(R.layout.custom_spinner);
        choseplacesave.setAdapter(choseplacesaveaf);
        choseplacesave.setSelection(choseplacesaveselect);
        choseplacesave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setLocale(ss[position]);
                sp.edit().putInt("choseplacesaveselect",position).apply();

                recreate();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void setLocale(String lang){

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(  );
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        sp.edit().putString("my_lang",lang).apply();

    }


}