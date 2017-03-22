package com.iptv.signin.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * Created by ZhangQian on 2017/3/11 0011.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showDiaLog(String title, String messgage, String leftBtn, String rightBtn, final BaseDiaLogListener baseDiaLogListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(messgage)) {
            builder.setMessage(messgage);
        }
        if (!TextUtils.isEmpty(leftBtn)) {
            builder.setPositiveButton(leftBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    baseDiaLogListener.setLeftBtn();
                }
            });
        }
        if (!TextUtils.isEmpty(rightBtn)) {
            builder.setNegativeButton(rightBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    baseDiaLogListener.setRightBtn();
                }
            });
        }
        builder.show();
    }

    public interface BaseDiaLogListener {
        void setLeftBtn();

        void setRightBtn();
    }
}
