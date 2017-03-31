package com.iptv.signin.view;

import com.iptv.signin.bean.BaseResult;

/**
 * Created by ZhangQian on 2017/3/25 0025.
 */

public interface UpdateQQView {
    void updateSuccess(BaseResult<String> result);
    void updateError(String ex);
}
