package com.anass.jplus.Activities.Auth;


import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.anass.jplus.API.BEANLINKS;
import com.anass.jplus.Activities.AddPost;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.MainActivity;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.AuthResponse;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Signintojcartoon2 extends AppCompatActivity {

    static  public  HashMap<String, String> headers;
    static  public  HashMap<String, String> infousers;

    ImageView back;
    EditText usernameedit;
    RelativeLayout addimguser,cansle;
    LinearLayout loginbtn;
    CircleImageView logogjj;
    TextView textmsg,progresstxt;
    private ApiService apiService;

    MultipartBody.Part imagePart = null;

    String fullname , email ,password;
    String imagepath;

    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri imageuri;
    Bitmap Phoroprofile;
    ByteArrayOutputStream byteArrayOutputStream;

    LinearProgressIndicator progresaccount;

    ArrayList<String> usersid;
    AuthManager authManager ;


    ActivityResultLauncher<Intent> resultLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signintojcartoon2);

        apiService = ApiClient.getRetrofit().create(ApiService.class);
        back = findViewById(R.id.back);
        addimguser = findViewById(R.id.cccss);
        usernameedit = findViewById(R.id.usernameedit);
        loginbtn = findViewById(R.id.loginbtn);
        logogjj = findViewById(R.id.logogjj);
        textmsg = findViewById(R.id.textmsg);
        progresaccount = findViewById(R.id.progresaccount);
        progresstxt = findViewById(R.id.progresstxt);
        cansle = findViewById(R.id.cansle);

        fullname = getIntent().getStringExtra("fullname");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        byteArrayOutputStream = new ByteArrayOutputStream();
        registerResult();
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("User-Agent","Thunder Client (https://www.thunderclient.com)");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cansle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addimguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if ( usernameedit.getText().toString().isEmpty()){

                    showMessage("إسم المستخدم فارغ") ;


                }else{

                  LoginTojcartoon.PleaseWait.show(Signintojcartoon2.this);

                  progresaccount.setVisibility(View.VISIBLE);
                  progresstxt.setVisibility(View.VISIBLE);

                    Chakeifusernameisexist(usernameedit.getText().toString(),email,fullname,password,v);
                }
            }
        });


    }

    public  boolean isIntentAvailable(Intent intent) {
        final PackageManager mgr = getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult( ), new ActivityResultCallback<ActivityResult>( ) {
            @Override
            public void onActivityResult(ActivityResult result) {
                try {

                    imageuri = result.getData().getData();
                    Phoroprofile = MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                    logogjj.setImageBitmap(Phoroprofile);

                }catch (Exception e){
                Toast.makeText(Signintojcartoon2.this, "لم يتم تحديد اي صورة", Toast.LENGTH_SHORT).show( );}
            }
        });
    }

    private void Chakeifusernameisexist(String username, String email, String fullname , String password, View v) {

        textmsg.setVisibility(View.INVISIBLE);
        Log.d("TAG", "onResponse: start"+username);


        apiService.checkusernamejcartoon(username).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if (response.body() != null){


                    String msj = response.body().getMessage();
                    LoginTojcartoon.PleaseWait.dismiss();

                    if (msj.equals("exist")){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            progresaccount.setProgress(0,true);
                        }else {
                            progresaccount.setProgress(0);

                        }
                        progresstxt.setVisibility(View.GONE);
                        progresstxt.setText("هدا الإسم  محجوز من قبل , جرب إسم أخر");
                    }else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            progresaccount.setProgress(50,true);
                        }else {
                            progresaccount.setProgress(50);
                        }

                        progresstxt.setText("تهيئة الحساب  إنتضر رجاء حتي تنتهي");

                        CreateUserAccount(email,username,password,fullname);
                    }




                }



            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

                Toast.makeText(Signintojcartoon2.this, "خطا في الإتصال  "+t.getMessage(), Toast.LENGTH_SHORT).show( );

            }
        });




    }

    private void CreateUserAccount(String email, final String username, String password, String fullname) {


        String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        TelephonyManager tm = (TelephonyManager)Signintojcartoon2.this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso();

        String imagepostse = null;

        if (imageuri != null){

            imagepostse = getRealPathFromURI(imageuri, Signintojcartoon2.this);
            File imageFile = new File(imagepostse);
            Log.d("TAG", "ss image: "+imageFile.getName());

            // Create a request body with file and part headers
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        }else {

            RequestBody nullBody = RequestBody.create(MediaType.parse("image/*"), "");
            imagePart = MultipartBody.Part.createFormData("image", "", nullBody);
        }


        Call<AuthResponse> responsCall = apiService
                .signin(username,fullname,email,countryCode,password,device_id,imagePart);

        responsCall.enqueue(new Callback<AuthResponse>( ) {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.body() != null){

                    UserModel userModel = response.body().getUser();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progresaccount.setProgress(100,true);
                    }else {
                        progresaccount.setProgress(100);
                    }

                    progresstxt.setText("تم التسجيل بنجاح");
                    authManager.saveSettings(userModel);

                    updateUI();

                }else {

                    Toast.makeText(Signintojcartoon2.this, "هناك مشكلة في الإتصال", Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(Signintojcartoon2.this, "هناك مشكلة في الإتصال"+t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });




    }



    public void updateUI() {

        Intent intent = new Intent(Signintojcartoon2.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }


    private void openGallery() {


        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(isIntentAvailable(i)){
            resultLauncher.launch(i);
        }

    }

    public static String getRealPathFromURI(Uri contentUri, Activity activity) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(Signintojcartoon2.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Signintojcartoon2.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Signintojcartoon2.this,"أرجوك إقبل صلاحيات التطبيق",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Signintojcartoon2.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
                openGallery();
            }

        }
        else
            openGallery();

    }
}