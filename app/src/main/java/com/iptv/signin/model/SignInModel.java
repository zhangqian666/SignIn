package com.iptv.signin.model;

import android.widget.Toast;

import com.iptv.signin.api.ApiManager;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.SignInTime;
import com.iptv.signin.listener.BaseModelListener;
import com.iptv.signin.utils.LogUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.iptv.signin.SignInApplication.mContext;

/**
 * Created by ZhangQian on 2017/3/12 0012.
 */

public class SignInModel extends BaseModel<SignInTime> {

    private List<SignInTime> signInTimes;
    private SignInModelListener signInTimeBaseModelListener;

    public SignInModel(SignInModelListener signInTimeBaseModelListener) {
        super();
        this.signInTimeBaseModelListener = signInTimeBaseModelListener;
    }

    public void saveSignInMessage(SignInTime signInTime) {
        ApiManager.setUserSignData(signInTime.getUserId(), signInTime.getSignUserName(), signInTime.getSignData(), signInTime.getSignAdress(), signInTime.getSignDesc(), signInTime.getSignUserImage(), signInTime.getSignId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.toString());
                        Toast.makeText(mContext, "签到成功", Toast.LENGTH_SHORT).show();
                        signInTimeBaseModelListener.setSaveResult(false);
                    }

                    @Override
                    public void onNext(BaseResult<String> stringBaseResult) {
                        LogUtil.e("存储成功");
                        signInTimeBaseModelListener.setSaveResult(true);
                    }
                });


    }

    public void getSignInMessage(String userId) {
        ApiManager.getUserSignDataList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<List<SignInTime>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("获取失败 ：" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(BaseResult<List<SignInTime>> listBaseResult) {
                        if (listBaseResult.getCode() == 200) {

                            signInTimeBaseModelListener.setResulList(listBaseResult.getResult());
                        } else {
                            LogUtil.e("获取失败");
                        }
                    }
                });
//        signInTimes = DataSupport.findAll(SignInTime.class);
//        if (signInTimes == null &signInTimes.size() ==0) {
//
//        }else {
//            signInTimeBaseModelListener.setResulList(signInTimes);
//        }
    }

    public void deleteSignInMessage(SignInTime signInTime) {
        ApiManager.deleteUserSignData(signInTime.getUserId(), signInTime.getSignId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResult<String> stringBaseResult) {
                        signInTimeBaseModelListener.setSaveResult(true);
                    }
                });
    }

    public interface SignInModelListener extends BaseModelListener<SignInTime> {
        void setSaveResult(boolean isSuccess);

        void setResulList(List<SignInTime> resultList);
    }
}
