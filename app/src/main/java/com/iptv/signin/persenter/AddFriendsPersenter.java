package com.iptv.signin.persenter;

import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.listener.BaseModelListener;
import com.iptv.signin.model.AddFriendsModel;
import com.iptv.signin.view.BaseView;

/**
 * Created by ZhangQian on 2017/3/29 0029.
 */

public class AddFriendsPersenter extends BasePersenter<BaseResult<String>> implements BaseModelListener<BaseResult<String>> {
    private final AddFriendsModel addFriendsModel;

    public AddFriendsPersenter(BaseView<BaseResult<String>> baseView) {
        super();
        this.baseView = baseView;
        addFriendsModel = new AddFriendsModel(this);
    }

    public void addFriends(String userId, String fllowUserId) {
        addFriendsModel.addFriends(userId, fllowUserId);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(BaseResult<String> result) {
        baseView.onSuccess(result);
    }

    @Override
    public void onError(Throwable e) {
        baseView.onError(e.toString());
    }
}
