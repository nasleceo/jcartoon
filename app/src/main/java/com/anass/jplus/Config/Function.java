package com.anass.jplus.Config;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Function {

    public static final String SDPATH = Environment.getExternalStorageDirectory().getPath() + "/j+/";// 获取文件夹

    public static void ShowIfThersIsSDCARD(Context context){
        String sdCardPath = null;



        // Get the list of external storage directories
        File[] externalDirs = context.getExternalFilesDirs(null);

        // Check if any of the external storage directories are SD cards
        for (File dir : externalDirs) {
            // Check if this is an SD card
            if (dir.getAbsolutePath().contains("sdcard")) {
                sdCardPath = dir.getAbsolutePath();
                break;
            }
        }

        // If an SD card was found, display its path
        if (sdCardPath != null) {
            Toast.makeText(context, "SD card found: " + sdCardPath, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "No SD card found", Toast.LENGTH_SHORT).show();
        }

    }

    public static int ToInterger(String value) {
        if (value != null && !value.equals("")) {
            return Integer.parseInt(value);
        } else
            return 0;
    }
    public static int ToFloat(String value) {
        if (value != null && !value.equals("")) {
            return Math.round(Float.parseFloat(value));
        } else
            return 0;
    }
    public static boolean isOreoOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isROrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static boolean isArchive(String filename) {
        return isZip(filename) || isRar(filename) || isTarball(filename) || isSevenZ(filename);
    }


    public static File[] listExternalStorageDirs(Context context) {
        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(context, null);
        // strip null entries (at least API16)
        ArrayList<File> entries = new ArrayList();
        for (int i = 0; externalFilesDirs != null && i < externalFilesDirs.length; i++) {
            File entry = externalFilesDirs[i];
            if (entry!=null)
                entries.add(entry);
        }
        return entries.toArray(new File[0]);
    }

    public static String showUsername(String username) {
        if (username != null && username.contains("@"))
            return username;
        else
            return "@" + username;
    }

    public static String bitmapToBase64(Bitmap imagebitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagebitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] byteArray = baos.toByteArray();
        String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return base64;
    }

    public static Bitmap base64ToBitmap(String base_64) {
        Bitmap decodedByte = null;
        try {

            byte[] decodedString = Base64.decode(base_64, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {

        }
        return decodedByte;
    }
    public static long getFileDuration(Context context, Uri uri) {
        try {

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(context, uri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            final int file_duration = Function.ToInterger(durationStr);

            return file_duration;
        } catch (Exception e) {

        }
        return 0;
    }


    public static void deleteCache(Context context) {


        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public static boolean appInstalledOrNot(Context context,String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private static boolean fileExists(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }
    private static String getPathFromExtSD(String[] pathData) {
        final String type = pathData[0];
        final String relativePath = "/" + pathData[1];
        String fullPath = "";

        if ("primary".equalsIgnoreCase(type)) {
            fullPath = Environment.getExternalStorageDirectory() + relativePath;
            if (fileExists(fullPath)) {
                return fullPath;
            }
        }
        // Environment.isExternalStorageRemovable() is `true` for external and internal storage
        // so we cannot relay on it.
        //
        // instead, for each possible path, check if file exists
        // we'll start with secondary storage as this could be our (physically) removable sd card
        fullPath = System.getenv("SECONDARY_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        fullPath = System.getenv("EXTERNAL_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        return fullPath;
    }


    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }


    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }


    public static boolean haveSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    public static int getDeviceHeight(Activity activity) {
        int px = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            px = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return px;
    }

    public static int getDeviceWidth(Activity activity) {
        int px = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            px = dm.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return px;
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //px-sp转换
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }




    public static File createSDDir(String dirName,Context context) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "createSDDir:" + dir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "createSDDir:" + dir.mkdir(), Toast.LENGTH_SHORT).show();
        }
        return dir;
    }

    public static String getDateTimeFromMillisecond(Long millisecond){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
    public static int getSystemScreenBrightness(Context context) {
        int values = 0;
        try {
            values = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * 设置系统亮度
     *
     * @param brightness 返回的亮度值是处于0-255之间的整型数值
     */
    public static void setScreenBrightness(Activity activity, int brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    /**
     * 系统是否自动调节亮度
     * return true 是自动调节亮度   return false 不是自动调节亮度
     */
    public static boolean isAutoBrightness(Activity activity) {
        int autoBrightness = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        try {
            autoBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return autoBrightness == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }

    /**
     * 关闭系统自动调节亮度
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 打开系统自动调节亮度
     */
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 请求屏幕常亮
     *
     * @param activity
     */
    public static void requireScreenOn(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 取消屏幕常亮
     *
     * @param activity
     */
    public static void releaseScreenOn(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static boolean isImage(String filename) {
        return filename.matches("(?i).*\\.(jpg|jpeg|bmp|gif|png|webp|jp2|j2k)$");
    }

    public static int getScreenDpWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(displayMetrics.widthPixels / displayMetrics.density);
    }

    public static boolean isIceCreamSandwitchOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean isHoneycombOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isHoneycombMR1orLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean isJellyBeanMR1orLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isKitKatOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isLollipopOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    // FileChannel
    public static boolean isNougatOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    // java.nio.path

    public static int getHeapSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        boolean isLargeHeap = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (isLargeHeap && isHoneycombOrLater()) {
            memoryClass = am.getLargeMemoryClass();
        }
        return 1024 * memoryClass;
    }

    public static int calculateBitmapSize(Bitmap bitmap) {
        int sizeInBytes;
        if (isHoneycombMR1orLater()) {
            sizeInBytes = bitmap.getByteCount();
        } else {
            sizeInBytes = bitmap.getRowBytes() * bitmap.getHeight();
        }
        return sizeInBytes / 1024;
    }





    public static boolean isZip(String filename) {
        return filename.toLowerCase().matches(".*\\.(zip|cbz)$");
    }

    public static boolean isRar(String filename) {
        return filename.toLowerCase().matches(".*\\.(rar|cbr)$");
    }

    public static boolean isTarball(String filename) {
        return filename.matches("(?i).*\\.(cbt|tar)$") || isCompressedTarball(filename);
    }

    public static boolean isCompressedTarball(String filename) {
        return isTGZ(filename) || isTBZ(filename) || isTXZ(filename) || isTZST(filename) || isTBR(filename);
    }

    public static boolean isTGZ(String filename) {
        return filename.toLowerCase().matches("(?i).*\\.(tar\\.gz|tgz)$");
    }

    public static boolean isTBZ(String filename) {
        return filename.toLowerCase().matches("(?i).*\\.(tar\\.bz2?|tbz2?)$");
    }

    public static boolean isTXZ(String filename) {
        return filename.toLowerCase().matches("(?i).*\\.(tar\\.xz|txz)$");
    }

    public static boolean isTZST(String filename) {
        return filename.toLowerCase().matches("(?i).*\\.(tar\\.zstd?|tzs(|t|td))$");
    }

    public static boolean isTBR(String filename) {
        return filename.toLowerCase().matches("(?i).*\\.(tar\\.br|tbr)$");
    }

    public static boolean isSevenZ(String filename) {
        return filename.toLowerCase().matches(".*\\.(cb7|7z)$");
    }


    public static int getDeviceWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int value = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        return value;
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int value = Math.round(displayMetrics.heightPixels / displayMetrics.density);
        return value;
    }

    public static String MD5(String string) {
        try {
            byte[] strBytes = string.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(strBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; ++i) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            return string.replace("/", ".");
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int calculateMemorySize(Context context, int percentage) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        int memoryClass = activityManager.getLargeMemoryClass();
        return 1024 * 1024 * memoryClass / percentage;
    }

    public static File getCacheFile(Context context, String identifier) {
        File dir = context.getExternalCacheDir();
        if (dir == null)
            dir = context.getCacheDir();
        return new File(dir, MD5(identifier));
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } catch (Exception e) {
            Log.e("Utils.toByteArray", e.getMessage(), e);
            throw new IOException(e);
        } finally {
            output.close();
        }
    }



    public static void rmDir(File dir) {
        rmDir(dir, true);
    }

    public static void rmDir(File dir, boolean recursive) {
        if (dir == null || !dir.isDirectory())
            return;

        for (File f : dir.listFiles()) {
            if (recursive && f.isDirectory())
                rmDir(f, true);
            else if (f.isFile())
                f.delete();
        }
        dir.delete();
    }






}
