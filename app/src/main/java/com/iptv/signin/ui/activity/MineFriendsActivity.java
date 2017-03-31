package com.iptv.signin.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.iptv.signin.R;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.persenter.UserFriendsListPersenter;
import com.iptv.signin.ui.adapter.UserFriendsListAdapter;
import com.iptv.signin.utils.SpUtil;
import com.iptv.signin.view.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

import static com.iptv.signin.SignInApplication.mContext;

public class MineFriendsActivity extends BaseActivity {
    @BindView(R.id.recycler_sign)
    RecyclerView mSignList;
    @BindView(R.id.refresh_sign)
    SwipeRefreshLayout mSwipeSignList;
    @BindView(R.id.toolbar_friends)
    Toolbar mToolBar;
    private UserFriendsListAdapter mSignListAdapter;
    private List<LoginData> mUserFrinedsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_friends);
        ButterKnife.bind(this);
        initActionBar();
        initSignFriendsList();
    }

    /**
     * 初始化ACtionbar
     */
    private void initActionBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) mToolBar.findViewById(R.id.toolbar_title)).setText("好友列表");

    }

    /**
     * 初始化签到朋友列表
     */
    private void initSignFriendsList() {
        mSwipeSignList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postchatList();
            }
        });
        mSwipeSignList.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);

        mSignList.setLayoutManager(new LinearLayoutManager(mContext));

        mSignListAdapter = new UserFriendsListAdapter(mContext, mUserFrinedsList);
        mSignListAdapter.setItemOnclickListener(new UserFriendsListAdapter.ItemOnclickListener() {
            @Override
            public void onClick(int position, LoginData loginData) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(MineFriendsActivity.this, loginData.getUserRongIMId(), loginData.getUserName());
                }
            }
        });
        mSignList.setAdapter(mSignListAdapter);
        postchatList();
    }

    private void postchatList() {
        UserFriendsListPersenter userFriendsListPersenter = new UserFriendsListPersenter(new BaseView<BaseResult<List<LoginData>>>() {
            @Override
            public void onError(String ex) {
                mSwipeSignList.setRefreshing(false);
                showShort("拉取数据失败");
            }

            @Override
            public void onSuccess(BaseResult<List<LoginData>> result) {
                if (result.getCode() == 200) {
                    if (mSwipeSignList.isRefreshing()) {
                        mSwipeSignList.setRefreshing(false);
                    }
                    mUserFrinedsList.clear();
                    mUserFrinedsList.addAll(result.getResult());
                    mSignListAdapter.notifyDataSetChanged();
                } else {
                    mSwipeSignList.setRefreshing(false);
                    showShort("拉取数据失败");
                }
            }
        });
        userFriendsListPersenter.getUserFriendList(SpUtil.getLoginData().getUserId());
    }

}
