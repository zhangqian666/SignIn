package com.iptv.signin.persenter;

import com.iptv.signin.bean.BaseMovie;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.listener.BaseModelListener;
import com.iptv.signin.model.MovieResultModel;
import com.iptv.signin.view.BaseView;

import java.util.List;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class MovieResultPersenter implements BaseModelListener<BaseResult<List<BaseMovie>>> {
    private final MovieResultModel movieResultModel;

    BaseView<BaseResult<List<BaseMovie>>> baseView;

    public MovieResultPersenter(BaseView<BaseResult<List<BaseMovie>>> baseView) {
        super();
        movieResultModel = new MovieResultModel(this);
        this.baseView = baseView;
    }

    public void getMovieList(String userId) {
        movieResultModel.getMovieList(userId);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(BaseResult<List<BaseMovie>> result) {
        baseView.onSuccess(result);
    }

    @Override
    public void onError(Throwable e) {
        baseView.onError(e.getMessage().toString());
    }

}
