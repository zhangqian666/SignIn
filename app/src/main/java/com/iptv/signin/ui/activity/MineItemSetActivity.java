package com.iptv.signin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iptv.signin.R;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.CommonData;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.bean.TencentLoginResult;
import com.iptv.signin.bean.TencentUserInfo;
import com.iptv.signin.persenter.UpdateQQPersenter;
import com.iptv.signin.service.SignInService;
import com.iptv.signin.utils.LogUtil;
import com.iptv.signin.utils.SpUtil;
import com.iptv.signin.view.UpdateQQView;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.iptv.signin.SignInApplication.mContext;


public class MineItemSetActivity extends BaseActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.three_btn)
    Button threeBtn;
    @BindView(R.id.loginout_btn)
    Button loginoutBtn;
    private Unbinder mBind;
    private BaseUiListener baseUiListener = new BaseUiListener();
    private Tencent mTencent;
    private LoginData commonLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_item_set);
        mBind = ButterKnife.bind(this);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent signInServerIntent = new Intent(MineItemSetActivity.this, SignInService.class);
                if (isChecked) {
                    startService(signInServerIntent);
                } else {
                    stopService(signInServerIntent);
                }
            }
        });
        if (!TextUtils.isEmpty(SpUtil.getLoginData().getUserQQopenId())) {
            threeBtn.setText("已经绑定");
            threeBtn.setEnabled(false);
            threeBtn.setBackgroundResource(R.color.text_grey_color);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    @OnClick({R.id.three_btn, R.id.loginout_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.three_btn:
                login();
                break;
            case R.id.loginout_btn:
                finishAll();
                break;
        }
    }

    /**
     * 腾讯第三方登录
     */
    public void login() {
        mTencent = Tencent.createInstance(CommonData.tencentAppID, mContext);
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", baseUiListener);
        } else {
            mTencent.logout(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
    }

    private String openId;
    private String accessToken;
    private String expires;

    /**
     * 腾讯登陆状态接口监听
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            if (o == null) return;
            LogUtil.e(o.toString());
            TencentLoginResult tencentLoginResult = null;
            try {
                Gson gson = new Gson();
                tencentLoginResult = gson.fromJson(o.toString(), new TypeToken<TencentLoginResult>() {
                }.getType());
                commonLoginData = new LoginData();
                commonLoginData.setUserQQopenId(tencentLoginResult.getOpenid());
                commonLoginData.setUserQQAccessToken(tencentLoginResult.getAccess_token());
                commonLoginData.setUserQQExpires(tencentLoginResult.getExpires_in());
                mTencent.setOpenId(tencentLoginResult.getOpenid());
                mTencent.setAccessToken(tencentLoginResult.getAccess_token(), tencentLoginResult.getExpires_in());
                getUserInfo();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError e) {
            LogUtil.e(e.errorMessage);
            showShort("登录失败");
        }

        @Override
        public void onCancel() {
            LogUtil.e("onCancel");
            showShort("取消登录");
        }
    }

    /**
     * 更新qq信息
     */
    private void updateQQDate() {
        LoginData loginData = SpUtil.getLoginData();
        UpdateQQPersenter updateQQPersenter = new UpdateQQPersenter(new UpdateQQView() {
            @Override
            public void updateSuccess(BaseResult<String> result) {
                if (result.getCode() == 200) {
                    LogUtil.e(result.getMsg());
                    threeBtn.setText("已经绑定");
                    threeBtn.setEnabled(false);
                    threeBtn.setBackgroundResource(R.color.text_grey_color);
                } else {
                    showShort("更新qq失败");
                }
            }

            @Override
            public void updateError(String ex) {
                showShort("更新qq失败 ： " + ex);
            }
        });
        updateQQPersenter.updateQQData(loginData);
    }

    /**
     * 腾讯获取用户信息
     */
    public void getUserInfo() {
        UserInfo userInfo = new UserInfo(this, mTencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LogUtil.e(o.toString());
                TencentUserInfo result = null;
                try {
                    Gson gson = new Gson();
                    result = gson.fromJson(o.toString(), new TypeToken<TencentUserInfo>() {
                    }.getType());
                    //qq返回信息存到本地
                    commonLoginData.setUserName(result.getNickname());
                    commonLoginData.setUserHeadImage(result.getFigureurl_qq_1());
                    commonLoginData.setUserDesc(result.getMsg());
                    SpUtil.updataLoginData(commonLoginData);
                    LogUtil.e(SpUtil.getLoginData().toString());
                    //上传qq消息
                    updateQQDate();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
