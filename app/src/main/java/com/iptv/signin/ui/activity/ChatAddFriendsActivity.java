package com.iptv.signin.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.iptv.signin.R;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.persenter.AddFriendsPersenter;
import com.iptv.signin.persenter.SignUserInfoPersenter;
import com.iptv.signin.ui.adapter.UserFriendsListAdapter;
import com.iptv.signin.utils.SpUtil;
import com.iptv.signin.view.BaseView;
import com.iptv.signin.view.SignUserInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.iptv.signin.SignInApplication.mContext;

public class ChatAddFriendsActivity extends BaseActivity {

    @BindView(R.id.user_mobile)
    TextInputEditText userMobile;
    @BindView(R.id.user_list)
    RecyclerView userList;
    @BindView(R.id.select_btn)
    Button selectBtn;
    private Toolbar mToolBar;
    private List<LoginData> lists = new ArrayList<>();
    private UserFriendsListAdapter mListAdapter;
    private Boolean isAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_add_friends);
        ButterKnife.bind(this);
        initActionBar();
        initSelectList();
    }

    private void initSelectList() {
        userList.setLayoutManager(new LinearLayoutManager(mContext));
        mListAdapter = new UserFriendsListAdapter(mContext, lists);
        userList.setAdapter(mListAdapter);
        userList.requestFocus();
        mListAdapter.setItemOnclickListener(new UserFriendsListAdapter.ItemOnclickListener() {
            @Override
            public void onClick(int position, LoginData loginData) {
                if (!isAdd) {
                    postAddFriends(loginData);
                }
            }
        });
    }

    private void postAddFriends(LoginData addloginData) {
        String userId = SpUtil.getLoginData().getUserId();
        String adduserId = addloginData.getUserId();
        AddFriendsPersenter addFriendsPersenter = new AddFriendsPersenter(new BaseView<BaseResult<String>>() {
            @Override
            public void onError(String ex) {
                showShort("请求失败 : " + ex);
            }

            @Override
            public void onSuccess(BaseResult<String> result) {
                if (result.getCode() == 200) {
                    showShort("添加成功");
                    isAdd = true;
                } else {
                    showShort("添加失败");
                }
            }
        });
        addFriendsPersenter.addFriends(userId, adduserId);

    }

    private void initActionBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) mToolBar.findViewById(R.id.toolbar_title)).setText("添加好友");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick(R.id.select_btn)
    public void onClick() {
        String mUserMobile = userMobile.getText().toString().trim();
        SignUserInfoPersenter signUserInfoPersenter = new SignUserInfoPersenter(new SignUserInfoView() {
            @Override
            public void onSuccess(BaseResult<LoginData> result) {
                lists.clear();
                if (result.getCode() == 200 && !TextUtils.isEmpty(result.getResult().getUserName())) {
                    lists.add(result.getResult());
                } else {
                    showShort("没有查到用户");
                }
                mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String ex) {
                showShort("查询失败");
            }
        });
        signUserInfoPersenter.getSignUserInfo(mUserMobile);
    }
}
