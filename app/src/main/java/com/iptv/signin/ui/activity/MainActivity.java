package com.iptv.signin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.iptv.signin.R;
import com.iptv.signin.bean.CommonData;
import com.iptv.signin.service.SignInService;
import com.iptv.signin.ui.fragment.HomeFragment;
import com.iptv.signin.ui.fragment.MineFragment;
import com.iptv.signin.ui.fragment.SignInFragment;

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
    private HomeFragment mHomeFragment;
    private SignInFragment mSignInFragment;
    private MineFragment mMineFragment;
    private Unbinder mBind;

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
        //开始后台签到服务
        startSignInServer();
        //融云开始链接
        initConnectRongIM(CommonData.token);
        //开始高德地图初始化
        initAMap();
    }

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
                Log.d("LoginActivity", "--onSuccess" + userid);

            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }

    /**
     * 初始化 底部导航
     */
    private void initButtomNavigation() {
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp
                        , getResources().getString(R.string.main_bottom_home)).setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp
                        , getResources().getString(R.string.main_bottom_location)).setActiveColor(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp
                        , getResources().getString(R.string.main_bottom_mine)).setActiveColor(R.color.grey))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        mBottomNavigationBar.setBarBackgroundColor(R.color.blue);

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.e(TAG, "onTabSelected: " + position);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        if (mHomeFragment == null) {
                            mHomeFragment = HomeFragment.newInstance("", "");
                        }
                        fragmentTransaction.replace(R.id.main_contain, mHomeFragment);
                        break;
                    case 1:
                        if (mSignInFragment == null) {
                            mSignInFragment = SignInFragment.newInstance("", "");
                        }
                        fragmentTransaction.replace(R.id.main_contain, mSignInFragment);
                        break;
                    case 2:
                        if (mMineFragment == null) {
                            mMineFragment = MineFragment.newInstance("", "");
                        }
                        fragmentTransaction.replace(R.id.main_contain, mMineFragment);

                        break;
                    default:
                        if (mHomeFragment == null) {
                            mHomeFragment = HomeFragment.newInstance("", "");
                        }
                        fragmentTransaction.replace(R.id.main_contain, mHomeFragment);
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
        });

        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mHomeFragment = HomeFragment.newInstance("", "");
        fragmentTransaction.add(R.id.main_contain, mHomeFragment);
        fragmentTransaction.commit();
    }


    /**
     * 执行一些销毁工作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }


    /**
     * 开启后台监听签到服务
     */
    private void startSignInServer() {
        Intent signInServerIntent = new Intent(this, SignInService.class);
        startService(signInServerIntent);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
