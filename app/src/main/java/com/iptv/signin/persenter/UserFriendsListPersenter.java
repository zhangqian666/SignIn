package com.iptv.signin.persenter;

import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.listener.BaseModelListener;
import com.iptv.signin.model.UserFriendListModel;
import com.iptv.signin.view.BaseView;

import java.util.List;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class UserFriendsListPersenter implements BaseModelListener<BaseResult<List<LoginData>>> {

    private final UserFriendListModel listModel;
    BaseView<BaseResult<List<LoginData>>> baseView;

    public UserFriendsListPersenter(BaseView<BaseResult<List<LoginData>>> baseView) {
        super();
        listModel = new UserFriendListModel(this);
        this.baseView = baseView;
    }

    public void getUserFriendList(String userId) {
        listModel.getUserFriendsList(userId);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(BaseResult<List<LoginData>> result) {
        baseView.onSuccess(result);
    }

    @Override
    public void onError(Throwable e) {
        baseView.onError(e.toString());
    }
}
