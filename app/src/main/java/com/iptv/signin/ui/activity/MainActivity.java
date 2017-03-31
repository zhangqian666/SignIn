package com.iptv.signin.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.FrameLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.iptv.signin.R;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.persenter.SignUserInfoPersenter;
import com.iptv.signin.ui.fragment.ChatFragment;
import com.iptv.signin.ui.fragment.MineFragment;
import com.iptv.signin.ui.fragment.MovieFragment;
import com.iptv.signin.ui.fragment.SignInFragment;
import com.iptv.signin.utils.LogUtil;
import com.iptv.signin.utils.SpUtil;
import com.iptv.signin.view.SignUserInfoView;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.iptv.signin.bean.CommonData.localAddress;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_contain)
    FrameLayout mContain;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    private SignInFragment mSignInFragment;
    private ChatFragment mChatFragment;
    private MineFragment mMineFragment;
    private Unbinder mBind;
    private Tencent mTencent;
    private MovieFragment mMovieFragment;
    private BottomNavigationItem mineNavigationItem;
    public int mBadgeItemNum = 0;
    private boolean canShowMessage;
    private BadgeItem mBadgeItem;
    private int currentFragmentPosition = 0;

    /**
     * 初始化
     *
     * @param savedInstanceState 意外退出保存数据参数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);
        //初始化 底部导航
        initButtomNavigation();
        //融云开始链接
        String userRongIMToken = SpUtil.getLoginData().getUserRongIMToken();
        LogUtil.e(userRongIMToken);
        initConnectRongIM(userRongIMToken);
        initRegisterReceiver();
        //开始高德地图初始化
        initPermissionRequest();
    }

    private void initButtomNavigation() {
        refreButtonNavigation();
        setDefaultFragment();
        mBottomNavigationBar.setTabSelectedListener(new BaseTabSelecerListener());
    }

    private void initPermissionRequest() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            initAMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            showShort("必须同意才能正常使用定位功能");
                        }
                    }
                    initAMap();
                } else {
                    showShort("发生未知错误");
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        canShowMessage = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        canShowMessage = false;
    }

    private void initRegisterReceiver() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.rongim.receive.message");
        registerReceiver(rongImMessageReceiver, intentFilter);
    }
    /********************************************* RongIM ***********************************************/
    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void initConnectRongIM(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                LogUtil.e("聊天登录成功 ，账号：" + userid);
                showShort("聊天登录成功 ，账号：" + userid);

            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.e("聊天登录失败 ，erroCode：" + errorCode);
                showShort("聊天登录失败 ，erroCode：" + errorCode);
            }
        });

        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public io.rong.imlib.model.UserInfo getUserInfo(String userId) {

                return findUserById(userId);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
    }

    private io.rong.imlib.model.UserInfo findUserById(final String userId) {
        SignUserInfoPersenter signUserInfoPersenter = new SignUserInfoPersenter(new SignUserInfoView() {
            @Override
            public void onSuccess(BaseResult<LoginData> result) {
                LogUtil.e(result.getResult().toString());
                io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(userId, result.getResult().getUserName(), Uri.parse(result.getResult().getUserHeadImage()));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
            }

            @Override
            public void onError(String ex) {
                LogUtil.e(ex);
            }
        });
        signUserInfoPersenter.getSignUserInfo(userId);
        return null;
    }

    /*********************************************
     * 高德地图
     ***********************************************/
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
//                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    amapLocation.getLatitude();//获取纬度
//                    amapLocation.getLongitude();//获取经度
//                    amapLocation.getAccuracy();//获取精度信息
//                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    amapLocation.getCountry();//国家信息
//                    amapLocation.getProvince();//省信息
//                    amapLocation.getCity();//城市信息
//                    amapLocation.getDistrict();//城区信息
//                    amapLocation.getStreet();//街道信息
//                    amapLocation.getStreetNum();//街道门牌号信息
//                    amapLocation.getCityCode();//城市编码
//                    amapLocation.getAdCode();//地区编码
//                    amapLocation.getAoiName();//获取当前定位点的AOI信息
//                    amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//                    amapLocation.getFloor();//获取当前室内定位的楼层
////                    amapLocation.getGpsStatus();//获取GPS的当前状态
//                    //获取定位时间
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = new Date(amapLocation.getTime());
//                    df.format(date);
                    localAddress = amapLocation.getCity() + amapLocation.getDistrict() + amapLocation.getStreet() + amapLocation.getStreetNum();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }

        }
    };


    /**
     * 初始化高德地图
     */
    private void initAMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//        mLocationOption.setInterval(2000);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    /********************************************* initview ***********************************************/

    /**
     * 初始化 底部导航
     */
    private void refreButtonNavigation() {
        mBottomNavigationBar.clearAll();
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setBarBackgroundColor(R.color.colorPrimary);
        if (mBadgeItemNum > 0) {
            mBadgeItem = new BadgeItem()
                    .setBorderWidth(1)
                    .setBorderColorResource(R.color.white)
                    .setBackgroundColorResource(R.color.red)
                    .setText(mBadgeItemNum + "")
                    .setTextColorResource(R.color.white)
                    .setHideOnSelect(true);
            mBottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_sign
                            , getResources().getString(R.string.main_bottom_home)).setActiveColorResource(R.color.bottom_color_1))
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_chat
                            , getResources().getString(R.string.main_bottom_chat)).setActiveColorResource(R.color.bottom_color_2).setBadgeItem(mBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_movie
                            , getResources().getString(R.string.main_bottom_movie)).setActiveColorResource(R.color.bottom_color_3))
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_mine
                            , getResources().getString(R.string.main_bottom_mine)).setActiveColorResource(R.color.bottom_color_4));
        } else {
            mBottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_sign
                            , getResources().getString(R.string.main_bottom_home)).setActiveColorResource(R.color.bottom_color_1))
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_chat
                            , getResources().getString(R.string.main_bottom_chat)).setActiveColorResource(R.color.bottom_color_2))
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_movie
                            , getResources().getString(R.string.main_bottom_movie)).setActiveColorResource(R.color.bottom_color_3))
                    .addItem(new BottomNavigationItem(R.drawable.icon_main_mine
                            , getResources().getString(R.string.main_bottom_mine)).setActiveColorResource(R.color.bottom_color_4));
        }
        mBottomNavigationBar.setFirstSelectedPosition(currentFragmentPosition);
        mBottomNavigationBar.initialise();
    }

    private class BaseTabSelecerListener implements BottomNavigationBar.OnTabSelectedListener {


        @Override
        public void onTabSelected(int position) {
            currentFragmentPosition = position;
            Log.e(TAG, "onTabSelected: " + position);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
                case 0:
                    if (mSignInFragment == null) {
                        mSignInFragment = SignInFragment.newInstance("", "");
                    }
                    fragmentTransaction.replace(R.id.main_contain, mSignInFragment);
                    break;
                case 1:
                    if (mBadgeItemNum > 0) {
                        mBadgeItemNum = 0;
                        refreButtonNavigation();
                    }
                    if (mChatFragment == null) {
                        mChatFragment = ChatFragment.newInstance("", "");
                    }
                    fragmentTransaction.replace(R.id.main_contain, mChatFragment);
                    break;
                case 2:
                    if (mMovieFragment == null) {
                        mMovieFragment = MovieFragment.newInstance("", "");
                    }
                    fragmentTransaction.replace(R.id.main_contain, mMovieFragment);

                    break;
                case 3:
                    if (mMineFragment == null) {
                        mMineFragment = MineFragment.newInstance("", "");
                    }
                    fragmentTransaction.replace(R.id.main_contain, mMineFragment);

                    break;
                default:
                    if (mSignInFragment == null) {
                        mSignInFragment = SignInFragment.newInstance("", "");
                    }
                    fragmentTransaction.replace(R.id.main_contain, mSignInFragment);
                    break;
            }
            fragmentTransaction.commit();

        }

        @Override
        public void onTabUnselected(int position) {
        }

        @Override
        public void onTabReselected(int position) {
        }
    }


    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mSignInFragment = SignInFragment.newInstance("", "");
        fragmentTransaction.add(R.id.main_contain, mSignInFragment);
        fragmentTransaction.commit();
    }


    /**
     * 执行一些销毁工作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        unregisterReceiver(rongImMessageReceiver);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    BroadcastReceiver rongImMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (canShowMessage) {
                mBadgeItemNum++;
                if (currentFragmentPosition != 1) {
                    refreButtonNavigation();
                }
            }
        }
    };
}
