package com.anass.jplus.Config;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;

public class Utils {

    public static boolean CADN = false;

    private static LoadImageFromURL l;

    public static int et = 0;

    public static int DpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }
    public static int PxToDp(int px, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) px / density);
    }
    public static int calculateNoOfColumns(float columnWidthDp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5);
        return noOfColumns;
    }
    public static String limitString(String s, int length){
        String ss;

        if(s.length() > length){
            ss = s.substring(0, length)+"...";
        }else {
            ss = s;
        }

        return ss;
    }
    public static String SpeedCal(float speed){
        String unit;
        String sp;

        if(speed < 0){
            sp = "";
            unit = "";
        }else if(speed < 1024){
            unit = "B/s";
            sp = String.format("%.1f", speed);
        }else if(speed < 1024*1024){
            unit = "KB/s";
            sp = String.format("%.1f", speed/1024);
        }else {
            unit = "MB/s";
            sp = String.format("%.1f", speed/1024/1024);
        }

        return sp+" "+unit;
    }
    public static String FileToString(String file, Context context){
        String s = null;

        FileInputStream fis = null;
        try {
            fis = context.openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            s = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }
    public static String w = "";
    public static void WriteInFile(String file, String text, Context context){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(file, MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void LoadImage(String url, ImageView im, View progress){
        l = new LoadImageFromURL(im, progress);
        l.execute(url);
    }

    public static String shrink(String c){
        String e = "";
        if (c.contains("?utm_source=ig_web_copy_link")) {
            String p= "?utm_source=ig_web_copy_link";
            e = c.replace(p, "");
        } else if (c.contains("?utm_source=ig_web_button_share_sheet")) {
            String p = "?utm_source=ig_web_button_share_sheet";
            e = c.replace(p, "");
        } else if (c.contains("?utm_medium=share_sheet")) {
            String p = "?utm_medium=share_sheet";
            e = c.replace(p, "");
        } else if (c.contains("?utm_medium=copy_link")) {
            String p = "?utm_medium=copy_link";
            e = c.replace(p, "");
        } else {
            e = c;
        }
        return e;
    }
    public static String simplify(String c){
        String e = "";
        if (c.contains("?utm_source=ig_web_copy_link")) {
            String p= "?utm_source=ig_web_copy_link";
            e = c.replace(p, "");
        } else if (c.contains("?utm_source=ig_web_button_share_sheet")) {
            String p = "?utm_source=ig_web_button_share_sheet";
            e = c.replace(p, "");
        } else if (c.contains("?utm_medium=share_sheet")) {
            String p = "?utm_medium=share_sheet";
            e = c.replace(p, "");
        } else if (c.contains("?utm_medium=copy_link")) {
            String p = "?utm_medium=copy_link";
            e = c.replace(p, "");
        } else {
            e = c;
        }
        return e.replaceAll("/", "").replaceAll("https:www.instagram.comtv", "");
    }

    private static class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
        private ImageView im;
        private View progress;

        public LoadImageFromURL(ImageView im, View progress){
            this.im = im;
            this.progress = progress;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;

            try {
                if(isCancelled()){
                    return null;
                }
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                im.setImageBitmap(bitmap);
                progress.setVisibility(View.GONE);
            }
        }
    }

    public static void Cancel(){
        if(l != null){
            l.cancel(true);
        }
    }

    public static void FixColumnsWidth(GridView gridView, int width, Context context){
        int c = calculateNoOfColumns(width, context);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        float columnsWidth = screenWidthDp/c;

        gridView.setColumnWidth((int) columnsWidth);
        gridView.setNumColumns(c);
    }
    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public static double getAvg(float story,float caracter, float mottionsdes,float musicSeek,boolean isMovie){

        if (isMovie){


            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            return Double.parseDouble(decimalFormat.format(((story + caracter + musicSeek) / 3)));

        }else {


            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            return Double.parseDouble(decimalFormat.format(((story + caracter +mottionsdes+ musicSeek) / 4)));
        }

    }

}
