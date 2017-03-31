package com.iptv.signin.ui.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iptv.signin.R;
import com.iptv.signin.bean.CommonData;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.ui.activity.ChatAddFriendsActivity;
import com.iptv.signin.ui.activity.MainActivity;
import com.iptv.signin.ui.adapter.UserFriendsListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.Conversation;

import static com.iptv.signin.SignInApplication.mContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.toolbar_sign_in_fragment)
    Toolbar mToolBar;
    @BindView(R.id.fabButton)
    FloatingActionButton fabButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mRootView;
    private Unbinder mBind;
    private UserFriendsListAdapter mSignListAdapter;
    private MainActivity mMainActivity;
    private List<LoginData> mUserChatList = new ArrayList<>();

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mMainActivity = ((MainActivity) context);
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
        mRootView = inflater.inflate(R.layout.fragment_chat, container, false);
        mBind = ButterKnife.bind(this, mRootView);

        initActionBar();
        //初始化朋友列表
        initChatFragment();
        initFrbBtn();
        return mRootView;
    }

    private void initChatFragment() {
        ConversationListFragment listFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + mContext.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();

        listFragment.setUri(uri);
        getChildFragmentManager().beginTransaction().replace(R.id.ll_chat, listFragment).commit();
    }

    private void initFrbBtn() {
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先需要构造使用客服者的用户信息
                CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
                CSCustomServiceInfo csInfo = csBuilder.nickName("融云").build();
                RongIM.getInstance().startCustomerServiceChat(getActivity(), CommonData.RongImServiceId, "在线客服", csInfo);
            }
        });
    }

    /**
     * 初始化ACtionbar
     */
    private void initActionBar() {
        setHasOptionsMenu(true);
        mMainActivity.setSupportActionBar(mToolBar);
        mMainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) mToolBar.findViewById(R.id.toolbar_title)).setText("最近联系人");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                mMainActivity.openActivity(ChatAddFriendsActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMainActivity.getMenuInflater().inflate(R.menu.menu_chat, menu);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBind.unbind();
    }

}
