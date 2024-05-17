package com.anass.jplus.Activities.jcartoonroom;

import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import static org.apache.commons.lang3.RandomStringUtils.random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.Models.RoomModel;
import com.anass.jplus.Models.SeasonModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.interfaces.ApiService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateRoom extends AppCompatActivity {

    AutoCompleteTextView tvormovie,cartoonnametxt,seasonnumbtxt,epenumbertxt;

    String[] listtype = {"مسلسل","فيلم"};
    String[] listtype2 = {"tv","movie"};

    String is7ark = "public";
    String typecartoon = "tv";
    int Cartoonid = 0;
    int Seasonid = 0;
    int Epeid = 0;
    RadioGroup grouiprado;
    ImageView goback,navigationBar;
    RelativeLayout createroomnoew,seasonrelat;
    AuthManager authManager;

    CartoonModel cartoonModel;
    SeasonModel seasonModel = null;
    EpesodModel epesodModel;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("rooms");
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_create_room);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        initial();


    }

    private void initial() {
        tvormovie = findViewById(R.id.tvormovie);
        grouiprado = findViewById(R.id.grouiprado);
        cartoonnametxt = findViewById(R.id.cartoonnametxt);
        seasonnumbtxt = findViewById(R.id.seasonnumbtxt);
        epenumbertxt = findViewById(R.id.epenumbertxt);

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


        cartoonModel = (CartoonModel) getIntent( ).getSerializableExtra("cartoon");
        seasonModel = (SeasonModel) getIntent( ).getSerializableExtra("season");
        epesodModel = (EpesodModel) getIntent( ).getSerializableExtra("epe");

        createroomnoew.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                String invitaion_code = randomNumeric(7);

                DatabaseReference databaseReference = db;
                String uniqueId = databaseReference.push().getKey();

                if (cartoonModel.getCountentType().equals("tv")){

                    Creatnoew(seasonModel.getId(),uniqueId,invitaion_code,databaseReference);
                    /*db.addValueEventListener(new ValueEventListener( ) {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<RoomModel> roomModels = new ArrayList<>();

                            for (DataSnapshot room : snapshot.getChildren()) {
                                Integer userId = room.child("whoCreateRoomUserId").getValue(Integer.class);
                                if (userId != null && userId.equals(authManager.getUserInfo().getId())) {
                                    RoomModel roomModel = createRoomModel(room);
                                    roomModels.add(roomModel);
                                }
                            }


                            int roombunber = roomModels.size();

                            if (authManager.getUserInfo().getRoomsNumber() == roombunber){
                                Toast.makeText(CreateRoom.this, "الحد المسموع هو 5 غرف لكل شخص , احدف غرفة أخرى أو تواصل مع فريق جي كرتون", Toast.LENGTH_LONG).show( );

                            }else {



                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

                }else {

                 /*   db.addValueEventListener(new ValueEventListener( ) {
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


                            int roombunber = roomModels.size();

                            if (authManager.getUserInfo().getRoomsNumber() == roombunber){
                                Toast.makeText(CreateRoom.this, "الحد المسموع هو 5 غرف لكل شخص , احدف غرفة أخرى أو تواصل مع فريق جي كرتون", Toast.LENGTH_LONG).show( );

                            }else {

                                Creatnoew(1,uniqueId,invitaion_code,databaseReference);

                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

                    Creatnoew(1,uniqueId,invitaion_code,databaseReference);

                }





            }
        });


        grouiprado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.publicrad:
                        is7ark = "public";
                        break;
                    case R.id.privatepag:
                        is7ark = "private";
                        break;


                }
            }
        });

        tvormovie.setHint(cartoonModel.getCountentType());
        cartoonnametxt.setHint(cartoonModel.getTitle());
        seasonnumbtxt.setHint(seasonModel.getName());
        epenumbertxt.setHint(epesodModel.getLebel());




    }
    private RoomModel createRoomModel(DataSnapshot room) {
        return new RoomModel(
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
    }
    private void Creatnoew(Integer id, String uniqueId, String invitaion_code, DatabaseReference databaseReference) {

        RoomModel roomModel = new RoomModel(uniqueId,
                cartoonModel.getTitle(),
                authManager.getUserInfo().getId()
                ,invitaion_code
                ,String.valueOf(System.currentTimeMillis())
                ,cartoonModel.getId()
                ,id,
                epesodModel.getId()
                ,is7ark,
                authManager.getUserInfo().getRoomsNumber()
        );

        databaseReference.child(uniqueId).setValue(roomModel).addOnSuccessListener(new OnSuccessListener<Void>( ) {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(CreateRoom.this, "تم إنشاء الغرفة بنجاح  , إدهب إلي غرفي ", Toast.LENGTH_SHORT).show( );
                finish();

            }
        }).addOnFailureListener(new OnFailureListener( ) {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateRoom.this, e.getMessage()+" هناك مشكلة ", Toast.LENGTH_SHORT).show( );
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

    public  String randomNumeric(final int count) {
        return random(count, true, true);
    }
}