package com.example.hend.candidatesmanager.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by Hend on 12/22/2016.
 */

public class PermissionUtil {

    /*
    * Check if version is marshmallow and above.
    * Used in deciding to ask runtime permission
    * */
    public static boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }


    /**
     * Check if  permission is granted
     *
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        if (shouldAskPermission()) {
            int permissionResult = ContextCompat.checkSelfPermission(context, permission);
            if (permissionResult == PackageManager.PERMISSION_GRANTED) {
                return true;
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * Check if permission is not granted
     *
     * @param context
     * @param permission
     * @param listener
     */
    public static void checkPermission(Context context, String permission, PermissionAskListener listener) {
        if (checkPermission(context, permission)) {
            listener.onPermissionGranted();
        } else {
            listener.onPermissionAsk();
        }
    }


}
