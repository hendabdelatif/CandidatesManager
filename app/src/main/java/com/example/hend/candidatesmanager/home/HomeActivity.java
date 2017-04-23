package com.example.hend.candidatesmanager.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hend.candidatesmanager.BaseActivity;
import com.example.hend.candidatesmanager.R;
import com.example.hend.candidatesmanager.login.LoginActivity;
import com.example.hend.candidatesmanager.misc.DialogClickListener;
import com.example.hend.candidatesmanager.misc.SharedPreferencesManager;
import com.example.hend.candidatesmanager.newcandidate.NewCandidateActivity;
import com.example.hend.candidatesmanager.permissions.PermissionAskListener;
import com.example.hend.candidatesmanager.permissions.PermissionUtil;
import com.example.hend.candidatesmanager.permissions.PermissionsConstants;
import com.example.hend.candidatesmanager.searchcandidate.SearchCandidateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Hend on 4/21/2017.
 */

public class HomeActivity extends BaseActivity implements PermissionAskListener{

    private Unbinder unbinder;

    @BindView(R.id.btn_new_candidate)
    AppCompatButton mBtnNewCandidate;
    @BindView(R.id.btn_search_candidate)
    AppCompatButton mBtnSearchCandidate;

    @OnClick(R.id.btn_new_candidate)
    public void onNewCandidateClick() {
        startActivity(new Intent(this, NewCandidateActivity.class));
    }

    @OnClick(R.id.btn_search_candidate)
    public void onSearchCandidateClick() {
        PermissionUtil.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        unbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        generateChoiceMessageAlert(this, getString(R.string.logout_confrim), new DialogClickListener() {
            @Override
            public void onDialogButtonClick() {
                performLogout();
            }
        }, null);
    }


    private void performLogout() {
        SharedPreferencesManager.getInstance(this).clear();
        Intent mIntent = new Intent(this, LoginActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onPermissionAsk() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PermissionsConstants.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionGranted() {
        startActivity(new Intent(this, SearchCandidateActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, SearchCandidateActivity.class));
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
