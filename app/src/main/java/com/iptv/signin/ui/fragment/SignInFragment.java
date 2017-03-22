package com.iptv.signin.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iptv.signin.R;

import java.util.concurrent.ConcurrentHashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mRootView;
    private TextView mBtnEnter;
    private TextView mBtn1Enter;
    private Toolbar mToolBar;


    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        mRootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mBtnEnter = (TextView) mRootView.findViewById(R.id.btn_sign_in_fragment);
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConcurrentHashMap<String, Boolean> supportedConversation = new ConcurrentHashMap<String, Boolean>();
                supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);
                RongIM.getInstance().startConversationList(getActivity(), supportedConversation);
            }
        });
        mBtn1Enter = (TextView) mRootView.findViewById(R.id.btn1_sign_in_fragment);
        mBtn1Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(getActivity(), "415678", "标题-“1号客服”为您服务");
            }
        });

        mToolBar = ((Toolbar) mRootView.findViewById(R.id.toolbar_sign_in_fragment));
        initActionBar();
        return mRootView;
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);
        ActionBar supportActionBar = activity.getSupportActionBar();
        supportActionBar.setTitle("签到聊天");
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
}
