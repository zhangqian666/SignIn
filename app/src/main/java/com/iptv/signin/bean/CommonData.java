package com.iptv.signin.bean;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by ZhangQian on 2017/3/12 0012.
 */

public class CommonData {
    public static String mUserName = "测试用户_ONE";
    public static String mUserHeadImage = "http://img1.touxiang.cn/uploads/20131114/14-054928_741.jpg";
    public static String mUserId = "415677";
    public static String mUserPassword = "m415677";
    public static String mUserDesc = "特立独行的人";

    public static String mHomeImage = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489315449818&di=973f3d183bcacb79bdcfa87c724c222a&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4ec2d5628535e5ddd386c2df74c6a7efce1b6203.jpg";
    public static String mImagePersonalDescHead = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489315449818&di=973f3d183bcacb79bdcfa87c724c222a&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4ec2d5628535e5ddd386c2df74c6a7efce1b6203.jpg";

    public static String localAddress = "默认位置：-- --";
    public static String RongImServiceId = "KEFU149024409032074";
    /***************************
     * 重要数据 / 不能丢失
     *******************************************/

    public static String tencentAppID = "1106007238";
    /**
     * 用来保存所有已打开的Activity
     */
    public static Stack<Activity> listActivity = new Stack<Activity>();
}
