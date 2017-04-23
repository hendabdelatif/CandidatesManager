package com.example.hend.candidatesmanager.permissions;

/**
 * Created by Hend on 12/22/2016.
 */

public interface PermissionAskListener {

    /**
     * Callback to ask permission if it is being asked first time
     */
    void onPermissionAsk();

    /**
     * Callback on permission granted
     */
    void onPermissionGranted();
}
