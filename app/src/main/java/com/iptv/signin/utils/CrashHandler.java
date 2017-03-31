package com.iptv.signin.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import com.iptv.signin.persenter.CrashHandlerPostPersenter;
import com.iptv.signin.view.CrashHandlerPostView;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.iptv.signin.SignInApplication.mContext;

/**
 * Created by ZhangQian on 2017/3/13 0013.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static CrashHandler instance;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private String exMessage;

    /**
     * 获取CrashHandler实例 ,单例模式
     */

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化CrashHandler
     */
    public void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // TODO Auto-generated method stub
        ex.printStackTrace();
        if (!handleException(thread, ex) && mDefaultCrashHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            Log.e(TAG, "uncaughtException: " );
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Log.e(TAG, "uncaughtException: 1" +ex.getMessage());
        }
    }

    private boolean handleException(Thread thread, final Throwable ex) {
        if (ex == null) {
            Log.e(TAG, "handleException: " );
            return false;
        }
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Log.e(TAG, "run: " );
//                showDialog(mContext,ex);
                Looper.loop();
            }
        }.start();
        return true;
    }

    private String getExMessage(Throwable ex){
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        return writer.toString();
    };
    private void showDialog(final Context context, final Throwable ex) {
        final Dialog dialog;
        AlertDialog.Builder buder = new AlertDialog.Builder(mContext);
        buder.setTitle("温馨提示");
        buder.setMessage("由于发生了一个未知错误，应用已关闭，我们对此引起的不便表示抱歉！" +
                "您可以将错误信息上传到我们的服务器，帮助我们尽快解决该问题，谢谢！");
        buder.setPositiveButton("上传", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //上传处理---我这里没写，大家根据实际情况自己补上，我这里是一个Toast提示，提示内容就是我们要上传的信息
                Log.e(TAG, "---" + getExMessage(ex));
                CrashHandlerPostPersenter crashHandlerPostPersenter = new CrashHandlerPostPersenter(new CrashHandlerPostView() {
                    @Override
                    public void onCompleted(boolean isSuccess) {
                        //退出程序
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
                crashHandlerPostPersenter.getErrorCrashHandlerResult("0010049900511A7000A700226D11B9DE",getDeviceInfo(),getExMessage(ex));


            }
        });
        buder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        dialog = buder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setCanceledOnTouchOutside(false);//设置点击屏幕其他地方，dialog不消失
        dialog.setCancelable(false);//设置点击返回键和HOme键，dialog不消失
        dialog.show();

        Log.i("PLog", "2");
    }

    /**
     * 获取指定字段信息
     *
     * @return
     */
    private String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\nBOARD：" + Build.BOARD);
        sb.append("\nBOOTLOADER：" + Build.BOOTLOADER);
        sb.append("\nBRAND：" + Build.BRAND);
        sb.append("\nCPU_ABI：" + Build.CPU_ABI);
        sb.append("\nCPU_ABI2：" + Build.CPU_ABI2);
        sb.append("\nDEVICE：" + Build.DEVICE);
        sb.append("\nDISPLAY：" + Build.DISPLAY);
        sb.append("\nRadioVersion：" + Build.getRadioVersion());
        sb.append("\nFINGERPRINT：" + Build.FINGERPRINT);
        sb.append("\nHARDWARE：" + Build.HARDWARE);
        sb.append("\nHOST:" + Build.HOST);
        sb.append("\nID：" + Build.ID);
        sb.append("\nMANUFACTURER：" + Build.MANUFACTURER);
        sb.append("\nMODEL：" + Build.MODEL);
        sb.append("\nSERIAL：" + Build.SERIAL);
        sb.append("\nPRODUCT：" + Build.PRODUCT);
        sb.append("\nTAGS：" + Build.TAGS);
        sb.append("\nTIME:" + Build.TIME);
        sb.append("\nbuilder_type：" + Build.TYPE);
        sb.append("\nUSER:" + Build.USER);
        return sb.toString();
    }
}
