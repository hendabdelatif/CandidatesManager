package com.example.hend.candidatesmanager.login;

import android.app.Activity;
import android.content.Context;

import com.example.hend.candidatesmanager.R;
import com.example.hend.candidatesmanager.misc.SharedPreferencesManager;
import com.example.hend.candidatesmanager.misc.Utilities;

import static com.example.hend.candidatesmanager.login.LoginActivity.PREF_PASSWORD;
import static com.example.hend.candidatesmanager.login.LoginActivity.PREF_USERNAME;

/**
 * Created by Hend on 4/21/2017.
 */

public class LoginPresenterImp implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView mLoginView;
    private LoginInteractor mLoginIntercator;
    private Context mContext;

    public LoginPresenterImp(LoginView loginView, Context context) {
        this.mLoginView = loginView;
        this.mContext = context;
        this.mLoginIntercator = new LoginInteractorImp();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (mLoginView != null)
            mLoginIntercator.login(username, password, this);
    }

    @Override
    public void checkRememberMe(String username, String password) {
        SharedPreferencesManager.getInstance(mContext).addString(PREF_USERNAME, username);
        SharedPreferencesManager.getInstance(mContext).addString(PREF_PASSWORD, password);

    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }

    @Override
    public void onUsernameError() {
        if (mLoginView != null)
            mLoginView.setUsernameError();
    }

    @Override
    public void onPasswordError() {
        if (mLoginView != null)
            mLoginView.setPasswordError();
    }

    @Override
    public void onValidInfo(String username, String password) {
        if (!Utilities.isNetworkAvailable(mContext)) {
            Utilities.showToast(mContext, mContext.getString(R.string.connection_error), true);
            return;
        }
        if (mLoginView != null)
            mLoginView.showProgress();
        sendLoginDetails(username, password);
    }

    private void sendLoginDetails(String username, String password) {

        Utilities.hidKeyboard((Activity) mContext);

        // TODO: Mock that you hit the server
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {


        if (mLoginView != null) {
            mLoginView.hideProgress();
            mLoginView.navigateToHome();
        }
    }

    public void onLoginFailed() {
        if (mLoginView != null)
            mLoginView.onLoginFailure(mContext.getString(R.string.login_credentials_error));
    }
}
