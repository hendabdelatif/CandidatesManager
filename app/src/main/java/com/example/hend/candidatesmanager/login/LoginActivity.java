package com.example.hend.candidatesmanager.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hend.candidatesmanager.BaseActivity;
import com.example.hend.candidatesmanager.R;
import com.example.hend.candidatesmanager.home.HomeActivity;
import com.example.hend.candidatesmanager.misc.SharedPreferencesManager;
import com.example.hend.candidatesmanager.misc.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity implements LoginView{

    private Unbinder unbinder;
    private LoginPresenter mLoginPresenter;

    public static String PREF_USERNAME = "username";
    public static String PREF_PASSWORD = "password";

    @BindView(R.id.input_username)
    EditText mTxtUsername;
    @BindView(R.id.input_password)
    EditText mTxtPassword;
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindView(R.id.ch_rememberme)
    CheckBox mCheckBoxRememberMe;

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        mLoginPresenter.validateCredentials(mTxtUsername.getText().toString(), mTxtPassword.getText().toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserCredentials();
    }

    private void init(){
        unbinder = ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenterImp(this, this);

        mTxtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mLoginPresenter.validateCredentials(mTxtUsername.getText().toString(), mTxtPassword.getText().toString());
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onDestroy();
        unbinder.unbind();
        super.onDestroy();

    }

    @Override
    public void showProgress() {
        mArchDialog.show();
    }

    @Override
    public void hideProgress() {
        mArchDialog.dismiss();
    }

    @Override
    public void setUsernameError() {
        mTxtUsername.setError(getString(R.string.login_username_error));
        mTxtUsername.requestFocus();
    }

    @Override
    public void setPasswordError() {
        mTxtPassword.setError(getString(R.string.login_password_error));
        mTxtPassword.requestFocus();
    }

    @Override
    public void navigateToHome() {
        if (mCheckBoxRememberMe.isChecked()) {
            mLoginPresenter.checkRememberMe(mTxtUsername.getText().toString(), mTxtPassword.getText().toString());
        }else{
            SharedPreferencesManager.getInstance(this).removeValue(LoginActivity.PREF_USERNAME);
            SharedPreferencesManager.getInstance(this).removeValue(LoginActivity.PREF_PASSWORD);
        }

        startActivity(new Intent(this, HomeActivity.class));
        finish();

    }

    @Override
    public void onLoginFailure(String error) {
        Utilities.showToast(this, error, true);
    }

    public void getUserCredentials() {

        String username = SharedPreferencesManager.getInstance(this).getStringValue(PREF_USERNAME, null);
        String password = SharedPreferencesManager.getInstance(this).getStringValue(PREF_PASSWORD, null);

        if (username != null && password != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
}
