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
import com.iptv.signin.utils.SpUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.iptv.signin.SignInApplication.mContext;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.user_mobile)
    TextInputEditText mMobile;
    @BindView(R.id.user_password)
    TextInputEditText mPassword;
    @BindView(R.id.check_visible)
    CheckBox mCheckBox;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.register_btn)
    Button mThirdLoginBtn;

    private Tencent mTencent;
    private BaseUiListener baseUiListener = new BaseUiListener();
    private Unbinder mBind;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBind = ButterKnife.bind(this);
        initUI();
    }


    /**
     * 初始化界面
     */
    private void initUI() {
        if (SpUtil.getLoginData() != null) {
            mMobile.setText(SpUtil.getLoginData().getUserId());
        }
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
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + mMobile.getText() + "---" + mPassword.getText());
                if (CommonData.mUserId.equals(mMobile.getText().toString().trim()) && CommonData.mUserPassword.equals(mPassword.getText().toString().trim())) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginData loginData = new LoginData();
                    loginData.setUserId(mMobile.getText().toString().trim());
                    loginData.setUserPassword(mPassword.getText().toString().trim());
                    loginData.setUserHeadImage(CommonData.mUserHeadImage);
                    loginData.setUserName(CommonData.mUserName);
                    loginData.setUserDesc(CommonData.mUserDesc);
                    SpUtil.saveLoginData(loginData);
                    finish();
                } else {
                    Toast.makeText(mContext, "密码/账号：空/不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mThirdLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /**
     * 腾讯第三方登录
     */
    public void login() {
        mTencent = Tencent.createInstance(CommonData.tencentAppID, this.getApplicationContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", baseUiListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
    }

    /**
     * 腾讯登陆状态接口监听
     */
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
