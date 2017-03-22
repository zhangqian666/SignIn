package com.iptv.signin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.iptv.signin.R;
import com.iptv.signin.bean.CommonData;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.utils.LitePalUtil;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import static com.iptv.signin.others.SignInApplication.mContext;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private TextInputEditText mMobile;
    private TextInputEditText mPassword;
    private CheckBox mCheckBox;
    private Tencent mTencent;
    private BaseUiListener baseUiListener = new BaseUiListener();
    private Button mThirdLoginBtn;
    private Button mLoginBtn;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mMobile = ((TextInputEditText) findViewById(R.id.user_mobile));
        mPassword = (TextInputEditText) findViewById(R.id.user_password);
        mCheckBox = (CheckBox) findViewById(R.id.check_visible);
        mThirdLoginBtn = (Button) findViewById(R.id.register_btn);
        mThirdLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + mMobile.getText() + "---" + mPassword.getText());
                if (CommonData.userName.equals(mMobile.getText().toString().trim()) && CommonData.userPassword.equals(mPassword.getText().toString().trim())) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginData loginData = new LoginData();
                    loginData.setUserName(mMobile.getText().toString().trim());
                    loginData.setUserPassword(mPassword.getText().toString().trim());
                    LitePalUtil.saveLoginData(loginData);
                    finish();
                } else {
                    Toast.makeText(mContext, "密码/账号：空/不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initUI();
        initContent();
    }

    private void initContent() {
        if (LitePalUtil.getLoginData() != null) {
            mMobile.setText(LitePalUtil.getLoginData().getUserName());
        }
    }


    private void initUI() {
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mPassword.postInvalidate();
                CharSequence text = mPassword.getText();
                //将光标移动到密码的最后处
                if (!TextUtils.isEmpty(text)) {
                    Selection.setSelection((Spannable) text, text.length());
                }
            }
        });
    }

    public void login() {
        mTencent = Tencent.createInstance("tencent1106007238", this.getApplicationContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", baseUiListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            Log.e(TAG, "onComplete: " + o.toString());
        }

        @Override
        public void onError(UiError e) {
            Log.e(TAG, "onError: " + e.errorMessage);
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "onCancel: ");
        }
    }

    private class BaseApiListener implements IRequestListener {
        @Override
        public void onComplete(JSONObject jsonObject) {

        }

        @Override
        public void onIOException(IOException e) {

        }

        @Override
        public void onMalformedURLException(MalformedURLException e) {

        }

        @Override
        public void onJSONException(JSONException e) {

        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException e) {

        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException e) {

        }

        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {

        }

        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e) {

        }

        @Override
        public void onUnknowException(Exception e) {

        }
    }

}
