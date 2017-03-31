package com.iptv.signin.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iptv.signin.R;
import com.iptv.signin.bean.BaseMovie;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.persenter.MovieResultPersenter;
import com.iptv.signin.ui.activity.MainActivity;
import com.iptv.signin.ui.activity.MovieWebViewActivity;
import com.iptv.signin.ui.adapter.BaseMovieAdapter;
import com.iptv.signin.ui.adapter.BasePagerViewAdapter;
import com.iptv.signin.ui.adapter.baseinterface.BaseOnclickListener;
import com.iptv.signin.utils.LogUtil;
import com.iptv.signin.view.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.iptv.signin.SignInApplication.mContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    String imageUrl = "http://sc.jb51.net/uploads/allimg/150709/14-150FZ94211O4.jpg";
    @BindView(R.id.toolbar_movie)
    Toolbar mToolBar;
    @BindView(R.id.recycle_movie)
    RecyclerView recycleMovie;
    @BindView(R.id.refresh_movie)
    SwipeRefreshLayout refreshMovie;

    List<ImageView> views = new ArrayList<>();
    List<BaseMovie> baseMovies = new ArrayList<>();
    private MainActivity mMainActivity;
    private BaseMovieAdapter mBaseMovieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(String param1, String param2) {
        return new MovieFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mMainActivity = ((MainActivity) context);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        initActionBar();
        initRecycleView();
        postGetMovieList();
        return view;
    }

    private void initRecycleView() {
        mBaseMovieAdapter = new BaseMovieAdapter(mContext, baseMovies);
        mBaseMovieAdapter.setOnclickListener(new BaseOnclickListener<BaseMovie>() {
            @Override
            public void onClick(int posistion, BaseMovie item) {
                Bundle bundle = new Bundle();
                bundle.putString("URL", item.getMovieUrl());
                mMainActivity.openActivity(MovieWebViewActivity.class, bundle);
            }
        });
        refreshMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postGetMovieList();
            }
        });
        recycleMovie.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recycleMovie.setAdapter(mBaseMovieAdapter);
//        initHeaderView(recycleMovie);
    }

    private void initHeaderView(RecyclerView mRecycle) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_header_movie_fragment, mRecycle, false);
        ViewPager mViewPager = (ViewPager) inflate.findViewById(R.id.viewpager_movie);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(mContext);
            Glide.with(mContext).load(imageUrl).into(imageView);
            views.add(imageView);
        }
        BasePagerViewAdapter basePagerViewAdapter = new BasePagerViewAdapter(views);
        mViewPager.setAdapter(basePagerViewAdapter);
        mBaseMovieAdapter.setHeaderView(inflate);
    }

    /**
     * 初始化actionbar
     */
    private void initActionBar() {
        mMainActivity.setSupportActionBar(mToolBar);
        mMainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) mToolBar.findViewById(R.id.toolbar_title)).setText("电影页面");
    }

    public void postGetMovieList() {
        MovieResultPersenter movieResultPersenter = new MovieResultPersenter(new BaseView<BaseResult<List<BaseMovie>>>() {
            @Override
            public void onError(String ex) {
                mMainActivity.showShort("请求电影数据失败");
            }

            @Override
            public void onSuccess(BaseResult<List<BaseMovie>> result) {
                if (refreshMovie.isRefreshing()) {
                    refreshMovie.setRefreshing(false);
                }
                if (result.getCode() == 200) {
                    LogUtil.e(result.getMsg());
                    baseMovies.clear();
                    baseMovies.addAll(result.getResult());
                    mBaseMovieAdapter.notifyDataSetChanged();
                } else {
                    mMainActivity.showShort("请求电影数据失败");
                }

            }
        });
        movieResultPersenter.getMovieList("123");
    }

}
