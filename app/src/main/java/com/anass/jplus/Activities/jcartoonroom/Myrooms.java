package com.anass.jplus.Activities.jcartoonroom;

import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anass.jplus.Activities.MyList.Mylist;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Activities.jcartoonroom.AllRooms;
import com.anass.jplus.Adapters.RoomAdapter;
import com.anass.jplus.Models.RoomModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.interfaces.ApiService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Myrooms extends AppCompatActivity {

    ImageView goback,navigationBar;
    RecyclerView roomrv;
    RelativeLayout addroom;

    AuthManager authManager;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("rooms");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_myrooms);
        initial();
    }

    private void initial() {
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        goback = findViewById(R.id.goback);
        navigationBar = findViewById(R.id.navigation_bar);
        roomrv = findViewById(R.id.roomrv);
        addroom = findViewById(R.id.addroom);
        navigationBar.getLayoutParams().height = getNavigationBarHeight();

        goback.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addroom.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( Myrooms.this,CreateRoom.class ));
            }
        });



        db.addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RoomModel> roomModels = new ArrayList<>();

                for (DataSnapshot room : snapshot.getChildren()) {

                    Integer whoCreateRoomUserId = room.child("whoCreateRoomUserId").getValue(Integer.class);

                    if (whoCreateRoomUserId != null && whoCreateRoomUserId.equals(authManager.getUserInfo().getId())) {
                        RoomModel roomModel = new RoomModel(
                                room.child("id").getValue(String.class),
                                room.child("title").getValue(String.class),
                                whoCreateRoomUserId,
                                room.child("invitaionCode").getValue(String.class),
                                room.child("timeCreted").getValue(String.class),
                                room.child("tvId").getValue(Integer.class),
                                room.child("seasonId").getValue(Integer.class),
                                room.child("epeId").getValue(Integer.class),
                                room.child("typeIspublicPrivate").getValue(String.class),
                                room.child("numberLimit").getValue(Integer.class)
                        );

                        roomModels.add(roomModel);
                    }

                }


                roomrv.setLayoutManager(new LinearLayoutManager(Myrooms.this,RecyclerView.VERTICAL,false));
                roomrv.setAdapter(new RoomAdapter(Myrooms.this,roomModels));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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