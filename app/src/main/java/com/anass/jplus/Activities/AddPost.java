package com.anass.jplus.Activities;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import static com.anass.jplus.Activities.Auth.Signintojcartoon2.getRealPathFromURI;
import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Activities.Auth.LoginTojcartoon;
import com.anass.jplus.Activities.Auth.Signintojcartoon2;
import com.anass.jplus.Activities.MyList.Mylist;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.MainActivity;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;

import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.AuthResponse;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPost extends AppCompatActivity {

    private ApiService apiService;

    SharedPreferences sharedPreferences;

    Uri pickedImgUri ;

    String PostType = null;
    String is7ark = "0";
    String Comments_type = "0";

    AutoCompleteTextView auto_complet,menuoftcoments;
    TextInputEditText contenttxtpost;
    CircleImageView cast1,cast2;
    String[] listtype = {"منشور","نظرية أو مراجعة"};
    String[] listtype2 = {"post","nadaria"};

    String[] listcomments = {"نعم","لا"};
    String[] listcomments2 = {"0","1"};
    ImageView backch,imagepost,cartoon_id_photo;
    TextView waithbro,cartoon_title;
    LinearLayout post_view,morajaa_view,mowajaha_view,allview;
    RelativeLayout addpostbtn;

    RadioGroup grouipradohark;

    AuthManager authManager;
    String CartoonID,CartoonName,CartoonImg;
    public String castID,castImg;
    public String castID2,castImg2;
    MultipartBody.Part imagePart = null;
    long currentTime;
    ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changestatucolor(this);
        setContentView(R.layout.activity_add_post);

        initialv();


        addpostbtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (authManager.getUserInfo().getId() > 0){
                    LoginTojcartoon.PleaseWait.show(AddPost.this);
                    Addpostn(view);
                   /* if (PostType != null  && Comments_type  != null){
                        if (!PostType.isEmpty() && !Comments_type.isEmpty()){
                            switch (PostType){
                                case  "post":


                                    break;
                                case  "nadaria":
                                    LoginTojcartoon.PleaseWait.show(AddPost.this);
                                    // morajaa_view.setVisibility(View.VISIBLE);
                                    if (!CartoonID.isEmpty()){
                                        addnadaria(CartoonID,view);
                                    }else {
                                        Snackbar.make(view,"المرجو إختيار  كرتون معين",3000).show();
                                    }

                                    break;

                                case  "mowajaha":
                                    //mowajaha_view.setVisibility(View.VISIBLE);
                                    LoginTojcartoon.PleaseWait.show(AddPost.this);
                                    if (!castID.isEmpty() && !castID2.isEmpty()){
                                        addmowajaha(view);
                                    }else {
                                        Snackbar.make(view,"المرجو إختيار  شخصية  معين",3000).show();
                                    }

                                    break;

                            }
                        }
                    }else {
                        Snackbar.make(view,"المرجو إختيار نوع المنشور",9000).show();
                    }*/
                }else {

                    ShowsnackLogin(view,AddPost.this);

                }





            }
        });

    }

    private void addnadaria(String cartoon_id,View view) {

        String textPost = contenttxtpost.getText().toString();

            RequestBody nullBody = RequestBody.create(MediaType.parse("image/*"), "");
            imagePart = MultipartBody.Part.createFormData("image", "", nullBody);


        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = convertCodeToCountryName(tm.getNetworkCountryIso());



        Call<MessageResponse> addpostcall = apiService
                .addpost(
                        String.valueOf(authManager.getUserInfo().getId()),
                        textPost,
                        "nadariat",
                        String.valueOf(System.currentTimeMillis()),
                        is7ark,
                        countryCode,
                        Comments_type,
                        cartoon_id,
                        null,
                        null,
                        null,
                        "Unpublished",
                        imagePart);

        addpostcall.enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                Snackbar.make(view,"تم إضافة منشورك بنجاح",4000).show();
                currentTime = System.currentTimeMillis();
                sharedPreferences.edit().putLong("time", currentTime).apply();
                LoginTojcartoon.PleaseWait.dismiss();
                finish();


            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                LoginTojcartoon.PleaseWait.dismiss();

                if (t.getMessage().equals("timeout")){
                    Toast.makeText(AddPost.this, "الإنترنت ضعيفة, جرب مرةأخرى", Toast.LENGTH_SHORT).show( );
                }else {
                    Toast.makeText(AddPost.this, "هناك خطأ , جرب مرةأخرى", Toast.LENGTH_SHORT).show( );

                }
                System.out.println("Message: " + t.getMessage());

            }
        });





    }
    private void addmowajaha(View view) {

        String textPost = contenttxtpost.getText().toString();

        RequestBody nullBody = RequestBody.create(MediaType.parse("image/*"), "");
        imagePart = MultipartBody.Part.createFormData("image", "", nullBody);


        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = convertCodeToCountryName(tm.getNetworkCountryIso());



        Call<MessageResponse> addpostcall = apiService
                .addpost(
                        String.valueOf(authManager.getUserInfo().getId()),
                        textPost,
                        "mawajaha",
                        String.valueOf(System.currentTimeMillis()),
                        is7ark,
                        countryCode,
                        Comments_type,
                        null,
                        null,
                        castID,
                        castID2,
                        "Unpublished",
                        imagePart);

        addpostcall.enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                LoginTojcartoon.PleaseWait.dismiss();

                Snackbar.make(view,"تم إضافة منشورك بنجاح",4000).show();


            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                LoginTojcartoon.PleaseWait.dismiss();

                if (t.getMessage().equals("timeout")){
                    Toast.makeText(AddPost.this, "الإنترنت ضعيفة, جرب مرةأخرى", Toast.LENGTH_SHORT).show( );
                }else {
                    Toast.makeText(AddPost.this, "هناك خطأ , جرب مرةأخرى", Toast.LENGTH_SHORT).show( );

                }
                System.out.println("Message: " + t.getMessage());

            }
        });





    }

    public void Addpostn(View view) {

        String textPost = contenttxtpost.getText().toString();
        String imagepostse = null;

            if (pickedImgUri != null){

                imagepostse = getRealPathFromURI(pickedImgUri,AddPost.this);
                File imageFile = new File(imagepostse);
                Log.d("TAG", "ss image: "+imageFile.getName());

                // Create a request body with file and part headers
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

            }else {

                RequestBody nullBody = RequestBody.create(MediaType.parse("image/*"), "");
                imagePart = MultipartBody.Part.createFormData("image", "", nullBody);
            }

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String countryCode = convertCodeToCountryName(tm.getNetworkCountryIso());
            System.out.println("Message: " + countryCode);


        System.out.println("Message: " + imagePart);

            Call<MessageResponse> addpostcall = apiService
                    .addpost(
                            String.valueOf(authManager.getUserInfo().getId()),
                            textPost,
                            "post",
                            String.valueOf(System.currentTimeMillis()),
                            is7ark,
                            countryCode,
                            Comments_type,
                            null,
                            null,
                            null,
                            null,
                            "Unpublished",
                            imagePart);

            addpostcall.enqueue(new Callback<MessageResponse>( ) {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    LoginTojcartoon.PleaseWait.dismiss();


                    Snackbar.make(view,"تم إضافة منشورك بنجاح",4000).show();
                    currentTime = System.currentTimeMillis();
                    sharedPreferences.edit().putLong("time", currentTime).apply();
                    finish();



                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    LoginTojcartoon.PleaseWait.dismiss();

                    if (t.getMessage().equals("timeout")){
                        Toast.makeText(AddPost.this, "الإنترنت ضعيفة, جرب مرةأخرى", Toast.LENGTH_SHORT).show( );
                    }else {
                        Toast.makeText(AddPost.this, t.getMessage()+"  هناك خطأ , جرب مرةأخرى ", Toast.LENGTH_SHORT).show( );

                    }
                    System.out.println("Message: " + t.getMessage());

                }
            });




    }

    private void Wait1hourTellpost() {

        long savedTime = sharedPreferences.getLong("time", 0);
        long timeDifference = System.currentTimeMillis() - savedTime;
        // 1 hour 3600000
        if (timeDifference < 3600000 ) {

            allview.setVisibility(View.INVISIBLE);
            waithbro.setVisibility(View.VISIBLE);

        }else {
            allview.setVisibility(View.VISIBLE);
            waithbro.setVisibility(View.INVISIBLE);
            sharedPreferences.edit().clear().apply();
        }



    }

    private void GetList() {
        auto_complet.setHint(listtype[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddPost.this,
                R.layout.custom_spinner,listtype);

        auto_complet.setAdapter(adapter);
        auto_complet.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              String text = listtype[i];
                switch (text){
                    case  "منشور":
                        post_view.setVisibility(View.VISIBLE);
                        morajaa_view.setVisibility(View.GONE);
                        mowajaha_view.setVisibility(View.GONE);
                        PostType =  listtype2[0];

                        break;
                    case  "نظرية أو مراجعة":
                        post_view.setVisibility(View.GONE);
                        morajaa_view.setVisibility(View.VISIBLE);
                        mowajaha_view.setVisibility(View.GONE);
                        PostType =  listtype2[1];
                        cartoon_id_photo.setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View view) {

                              runOnUiThread(new Runnable( ) {
                                  @Override
                                  public void run() {
                                      Intent intent = new Intent(AddPost.this, SearchCartoon.class);
                                      startActivityForResult(intent, 1);
                                  }
                              });

                            }
                        });


                        break;

               /*     case  "مواجهة":
                        post_view.setVisibility(View.GONE);
                        morajaa_view.setVisibility(View.GONE);
                        mowajaha_view.setVisibility(View.VISIBLE);
                        PostType =  listtype2[2];

                        cast1.setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View view) {
                               runOnUiThread(new Runnable( ) {
                                   @Override
                                   public void run() {
                                       Intent intent = new Intent(AddPost.this, Allcharacters.class).putExtra("place","get");
                                       startActivityForResult(intent, 2);
                                   }
                               });
                            }
                        });
                        cast2.setOnClickListener(new View.OnClickListener( ) {
                            @Override
                            public void onClick(View view) {
                                runOnUiThread(new Runnable( ) {
                                    @Override
                                    public void run() {
                                       runOnUiThread(new Runnable( ) {
                                           @Override
                                           public void run() {
                                               Intent intent = new Intent(AddPost.this, Allcharacters.class).putExtra("place","get");
                                               startActivityForResult(intent, 3);
                                           }
                                       });
                                    }
                                });
                            }
                        });

                        break;*/

                }


            }
        });
        menuoftcoments.setHint(listcomments[0]);



        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(AddPost.this,
                R.layout.custom_spinner,listcomments);

        menuoftcoments.setAdapter(adapter2);
        menuoftcoments.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Comments_type = listcomments2[i];


            }
        });


          grouipradohark.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.yeshark:
                        is7ark = "1";
                        break;
                    case R.id.nohark:
                        is7ark = "0";
                        break;


                }
            }
        });

    }


    private void initialv() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        sharedPreferences = getSharedPreferences("timePrefs", Context.MODE_PRIVATE);


        auto_complet = findViewById(R.id.menuoftypes);
        cartoon_id_photo = findViewById(R.id.cartoon_id_photo);
        cartoon_title = findViewById(R.id.cartoon_title);
        backch = findViewById(R.id.backch);
        menuoftcoments = findViewById(R.id.menuoftcoments);
        post_view = findViewById(R.id.post_view);
        morajaa_view = findViewById(R.id.morajaa_view);
        mowajaha_view = findViewById(R.id.mowajaha_view);
        allview = findViewById(R.id.allview);
        waithbro = findViewById(R.id.waitbro);
        addpostbtn = findViewById(R.id.addpostbtn);
        contenttxtpost = findViewById(R.id.contenttxtpost);
        cast1 = findViewById(R.id.cast1);
        cast2 = findViewById(R.id.cast2);
        imagepost = findViewById(R.id.imagepost);
        grouipradohark = findViewById(R.id.grouipradohark);



        Wait1hourTellpost();
        backch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable( ) {
                    @Override
                    public void run() {
                        GetOut_press( );
                    }
                });
            }
        });
        registerResult();
        GetList();
        ClickOnImagePost();
    }

    private void ClickOnImagePost() {

        imagepost.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void GetOut_press() {


        final Dialog dialog = new Dialog(AddPost.this, R.style.MyAlertDialogProfile);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit_poast);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;

        LinearLayout addtofav_btn = dialog.findViewById(R.id.addtofav_btn);
        LinearLayout copylink_btn = dialog.findViewById(R.id.copylink_btn);
        addtofav_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        copylink_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
    public static void changestatucolor(Activity activity) {
        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(activity,R.color.bg4));
        }
    }
    private void openGallery() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(isIntentAvailable(i)){
            resultLauncher.launch(i);
            return;
        }

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

                    pickedImgUri = result.getData().getData();
                    imagepost.setImageURI(pickedImgUri);

                }catch (Exception e){
                    Toast.makeText(AddPost.this, "لم تحدد اي صورة", Toast.LENGTH_SHORT).show( );}
            }
        });
    }

    public static String convertCodeToCountryName(String countryCode) {
        // Make sure the provided country code is not null or empty
        if (countryCode == null || countryCode.isEmpty()) {
            return "Invalid country code";
        }

        // Convert the country code to uppercase (ISO 3166-1 alpha-2 codes are typically uppercase)
        String upperCaseCountryCode = countryCode.toUpperCase();

        // Use Locale to get the country name
        Locale locale = new Locale("", upperCaseCountryCode);

        // Get the display name of the country
        String countryName = locale.getDisplayCountry();

        return countryName;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            CartoonID = data.getStringExtra("CartoonID");
            CartoonName = data.getStringExtra("CartoonName");
            CartoonImg = data.getStringExtra("CartoonImg");

            cartoon_title.setText(CartoonName);
            Glide.with(AddPost.this).load(CartoonImg).into(cartoon_id_photo);
        }else  if (requestCode == 2 && resultCode == RESULT_OK){


            castID = data.getStringExtra("castID");
            castImg = data.getStringExtra("castImg");

            Glide.with(AddPost.this).load(castImg).into(cast1);

        }else  if (requestCode == 3 && resultCode == RESULT_OK){

            castID2 = data.getStringExtra("castID2");
            castImg2 = data.getStringExtra("castImg2");

            Glide.with(AddPost.this).load(castImg2).into(cast2);

        } else  {
            Toast.makeText(this, "المرجو الإختيار", Toast.LENGTH_SHORT).show( );

        }
    }
}