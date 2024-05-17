package com.anass.jplus.Activities.Auth;



import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.anass.jplus.API.MovieApi;
import com.anass.jplus.API.Respons.LoginResponse;
import com.anass.jplus.API.Servicy;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.MainActivity;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.AuthResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTojcartoon extends AppCompatActivity {


    EditText emailedit,passedit;
    RelativeLayout gotosignin;
    LinearLayout loginbtn;

    ImageView skip;
    private ApiService apiService;

    AuthManager authManager ;

    String email,password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_IMMERSIVE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.logintojcartoonac);
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        ImageView closlogin = findViewById(R.id.closlogin);
        closlogin.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gotosignin = findViewById(R.id.gotosignin);
        emailedit = findViewById(R.id.emailedit);
        passedit = findViewById(R.id.passedit);
        loginbtn = findViewById(R.id.loginbtn);
        skip = findViewById(R.id.skip);




        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginTojcartoon.this,Signintojcartoon.class));
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginTojcartoon.this,MainActivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailedit.getText().toString();
                password = passedit.getText().toString();

                if (email.isEmpty()){
                    Snackbar.make(v,"الإيميل فارغ",5000).show();
                    Toast.makeText(LoginTojcartoon.this, "الإيميل فارغ", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    {
                        Snackbar.make(v,"كلمة المرور فارغة",5000).show();
                       Toast.makeText(LoginTojcartoon.this, "كلمة المرور فارغة", Toast.LENGTH_SHORT).show();
                    }

                }else {
                   PleaseWait.show(LoginTojcartoon.this);

                    signIn(email,password);
                }

            }
        });




    }






    @Override
    protected void onStart() {
        super.onStart();
        /*if (authManager.getUserInfo().getToken() != null){
          updateUI();
        }*/
    }



    private void signIn(String mail, String password) {





        Call<AuthResponse> authResponseCall = apiService
                .login(
                        mail,password
                );

        authResponseCall.enqueue(new Callback<AuthResponse>( ) {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.body() != null){
                    if (response.body().getUser() != null){
                        UserModel userModel = response.body().getUser();
                        PleaseWait.dismiss();

                        authManager.saveSettings(userModel);
                        updateUI();
                    }



                }else {
                    PleaseWait.dismiss();


                    Toast.makeText(LoginTojcartoon.this, " هناك مشكلة في تسجيل الدخول", Toast.LENGTH_LONG).show();



                }


            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                PleaseWait.dismiss();
                Toast.makeText(LoginTojcartoon.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });






    }

    public void updateUI() {

        finish();
        Intent intent = new Intent(LoginTojcartoon.this, MainActivity.class);
        startActivity(intent);

    }

    public static class PleaseWait {

        static Dialog wait;

        public static void show(Context context){
            wait = new Dialog(context);
            wait.setContentView(R.layout.please_wait);
            wait.setCancelable(false);
            wait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            wait.show();
        }

        public static void dismiss(){
            wait.dismiss();
        }
    }
}