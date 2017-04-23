package com.example.hend.candidatesmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hend.candidatesmanager.misc.DialogClickListener;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

/**
 * Created by Hend on 4/21/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected SimpleArcDialog mArchDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int[] colors = {Color.parseColor("#3fb9ee")};
        ArcConfiguration mConfig = new ArcConfiguration(this);
        mConfig.setColors(colors);
        mConfig.setText(getString(R.string.loading));
        mArchDialog = new SimpleArcDialog(this, false, null, mConfig);
    }

    public static void generateChoiceMessageAlert(Context context, String msg, final DialogClickListener onOkClicked, final DialogClickListener onCancelClicked) {
        if (context == null || msg == null || msg.isEmpty())
            return;

        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.setTitle(context.getString(R.string.app_name));
        mDialogBuilder.setMessage(msg);
        mDialogBuilder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onOkClicked != null) {
                    onOkClicked.onDialogButtonClick();
                }
            }
        });
        mDialogBuilder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onCancelClicked != null) {
                    onCancelClicked.onDialogButtonClick();
                }
                dialog.dismiss();
            }
        });
        try {
            Dialog mDailog = mDialogBuilder.create();
            mDailog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
