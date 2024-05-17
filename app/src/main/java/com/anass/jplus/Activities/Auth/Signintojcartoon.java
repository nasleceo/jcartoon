package com.anass.jplus.Activities.Auth;

import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anass.jplus.API.BEANLINKS;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Signintojcartoon extends AppCompatActivity {

    ImageView back;
    TextInputEditText onenameedit,emaileditnew,passedit;
    RelativeLayout gotopagename;

    String fullname , email ,password;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_IMMERSIVE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signintojcartoon);
        apiService = ApiClient.getRetrofit( ).create(ApiService.class);

        back = findViewById(R.id.back);
        gotopagename = findViewById(R.id.gotopagename);
        onenameedit = findViewById(R.id.onenameedit);
        emaileditnew = findViewById(R.id.emaileditnew);
        passedit = findViewById(R.id.passedit);



        gotopagename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = onenameedit.getText().toString();
                email = emaileditnew.getText().toString();
                password = passedit.getText().toString();

                if (email.isEmpty() ){

                    Toast.makeText(Signintojcartoon.this, "البريد الإلكتروني فارغ", Toast.LENGTH_SHORT).show();

                }else if(password.isEmpty()  ){

                        Toast.makeText(Signintojcartoon.this, "كلمة المرور فارغة", Toast.LENGTH_SHORT).show();


                }else if( password.length() < 6  ){

                        Toast.makeText(Signintojcartoon.this, "كلمة المرور أقل من 6 احرف", Toast.LENGTH_SHORT).show();


                }else if(fullname.isEmpty()){

                        Toast.makeText(Signintojcartoon.this, "الإسم الكامل فارغ", Toast.LENGTH_SHORT).show();


                }else {
                    LoginTojcartoon.PleaseWait.show(Signintojcartoon.this);

                    apiService.checkuseremail(email).enqueue(new Callback<MessageResponse>( ) {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                            if (response.body() != null){

                                String msg = response.body().getMessage();
                                LoginTojcartoon.PleaseWait.dismiss();

                                if (msg.equals("exist")){
                                    Snackbar.make(v,"هدا الإيميل محجوز من قبل , جرب إيميل أخر",5000).show();

                                }else {
                                    Intent sendDataToDetailsActivity = new Intent(Signintojcartoon.this, Signintojcartoon2.class);
                                    sendDataToDetailsActivity.putExtra("fullname",fullname);
                                    sendDataToDetailsActivity.putExtra("email",email);
                                    sendDataToDetailsActivity.putExtra("password",password);
                                    startActivity(sendDataToDetailsActivity);
                                }


                            }



                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });

                }

            }
        });







        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}