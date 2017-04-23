package com.example.hend.candidatesmanager.newcandidate;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Hend on 4/21/2017.
 */

public class NewCandidatePresenterImp implements NewCandidatePresenter {

    private NewCandidateView mNewCandidateView;
    private Context mContext;

    public NewCandidatePresenterImp(NewCandidateView newCandidateView, Context context) {
        this.mNewCandidateView = newCandidateView;
        this.mContext = context;
    }

    @Override
    public String getFileNameByUri(Context context, Uri uri) {

        String result;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(columnIndex);
            cursor.close();
        }
        return result;
    }

}
