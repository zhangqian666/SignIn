package com.iptv.signin.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iptv.signin.R;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.ui.activity.MainActivity;
import com.iptv.signin.ui.activity.MineFriendsActivity;
import com.iptv.signin.ui.activity.MineItemSetActivity;
import com.iptv.signin.ui.activity.PersonalDescActivity;
import com.iptv.signin.utils.LogUtil;
import com.iptv.signin.utils.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.iptv.signin.SignInApplication.mContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.toolbar_mine_fragment)
    Toolbar mToolBar;
    @BindView(R.id.image_mine_header)
    ImageView mImageMineHeader;
    @BindView(R.id.text_name_mine_header)
    TextView mTextNameMineHeader;
    @BindView(R.id.text_desc_mine_content)
    TextView mTextDescMineContent;
    @BindView(R.id.ll_head_mine)
    LinearLayout mllHeadMine;
    @BindView(R.id.ll_item_album_mine_content)
    LinearLayout mllItemAlbumMineContent;
    @BindView(R.id.ll_item_save_mine_content)
    LinearLayout mllItemSaveMineContent;
    @BindView(R.id.ll_item_set_mine_content)
    LinearLayout llItemSetMineContent;
    @BindView(R.id.ll_item_friends_mine_content)
    LinearLayout llItemFriendsMineContent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mRootView;
    private MainActivity mMainActivity;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mMainActivity = ((MainActivity) activity);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, mRootView);
        LogUtil.e("创建了MineFragment");
        LogUtil.e(SpUtil.getLoginData().toString());
        initActionBar();
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        LoginData loginData = SpUtil.getLoginData();
        Glide.with(mContext).load(loginData.getUserHeadImage()).into(mImageMineHeader);
        mTextNameMineHeader.setText(loginData.getUserName());
        mTextDescMineContent.setText(loginData.getUserDesc());
    }

    /**
     * 初始化actionbar
     */
    private void initActionBar() {
        mMainActivity.setSupportActionBar(mToolBar);
        mMainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) mToolBar.findViewById(R.id.toolbar_title)).setText("个人页面");
    }


    @OnClick({R.id.ll_head_mine, R.id.ll_item_album_mine_content, R.id.ll_item_save_mine_content, R.id.ll_item_set_mine_content, R.id.ll_item_friends_mine_content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head_mine:
                startActivity(new Intent(getActivity(), PersonalDescActivity.class));
                break;
            case R.id.ll_item_album_mine_content:
                break;
            case R.id.ll_item_save_mine_content:
                break;
            case R.id.ll_item_set_mine_content:
                mMainActivity.openActivity(MineItemSetActivity.class);
                break;
            case R.id.ll_item_friends_mine_content:
                mMainActivity.openActivity(MineFriendsActivity.class);
                break;
        }
    }

    /**
     * 更新个人信息
     */
    public void updataPersonalData() {
        initUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("销毁了MineFragment");
    }
}


