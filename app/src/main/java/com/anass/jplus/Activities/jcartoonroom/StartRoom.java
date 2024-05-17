package com.anass.jplus.Activities.jcartoonroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anass.jplus.R;


public class StartRoom extends AppCompatActivity {

    ImageView goback,navigationBar;
    RelativeLayout createroomnoew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_start_room);

        initial();
    }

    private void initial() {

        goback = findViewById(R.id.goback);
        navigationBar = findViewById(R.id.navigation_bar);
        createroomnoew = findViewById(R.id.createroomnoew);
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