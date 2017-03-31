package com.iptv.signin.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iptv.signin.listener.BaseDiaLogListener;

import static com.iptv.signin.bean.CommonData.listActivity;

/**
 * Created by ZhangQian on 2017/3/11 0011.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * 记录上次点击按钮的时间
     **/
    private long lastClickTime;
    /**
     * 按钮连续点击最低间隔时间 单位：毫秒
     **/
    public final static int CLICK_TIME = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将activity推入栈中
        listActivity.push(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从栈中移除当前activity
        if (listActivity.contains(this)) {
            listActivity.remove(this);
        }
    }

    /**
     * 打开dialog
     *
     * @param title
     * @param messgage
     * @param leftBtn
     * @param rightBtn
     * @param baseDiaLogListener
     */

    public void showDiaLog(String title, String messgage, String leftBtn, String rightBtn, final BaseDiaLogListener baseDiaLogListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(messgage)) {
            builder.setMessage(messgage);
        }
        if (!TextUtils.isEmpty(leftBtn)) {
            builder.setPositiveButton(leftBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    baseDiaLogListener.setLeftBtn();
                }
            });
        }
        if (!TextUtils.isEmpty(rightBtn)) {
            builder.setNegativeButton(rightBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    baseDiaLogListener.setRightBtn();
                }
            });
        }
        builder.show();
    }

    /********************** activity跳转 **********************************/
    /**
     * 打开带参数的activity
     *
     * @param targetActivityClass
     * @param bundle
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 打开activity
     *
     * @param targetActivityClass
     */
    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

    /**
     * 打开activty 并且关闭当前activity
     *
     * @param targetActivityClass
     */
    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        this.finish();
    }
    /********************** 吐司 **********************************/
    /**
     * 短吐司
     *
     * @param text
     */
    public void showShort(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长吐司
     *
     * @param text
     */
    public void showLong(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /***************************************************************/

    /**
     * 验证上次点击按钮时间间隔，防止重复点击
     */
    public boolean verifyClickTime() {
        if (System.currentTimeMillis() - lastClickTime <= CLICK_TIME) {
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }

    /**
     * 收起键盘
     */
    public void closeInputMethod() {
        // 收起键盘
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭所有(前台、后台)Activity,注意：请已BaseActivity为父类
     */
    protected static void finishAll() {
        int len = listActivity.size();
        for (int i = 0; i < len; i++) {
            Activity activity = listActivity.pop();
            activity.finish();
        }
    }

    /*****************
     * 双击退出程序
     ************************************************/
    private long exitTime = 0;

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
//            if (System.currentTimeMillis() - exitTime > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                // 将系统当前的时间赋值给exitTime
//                exitTime = System.currentTimeMillis();
//            } else {
//                finishAll();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


}
