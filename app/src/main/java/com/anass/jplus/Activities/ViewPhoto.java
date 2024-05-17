package com.anass.jplus.Activities;

import static android.bluetooth.BluetoothGattDescriptor.PERMISSION_WRITE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.anass.jplus.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class ViewPhoto extends AppCompatActivity {


    PhotoView photoview;
    ImageView downloadimg,backnow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_view_photo);
        checkAndRequestForPermission();

        photoview = findViewById(R.id.photoview);
        downloadimg = findViewById(R.id.downloadimg);
        backnow = findViewById(R.id.backnow);

        String photo = getIntent().getStringExtra("photo");
        Glide.with(this)
                .load(photo)
                .into(photoview);
        downloadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Toast.makeText(ViewPhoto.this, "يتم تحميل الصورة حاليا", Toast.LENGTH_LONG).show();


                DownloadManager manager = (DownloadManager) ViewPhoto.this.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(photo));

                request.allowScanningByMediaScanner();
                request.setVisibleInDownloadsUi(true)
                        .setVisibleInDownloadsUi(true)
                        .setAllowedOverMetered(true)
                        .setDestinationInExternalFilesDir(ViewPhoto.this,Environment.DIRECTORY_DOWNLOADS, photo)
                        .setNotificationVisibility(
                                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


                manager.enqueue(request);

            }
        });

        backnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public boolean checkAndRequestForPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }

}