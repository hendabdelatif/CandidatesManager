package com.example.hend.candidatesmanager.newcandidate;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Hend on 4/21/2017.
 */

public interface NewCandidatePresenter {

    String getFileNameByUri(Context context, Uri uri);


}
