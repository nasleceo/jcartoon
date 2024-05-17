package com.anass.jplus.Activities.jcartoonroom;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Activities.Auth.LoginTojcartoon;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.RoomAdapter;
import com.anass.jplus.Config.OverlapDecoration;
import com.anass.jplus.Fragments.Jsocity;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.Models.RoomModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.interfaces.ApiService;
import com.facebook.all.All;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRooms extends AppCompatActivity {

    ImageView goback,navigationBar;
    RecyclerView roomrv;
    RelativeLayout getsecretroom;
    AuthManager authManager;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("rooms");

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_all_rooms);
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        initial();



    }

    private void initial() {
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        getsecretroom = findViewById(R.id.getsecretroom);
        goback = findViewById(R.id.goback);
        roomrv = findViewById(R.id.roomrv);
        navigationBar = findViewById(R.id.navigation_bar);
        navigationBar.getLayoutParams().height = getNavigationBarHeight();

        goback.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        getsecretroom.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() > 0){

                    OPENdIALOGlock();


                }else {
                    ShowsnackLogin(view,AllRooms.this);
                }

            }
        });


        db.addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RoomModel> roomModels = new ArrayList<>();

                for (DataSnapshot room : snapshot.getChildren()) {

                    String typeIspublicPrivate = room.child("typeIspublicPrivate").getValue(String.class);

                    if (typeIspublicPrivate.equals("public")) {
                        RoomModel roomModel = new RoomModel(
                                room.child("id").getValue(String.class),
                                room.child("title").getValue(String.class),
                                room.child("whoCreateRoomUserId").getValue(Integer.class),
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


                roomrv.setLayoutManager(new LinearLayoutManager(AllRooms.this,RecyclerView.VERTICAL,false));
                roomrv.setAdapter(new RoomAdapter(AllRooms.this,roomModels));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void OPENdIALOGlock() {

        final Dialog dialog = new Dialog(AllRooms.this);
        dialog.setContentView(R.layout.addkeydialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;


        TextInputEditText sendcomentedit = dialog.findViewById(R.id.sendcomentedit);
        LinearLayout unlockroom = dialog.findViewById(R.id.unlockroom);


        unlockroom.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (!sendcomentedit.getText().toString().isEmpty()){

                    String code = sendcomentedit.getText().toString();

                    // Assuming "invitationCode" is the key in your Firebase Realtime Database representing the invitation code
                    Query query = db.orderByChild("invitaionCode").equalTo(code);
                    LoginTojcartoon.PleaseWait.show(AllRooms.this);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                    DataSnapshot room = dataSnapshot.getChildren().iterator().next();
                                RoomModel roomModel = new RoomModel(
                                        room.child("id").getValue(String.class),
                                        room.child("title").getValue(String.class),
                                        room.child("whoCreateRoomUserId").getValue(Integer.class),
                                        room.child("invitaionCode").getValue(String.class),
                                        room.child("timeCreted").getValue(String.class),
                                        room.child("tvId").getValue(Integer.class),
                                        room.child("seasonId").getValue(Integer.class),
                                        room.child("epeId").getValue(Integer.class),
                                        room.child("typeIspublicPrivate").getValue(String.class),
                                        room.child("numberLimit").getValue(Integer.class)
                                );


                                apiService.getoneEpisode(roomModel.getEpeId()).enqueue(new Callback<EpesodModel>( ) {
                                    @Override
                                    public void onResponse(Call<EpesodModel> call, Response<EpesodModel> response) {

                                        if (response.body() != null){
                                            LoginTojcartoon.PleaseWait.dismiss();

                                            EpesodModel epesodModel = response.body();

                                            Intent intent = new Intent(AllRooms.this, WatchRoom.class);
                                            intent.putExtra("episode",epesodModel);
                                            intent.putExtra("roomid", String.valueOf(roomModel.getId()));
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }else {
                                            LoginTojcartoon.PleaseWait.dismiss();
                                            Toast.makeText(AllRooms.this, "هناك مشكلة في الإتصال , حاول مجددا", Toast.LENGTH_SHORT).show( );
                                        }



                                    }

                                    @Override
                                    public void onFailure(Call<EpesodModel> call, Throwable t) {
                                        Toast.makeText(AllRooms.this, "هناك مشكلة في الإتصال , حاول مجددا", Toast.LENGTH_SHORT).show( );

                                        LoginTojcartoon.PleaseWait.dismiss();
                                    }
                                });



                            } else {
                                LoginTojcartoon.PleaseWait.dismiss();
                                // No room found with the specified invitation code
                                Toast.makeText(AllRooms.this, "لم يتم العثور على غرفة بهذا الرمز", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            LoginTojcartoon.PleaseWait.dismiss();
                            // Handle database error
                            Toast.makeText(AllRooms.this, "حدث خطأ أثناء البحث عن الغرفة", Toast.LENGTH_SHORT).show();
                        }
                    });


                }else {
                    Toast.makeText(AllRooms.this, "أدخل الرمز", Toast.LENGTH_SHORT).show( );
                }



            }
        });



        dialog.show();
        dialog.getWindow().setAttributes(lp);




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
