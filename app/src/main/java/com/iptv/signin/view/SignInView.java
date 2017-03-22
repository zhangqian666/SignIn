package com.iptv.signin.view;

import com.iptv.signin.bean.SignInTime;

import java.util.List;

/**
 * Created by ZhangQian on 2017/3/12 0012.
 */

public interface SignInView {
    void setAllSignInMessage(List<SignInTime> signInTimes);
    void setSaveResult(boolean isSuccess);
}
