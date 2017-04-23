package com.example.hend.candidatesmanager.login;

/**
 * Created by Hend on 4/21/2017.
 */

public class LoginInteractorImp implements LoginInteractor {

    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {

        if (username.trim().isEmpty())
            listener.onUsernameError();
        else if (password.trim().isEmpty())
            listener.onPasswordError();
        else
            listener.onValidInfo(username,password);

    }
}
