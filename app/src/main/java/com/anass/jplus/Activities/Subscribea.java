package com.anass.jplus.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.anass.jplus.Activities.MyList.Mylist;
import com.anass.jplus.R;

public class Subscribea extends AppCompatActivity {

    ImageView goback,navigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_subscribea);
        initial();




    }


    private void initial() {

        goback = findViewById(R.id.goback);
        navigationBar = findViewById(R.id.navigation_bar);
        navigationBar.getLayoutParams().height = getNavigationBarHeight();

        goback.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    Point navigationBarSize = new Point();
    public int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarSize.y = getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarSize.y;
    }
}