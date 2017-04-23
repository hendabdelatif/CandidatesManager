package com.example.hend.candidatesmanager.misc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.hend.candidatesmanager.BaseActivity;
import com.example.hend.candidatesmanager.R;

import java.io.File;

/**
 * Created by Hend on 4/21/2017.
 */

public class Utilities {

    public static boolean isNetworkAvailable(Context context) {
        if (context == null)
            return false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected())
                return true;
        }

        return false;
    }

    public static void showToast(Context mContext, String message, boolean isLong) {
        Toast.makeText(mContext, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static void hidKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        } catch (Exception e) {

        }
    }

    public static void setToolbar(BaseActivity context, String title) {
        Toolbar toolBar = (Toolbar) context.findViewById(R.id.toolbar);
        context.setSupportActionBar(toolBar);
        context.getSupportActionBar().setTitle(title);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
