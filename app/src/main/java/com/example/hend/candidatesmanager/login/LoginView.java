package com.example.hend.candidatesmanager.login;

/**
 * Created by Hend on 4/21/2017.
 */

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void onLoginFailure(String error);
}
