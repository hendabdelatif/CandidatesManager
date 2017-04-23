package com.example.hend.candidatesmanager.newcandidate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hend.candidatesmanager.BaseActivity;
import com.example.hend.candidatesmanager.R;
import com.example.hend.candidatesmanager.database.RealmController;
import com.example.hend.candidatesmanager.home.HomeActivity;
import com.example.hend.candidatesmanager.misc.DialogClickListener;
import com.example.hend.candidatesmanager.misc.Utilities;
import com.example.hend.candidatesmanager.models.Candidate;
import com.example.hend.candidatesmanager.permissions.PermissionAskListener;
import com.example.hend.candidatesmanager.permissions.PermissionUtil;
import com.example.hend.candidatesmanager.permissions.PermissionsConstants;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * Created by Hend on 4/21/2017.
 */

public class NewCandidateActivity extends BaseActivity implements NewCandidateView, PermissionAskListener {

    private Realm realm;
    private NewCandidatePresenter mNewCandidatePresenter;
    private Unbinder unbinder;
    private String mAddress;
    private String picturePath, cvFilePath, otherFilesPath;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_CV = 2;
    private static int RESULT_LOAD_Other = 3;

    @BindView(R.id.img_photo)
    CircleImageView mImgPhoto;
    @BindView(R.id.input_name)
    EditText mEtName;
    @BindView(R.id.input_mobile)
    EditText mEtMobile;
    @BindView(R.id.input_position)
    EditText mEtPosition;
    @BindView(R.id.input_location)
    EditText mEtLocation;
    @BindView(R.id.btn_upload_cv)
    AppCompatButton mBtnUploadCV;
    @BindView(R.id.btn_upload_other)
    AppCompatButton mBtnUploadOtherPapers;
    @BindView(R.id.txt_cv_name)
    TextView mTxtCVName;
    @BindView(R.id.txt_other_paper_name)
    TextView mTxtOtherPapersName;


    @OnClick(R.id.input_location)
    public void onLocationClick() {
        startActivity(new Intent(this, MapsActivity.class));
    }

    @OnClick(R.id.btn_upload_cv)
    public void onUploadCVClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/msword , application/pdf");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/msword", "application/pdf"});
        startActivityForResult(intent, RESULT_LOAD_CV);

    }

    @OnClick(R.id.btn_upload_other)
    public void onUploadOtherPapers() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*, application/pdf");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "application/pdf"});
        startActivityForResult(intent, RESULT_LOAD_Other);

    }

    @OnClick(R.id.img_photo)
    public void onPhotoClick() {
        PermissionUtil.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_candidate);
        init();
    }

    private void init() {
        unbinder = ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNewCandidatePresenter = new NewCandidatePresenterImp(this, this);
        realm = Realm.getDefaultInstance();

        mEtLocation.setKeyListener(null);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.hasExtra("location")) {
            mAddress = getIntent().getStringExtra("location");
            mEtLocation.setText(mAddress);
        } else {
            mEtLocation.setText("");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_candidate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                break;
            case R.id.menu_save:
                saveData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        generateChoiceMessageAlert(this, getString(R.string.discard_changes_confrim), new DialogClickListener() {
            @Override
            public void onDialogButtonClick() {
                Utilities.hidKeyboard(NewCandidateActivity.this);
                finish();
            }
        }, null);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                Uri selectedImage = data.getData();
                picturePath = mNewCandidatePresenter.getFileNameByUri(this, selectedImage);

                Picasso.with(this).load(selectedImage).placeholder(R.drawable.placeholder).into(mImgPhoto);

            } else if (requestCode == RESULT_LOAD_CV) {
                Uri fileUri = data.getData();
                cvFilePath = mNewCandidatePresenter.getFileNameByUri(this, fileUri);
                if (cvFilePath != null) {
                    File file = new File(cvFilePath);
                    mTxtCVName.setText(file.getName());
                }

            } else if (requestCode == RESULT_LOAD_Other) {
                Uri fileUri = data.getData();
                otherFilesPath = mNewCandidatePresenter.getFileNameByUri(this, fileUri);
                if (otherFilesPath != null) {
                    File file = new File(otherFilesPath);
                    mTxtOtherPapersName.setText(file.getName());
                }
            }
        }
    }

    /**
     * Save candidate data to realm database
     */
    private void saveData() {
        Candidate candidate = new Candidate();
        candidate.setId(RealmController.getInstance().getCandidates().size() + System.currentTimeMillis());
        candidate.setName(mEtName.getText().toString());
        candidate.setMobile(mEtMobile.getText().toString());
        candidate.setPosition(mEtPosition.getText().toString());
        candidate.setAddress(mEtLocation.getText().toString());
        candidate.setPhoto(picturePath);
        candidate.setCvName(mTxtCVName.getText().toString());
        candidate.setOtherPaper(mTxtOtherPapersName.getText().toString());

        if (mEtName.getText() == null || mEtName.getText().toString().equals("") || mEtName.getText().toString().equals(" ")) {
            Toast.makeText(this, "Name is missing!", Toast.LENGTH_SHORT).show();
        } else if (mEtMobile.getText() == null || mEtMobile.getText().toString().equals("") || mEtMobile.getText().toString().equals(" ")) {
            Toast.makeText(this, "Mobile is missing!", Toast.LENGTH_SHORT).show();
        } else if (mEtPosition.getText() == null || mEtPosition.getText().toString().equals("") || mEtPosition.getText().toString().equals(" ")) {
            Toast.makeText(this, "Position is missing!", Toast.LENGTH_SHORT).show();
        } else if (picturePath == null) {
            Toast.makeText(this, "Photo is missing!", Toast.LENGTH_SHORT).show();
        } else {

            realm.beginTransaction();
            realm.copyToRealm(candidate);
            realm.commitTransaction();
            realm.close();


            Toast.makeText(this, "Candidate data saved successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onPermissionAsk() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PermissionsConstants.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionGranted() {
        navigateToStorage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navigateToStorage();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void navigateToStorage() {
        startActivityForResult(new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
    }
}
