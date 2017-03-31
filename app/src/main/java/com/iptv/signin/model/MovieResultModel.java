package com.iptv.signin.model;

import com.iptv.signin.api.ApiManager;
import com.iptv.signin.bean.BaseMovie;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.listener.BaseModelListener;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class MovieResultModel extends BaseModel<BaseResult<List<BaseMovie>>> {
    public MovieResultModel(BaseModelListener<BaseResult<List<BaseMovie>>> modelListener) {
        this.modelListener = modelListener;
    }

    public void getMovieList(String userId) {
        ApiManager.getMovieList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<List<BaseMovie>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        modelListener.onError(e);
                    }

                    @Override
                    public void onNext(BaseResult<List<BaseMovie>> listBaseResult) {
                        modelListener.onNext(listBaseResult);
                    }
                });
    }

}
