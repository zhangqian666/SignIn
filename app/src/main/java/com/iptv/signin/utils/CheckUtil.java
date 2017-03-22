package com.iptv.signin.utils;

import android.text.TextUtils;

/**
 * Created by  on 16/5/28.
 */
public class CheckUtil {

    public static boolean isValidMobile(CharSequence mobile){
        if (TextUtils.isEmpty(mobile) || 11 != mobile.toString().trim().length() || !mobile.toString().trim().startsWith("1")) {
            return false;
        }
        return true;
    }

}
