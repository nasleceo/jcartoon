package com.anass.jplus.Activities;

import static com.anass.jplus.Activities.AddPost.changestatucolor;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.getRealPathFromURI;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.API.BEANLINKS;
import com.anass.jplus.Activities.Auth.LoginTojcartoon;
import com.anass.jplus.Activities.Auth.Signintojcartoon2;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Config.config;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.AuthResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfil extends AppCompatActivity {

    AuthManager mAuth;
    TextView textmsg;
    EditText onenameedit,usernameedit,editpost;
    RelativeLayout AddImage;
    MultipartBody.Part imagePart = null;

    private ApiService apiService;

    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    static int REQUESCODECOVER = 2 ;
    Bitmap bitmapprofil,bitmapcover;
    ByteArrayOutputStream byteArrayOutputStream;

    ImageView userimg,backch,addimgprof;
    String fullname,username,cover,profile,desc,email,password,isking,showlist;
    Uri imageuri;
    Bitmap Phoroprofile;

    ActivityResultLauncher<Intent> resultLauncher;

    ArrayList<String> usersid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changestatucolor(this);
        setContentView(R.layout.activity_edit_profil);

        mAuth = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        backch = findViewById(R.id.backch);
        userimg = findViewById(R.id.userimg);
        addimgprof = findViewById(R.id.addimgprof);
        onenameedit = findViewById(R.id.onenameedit);
        usernameedit = findViewById(R.id.usernameedit);
        textmsg = findViewById(R.id.textmsg);
        editpost = findViewById(R.id.editpost);
        AddImage = findViewById(R.id.AddImage);

        apiService = ApiClient.getRetrofit().create(ApiService.class);
        registerResult();
        //Image Profil
        Glide.with(this).load(mAuth.getUserInfo().getProfil()).into(userimg);

        editpost.setText(mAuth.getUserInfo().getProfileDesc());
        onenameedit.setText(mAuth.getUserInfo().getName());

        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginTojcartoon.PleaseWait.show(EditProfil.this);

                if (usernameedit.getText().toString().isEmpty()){
                    Editacc();
                }else {
                    Chakeifusernameisexist(usernameedit.getText().toString());
                }



            }
        });


        backch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission();
            }
        });



    }
    private void Chakeifusernameisexist(String username) {


        AndroidNetworking.post(BEANLINKS.BASELINK+"auth/checkusernamejcartoon")
                .addQueryParameter("userspecial_name",username)
                .addHeaders(headers)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("TAG", "onResponse: "+response.toString());
                        if (response.toString().contains("exist")){
                            LoginTojcartoon.PleaseWait.dismiss();
                            textmsg.setVisibility(View.VISIBLE);

                        }else {
                            textmsg.setVisibility(View.INVISIBLE);

                            Editacc();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });




    }
    private void Editacc() {

        // this method create user account with specific email and password
        String imagepostse = null;


        String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        TelephonyManager tm = (TelephonyManager)EditProfil.this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso();



        if (imageuri != null){

            imagepostse = getRealPathFromURI(imageuri,EditProfil.this);
            File imageFile = new File(imagepostse);
            Log.d("TAG", "ss image: "+imageFile.getName());

            // Create a request body with file and part headers
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        }else {

            RequestBody nullBody = RequestBody.create(MediaType.parse("image/*"), "");
            imagePart = MultipartBody.Part.createFormData("image", "", nullBody);
        }



        String profile_desc = editpost.getText().toString();
        String userspecial_name = usernameedit.getText().toString().isEmpty() ? mAuth.getUserInfo().getUserspecialName() : usernameedit.getText().toString() ;
        String name = onenameedit.getText().toString().isEmpty() ? mAuth.getUserInfo().getName() :  onenameedit.getText().toString() ;


        Call<AuthResponse> responsCall = apiService
                .editprofi(mAuth.getUserInfo().getId(),profile_desc,userspecial_name,name,countryCode,device_id,imagePart);

        responsCall.enqueue(new Callback<AuthResponse>( ) {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.body() != null){
                    LoginTojcartoon.PleaseWait.dismiss();
                    UserModel userModel = response.body().getUser();
                    mAuth.saveSettings(userModel);
                    finish();

                }else {
                    LoginTojcartoon.PleaseWait.dismiss();
                    Toast.makeText(EditProfil.this, "هناك مشكلة في الإتصال" + response.message(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                LoginTojcartoon.PleaseWait.dismiss();
                Toast.makeText(EditProfil.this, "  هناك مشكلة " + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(EditProfil.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfil.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(EditProfil.this,"أرجوك إقبل صلاحيات التطبيق",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(EditProfil.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            openGallery();

    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult( ), new ActivityResultCallback<ActivityResult>( ) {
            @Override
            public void onActivityResult(ActivityResult result) {
                try {

                    imageuri = result.getData().getData();
                    Phoroprofile = MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                    userimg.setImageBitmap(Phoroprofile);

                }catch (Exception e){
                    Toast.makeText(EditProfil.this, "لم يتم تحديد اي صورة", Toast.LENGTH_SHORT).show( );}
            }
        });
    }
    private void openGallery() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(isIntentAvailable(i)){
            resultLauncher.launch(i);
        }

    }

    public  boolean isIntentAvailable(Intent intent) {
        final PackageManager mgr = getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }



}