package com.anass.jplus.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Activities.Auth.LoginTojcartoon;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Activities.jcartoonroom.WatchRoom;
import com.anass.jplus.Config.OverlapDecoration;
import com.anass.jplus.Fragments.Jsocity;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.Models.MessageModel;
import com.anass.jplus.Models.RoomModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.interfaces.ApiService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MYview> {

    Activity context;
    List<RoomModel> roomModels;
    AuthManager authManager;
    DatabaseReference db;
    private ApiService apiService;

    List<UserModel> users = new ArrayList<>();
    SharedPreferences sharedPreferences;

    public RoomAdapter(Activity context, List<RoomModel> roomModels) {
        this.context = context;
        this.roomModels = roomModels;
        authManager = new AuthManager(context.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        db = FirebaseDatabase.getInstance().getReference().child("rooms");
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        sharedPreferences = context.getSharedPreferences("roomtimes", Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public MYview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYview(LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MYview holder, int position) {

        RoomModel room = roomModels.get(position);

        holder.texttitle.setText(room.getTitle());

        holder.profileuserss.addItemDecoration(new OverlapDecoration());

        holder.profileuserss.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        holder.moreoption_menu.setVisibility(View.GONE);
        holder.settingofroom.setVisibility(View.GONE);

        if (authManager.getUserInfo().getId() > 0){
            if (room.getWhoCreateRoomUserId().equals(authManager.getUserInfo().getId())){
                holder.settingofroom.setVisibility(View.VISIBLE);
                holder.moreoption_menu.setVisibility(View.VISIBLE);
                holder.moreoption_menu.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view) {


                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.deletepost);
                        dialog.setCancelable(true);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                        ImageView delete_file = dialog.findViewById(R.id.delete_file);
                        ImageView close = dialog.findViewById(R.id.close);
                        delete_file.setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View view) {
                                db.child(room.getId()).setValue(null);
                                dialog.dismiss();
                            }
                        });
                        close.setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });



                        dialog.show();


                    }
                });

                holder.settingofroom.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view) {


                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.roomsetting);
                        dialog.setCancelable(true);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



                        ImageView copycode = dialog.findViewById(R.id.copycode);
                        copycode.setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View view) {
                                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

                                // Create a new ClipData object
                                ClipData clip = ClipData.newPlainText("Copied Text", room.getInvitaionCode());

                                // Set the ClipData on the clipboard
                                clipboard.setPrimaryClip(clip);

                                // Show a toast message to indicate that the text has been copied
                                Toast.makeText(context, "تم نسخ الرمز", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        });
                        String[] listcomments2 = {"10","50","100"};
                        int[] listofdb = {10,50,100};

                        AutoCompleteTextView autoCompleteTextView = dialog.findViewById(R.id.menuoftypes);

                        autoCompleteTextView.setHint(listcomments2[0]);

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context,
                                R.layout.custom_spinner,listcomments2);

                        autoCompleteTextView.setAdapter(adapter2);

                        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                HashMap<String, Object> upp = new HashMap<String, Object>(  );
                                upp.put("numberLimit",listofdb[i]);
                                db.child(room.getId()).updateChildren(upp);


                            }
                        });


                        dialog.show();


                    }
                });

            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (authManager.getUserInfo().getId() > 0){

                    if (room.getNumberLimit() == users.size()){
                        Toast.makeText(context, "الغرفة وصلت حدودها", Toast.LENGTH_SHORT).show( );
                    }else {
                        LoginTojcartoon.PleaseWait.show(context);
                        apiService.getoneEpisode(room.getEpeId()).enqueue(new Callback<EpesodModel>( ) {
                            @Override
                            public void onResponse(Call<EpesodModel> call, Response<EpesodModel> response) {

                                if (response.body() != null){
                                    LoginTojcartoon.PleaseWait.dismiss();

                                    EpesodModel epesodModel = response.body();

                                    Intent intent = new Intent(context, WatchRoom.class);
                                    intent.putExtra("episode",epesodModel);
                                    intent.putExtra("roomid", String.valueOf(room.getId()));
                                    context.startActivity(intent);
                                }else {
                                    LoginTojcartoon.PleaseWait.dismiss();
                                    Toast.makeText(context, "هناك مشكلة في الإتصال , حاول مجددا", Toast.LENGTH_SHORT).show( );
                                }



                            }

                            @Override
                            public void onFailure(Call<EpesodModel> call, Throwable t) {
                                Toast.makeText(context, "هناك مشكلة في الإتصال , حاول مجددا", Toast.LENGTH_SHORT).show( );

                                LoginTojcartoon.PleaseWait.dismiss();
                            }
                        });
                    }



                }else {
                    ShowsnackLogin(view,context);
                }







            }
        });

        getRoomUsers(room,holder);
        getRoommsjs(room,holder);
        getEpeinfo(room,holder);


    }



    private void getEpeinfo(RoomModel room, MYview holder) {

        apiService.getEpisodes(room.getSeasonId()).enqueue(new Callback<List<EpesodModel>>( ) {
            @Override
            public void onResponse(Call<List<EpesodModel>> call, Response<List<EpesodModel>> response) {
                EpesodModel epesodModel = new EpesodModel();
                for (EpesodModel epesodModels:response.body() ) {
                    if (epesodModels.getId().equals(room.getEpeId())){
                        epesodModel.setId(epesodModels.getId());
                        epesodModel.setLebel(epesodModels.getLebel());
                    }
                }

                if (epesodModel.getLebel() == null){
                    holder.getnameepe.setVisibility(View.GONE);
                }
                holder.getnameepe.setText(epesodModel.getLebel() + " الحلقة ");
            }

            @Override
            public void onFailure(Call<List<EpesodModel>> call, Throwable t) {

            }
        });



    }

    private void getRoommsjs(RoomModel room, MYview holder) {

        List<MessageModel> users = new ArrayList<>();
        db.child(room.getId()).child("message").addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                holder.count_roommsj.setText(snapshot.getChildrenCount()+"");

             /*   for (DataSnapshot room : snapshot.getChildren()) {
                    // Assuming you have a constructor in RoomModel to set values
                    MessageModel messageModel = new MessageModel(
                            room.child("imageProfile").getValue(String.class),
                            room.child("message").getValue(String.class),
                            room.child("nameUser").getValue(String.class),
                            room.child("time").getValue(Long.class),
                            room.child("userId").getValue(Integer.class)
                    );
                    users.add(messageModel);

                }*/



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void getRoomUsers(RoomModel room, MYview holder) {

        db.child(room.getId()).child("users").addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                holder.count_room.setText(snapshot.getChildrenCount()+"");

                for (DataSnapshot room : snapshot.getChildren()) {
                    // Assuming you have a constructor in RoomModel to set values
                    UserModel roomModel = new UserModel();
                    roomModel.setId(room.child("id").getValue(Integer.class ));
                    roomModel.setName(room.child("name").getValue(String.class ));
                    roomModel.setProfil(room.child("profil").getValue(String.class ));

                    users.add(roomModel);

                }

                Jsocity.OverlapFollowesAdapter overlapFollowesAdapter = new Jsocity.OverlapFollowesAdapter(context,users);
                holder.profileuserss.setAdapter(overlapFollowesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return roomModels.size();
    }

    private void OpenpopMenu() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.menuposter_item);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;



        dialog.show();
        dialog.getWindow().setAttributes(lp);


    }

    public class MYview extends RecyclerView.ViewHolder {
        RecyclerView profileuserss;
        ImageView moreoption_menu,settingofroom;

        TextView texttitle,count_room,count_roommsj,getnameepe;
        public MYview(@NonNull View itemView) {
            super(itemView);


            count_room = itemView.findViewById(R.id.count_room);
            count_roommsj = itemView.findViewById(R.id.count_roommsj);
            profileuserss = itemView.findViewById(R.id.profileuserss);
            texttitle = itemView.findViewById(R.id.texttitle);
            getnameepe = itemView.findViewById(R.id.getnameepe);
            moreoption_menu = itemView.findViewById(R.id.moreoption_menu);
            settingofroom = itemView.findViewById(R.id.settingofroom);
        }
    }
}