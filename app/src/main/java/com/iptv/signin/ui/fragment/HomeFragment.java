package com.iptv.signin.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iptv.signin.R;
import com.iptv.signin.adapter.SignInTimeAdapter;
import com.iptv.signin.bean.CommonData;
import com.iptv.signin.bean.SignInTime;
import com.iptv.signin.persenter.SignInPersenter;
import com.iptv.signin.ui.activity.BaseActivity;
import com.iptv.signin.ui.activity.MainActivity;
import com.iptv.signin.view.SignInView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.iptv.signin.others.SignInApplication.mContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View mRootView;
    private AddTimeBroadcastReceiver addTimeBroadcastReceiver;
    private List<SignInTime> signInTimes = new ArrayList<>();
    private SignInTimeAdapter mAdapter;
    private RecyclerView mRecycleView;
    private FloatingActionButton mFabBtn;
    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;

    public HomeFragment() {
        // Required empty public constructor
        signInPersenter = new SignInPersenter(mSignInView);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        mDrawerLayout = (DrawerLayout) mRootView.findViewById(R.id.drawer_home_fragment);
        mToolBar = (Toolbar) mRootView.findViewById(R.id.main_toolbar);
        mRecycleView = ((RecyclerView) mRootView.findViewById(R.id.recycle_view));
        mFabBtn = (FloatingActionButton) mRootView.findViewById(R.id.fabButton);
        ImageView mleftContent = (ImageView) mRootView.findViewById(R.id.left_content);
        mleftContent.setOnTouchListener(new AutoScrollHelper(container) {
            @Override
            public void scrollTargetBy(int deltaX, int deltaY) {

            }

            @Override
            public boolean canTargetScrollHorizontally(int direction) {
                return false;
            }

            @Override
            public boolean canTargetScrollVertically(int direction) {
                return false;
            }
        });
        Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489315449818&di=973f3d183bcacb79bdcfa87c724c222a&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4ec2d5628535e5ddd386c2df74c6a7efce1b6203.jpg")
                .centerCrop().into(mleftContent);

        initActionBar();
        //初始化 floatActionBtn
        initfabBtn();
        //注册接收
        mRegisterReceiver();
        //在数据库获得数据
        loadDatas();
        //初始化RecycleView
        showRecycle();
        // Inflate the layout for this fragment
        return mRootView;
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);
        ActionBar supportActionBar = activity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_actionbar);
        supportActionBar.setTitle("签到主页");
    }

    /**
     * 对侧滑进行监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "onOptionsItemSelected: " + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化FloatingActionButton
     */
    private void initfabBtn() {

        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSignInMessage();
            }
        });
    }

    /**
     * 注册接受 签到的事件
     */
    private void mRegisterReceiver() {
        addTimeBroadcastReceiver = new AddTimeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.iptv.ADDTIME");
        getActivity().registerReceiver(addTimeBroadcastReceiver, intentFilter);
    }


    /**
     * 接受notification 发来的信息
     */
    public class AddTimeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            saveSignInMessage();
            if (signInPersenter == null) {
                signInPersenter = new SignInPersenter(mSignInView);
            }
            signInPersenter.getSignInMessage("");
        }
    }

    /**
     * 获取当前时间 并且存到litepal 数据库
     */
    public String getTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
        return sDateFormat.format(new Date());
    }

    public void saveSignInMessage() {
        SignInTime signInTime = new SignInTime();
        signInTime.setUser(CommonData.mUserName);
        signInTime.setContent(getTime());
        signInTime.setLocalAddress(CommonData.localAddress);
        if (signInPersenter == null) {
            signInPersenter = new SignInPersenter(mSignInView);
        }
        signInPersenter.saveSignInMessage(signInTime);
    }

    /**
     * 对于数据返回结果的处理
     */
    SignInPersenter signInPersenter = null;
    SignInView mSignInView = new SignInView() {
        @Override
        public void setAllSignInMessage(List<SignInTime> signInTimesRes) {
            signInTimes.clear();
            signInTimes.addAll(signInTimesRes);
            Log.e(TAG, "loadDatas: " + "signintimes size" + signInTimes.size());
            if (mAdapter == null) {
                mAdapter = new SignInTimeAdapter(signInTimes);
                mRecycleView.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void setSaveResult(boolean isSuccess) {
            if (isSuccess) {
                Toast.makeText(mContext, "恭喜您，签到成功", Toast.LENGTH_SHORT).show();
                signInPersenter.getSignInMessage("");
            } else {
                Toast.makeText(mContext, "很遗憾，签到失败。请重试", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 获取数据库的数据
     */
    private void loadDatas() {
        if (signInPersenter == null) {
            signInPersenter = new SignInPersenter(mSignInView);
        }
        signInPersenter.getSignInMessage("");
    }

    /**
     * 初始化 recycleView
     */
    private void showRecycle() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecycleView.setLayoutManager(linearLayoutManager);

        mAdapter = new SignInTimeAdapter(signInTimes);
        mAdapter.setSignInTimeAdapterOnLongClickListener(new SignInTimeAdapter.SignInTimeAdapterOnLongClickListener() {
            @Override
            public void onLongClick(View v, int position, final SignInTime signInTime) {
                if (getActivity() instanceof MainActivity) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.showDiaLog("删除操作", "是否删除该条信息", "是", "否", new BaseActivity.BaseDiaLogListener() {
                        @Override
                        public void setLeftBtn() {
                            signInPersenter.deleteSignInMessage(signInTime, signInTime.getContent());
                            signInPersenter.getSignInMessage("");
                        }

                        @Override
                        public void setRightBtn() {

                        }
                    });

                }


            }
        });
        mRecycleView.setAdapter(mAdapter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(addTimeBroadcastReceiver);
    }

}
