package com.example.hend.candidatesmanager.login;

/**
 * Created by Hend on 4/21/2017.
 */

public interface LoginPresenter {

    void validateCredentials(String username, String password);

    void checkRememberMe(String username, String password);

    void onDestroy();
}
