package com.example.hend.candidatesmanager.candidatedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hend.candidatesmanager.BaseActivity;
import com.example.hend.candidatesmanager.R;
import com.example.hend.candidatesmanager.database.RealmController;
import com.example.hend.candidatesmanager.misc.Utilities;
import com.example.hend.candidatesmanager.models.Candidate;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hend on 4/22/2017.
 */

public class CandidateDetailsActivity extends BaseActivity {

    private Unbinder unbinder;
    private long candidateId;
    private Candidate candidate;

    @BindView(R.id.img_photo)
    CircleImageView mImgPhoto;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_mobile)
    TextView mTxtMobile;
    @BindView(R.id.txt_position)
    TextView mTxtPosition;
    @BindView(R.id.txt_address)
    TextView mTxtAddress;
    @BindView(R.id.txt_cv_name)
    TextView mTxtCVName;
    @BindView(R.id.txt_other_papers)
    TextView mTxtOtherPapers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_details);

        init();
    }

    private void init(){
        unbinder = ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        candidateId = getIntent().getExtras().getLong("id");
        candidate = RealmController.getInstance().getCandidate(candidateId);

        Picasso.with(this).load(Utilities.getImageContentUri(this, new File(candidate.getPhoto()))).placeholder(R.drawable.placeholder).into(mImgPhoto);

        mTxtName.setText(candidate.getName());
        mTxtPosition.setText(candidate.getPosition());
        mTxtMobile.setText(candidate.getMobile());
        mTxtAddress.setText(candidate.getAddress());
        mTxtCVName.setText(candidate.getCvName());
        mTxtOtherPapers.setText(candidate.getCvName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
