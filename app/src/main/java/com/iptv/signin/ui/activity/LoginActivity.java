package com.iptv.signin.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.iptv.signin.R;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.persenter.LoginPersenter;
import com.iptv.signin.utils.LogUtil;
import com.iptv.signin.utils.SpUtil;
import com.iptv.signin.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private Unbinder mBind;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBind = ButterKnife.bind(this);
        initUI();
        autoLogin();
    }

    /**
     * 初始化界面
     */
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
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("onClick: " + mMobile.getText() + "---" + mPassword.getText());
                String mobile = mMobile.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if ((!TextUtils.isEmpty(mobile)) & (!TextUtils.isEmpty(password))) {
                    postLoginData(mobile, password);
                } else {
                    showShort("密码/账号：空");
                }
            }
        });
    }

    /**
     * 登录账号
     *
     * @param mobile
     * @param password
     */
    private void postLoginData(String mobile, String password) {
        LoginPersenter loginPersenter = new LoginPersenter(new LoginView() {
            @Override
            public void loginSuccess(boolean isSuccess, BaseResult<LoginData> mResult) {
                SpUtil.setLoginData(mResult.getResult());
                LogUtil.e(SpUtil.getLoginData().toString());
                openActivityAndCloseThis(MainActivity.class);
            }

            @Override
            public void loginError(String error) {
                showShort("密码/账号：不正确" + error);
            }
        });
        loginPersenter.getLoginData(mobile, password);
    }

    /**
     * 自动登录腾讯
     */
    private void autoLogin() {
        LoginData loginData = SpUtil.getLoginData();
        if (!TextUtils.isEmpty(loginData.getUserId())) {
            openActivityAndCloseThis(MainActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

}
