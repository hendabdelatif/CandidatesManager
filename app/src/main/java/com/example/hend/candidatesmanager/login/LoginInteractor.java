package com.example.hend.candidatesmanager.login;

/**
 * Created by Hend on 4/21/2017.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onValidInfo(String username,String password);
    }

    void login(String username, String password, OnLoginFinishedListener listener);
}
