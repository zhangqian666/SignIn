package com.iptv.signin.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iptv.signin.R;
import com.iptv.signin.bean.BaseMovie;
import com.iptv.signin.ui.adapter.baseinterface.BaseOnclickListener;
import com.iptv.signin.utils.LogUtil;
import com.iptv.signin.utils.ScreenUtils;

import java.util.List;

import static com.iptv.signin.SignInApplication.mContext;

/**
 * Created by ZhangQian on 2017/3/27 0027.
 */

public class BaseMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    Context context;
    List<BaseMovie> baseMovies;
    private View mHeaderView;

    public BaseMovieAdapter(Context context, List<BaseMovie> baseMovies) {
        super();
        this.context = context;
        this.baseMovies = baseMovies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.e(viewType + "--" + "onCreateViewHolder");
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new HeaderViewHolder(mHeaderView);
        View item1 = LayoutInflater.from(mContext).inflate(R.layout.item_movie_fragment, parent, false);
        return new MovieViewHolder(item1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        if (holder instanceof MovieViewHolder) {
            if (mHeaderView != null) {
                position = position - 1;
            }
            MovieViewHolder viewHolder = (MovieViewHolder) holder;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewHolder.imageMovieItem.getLayoutParams();
            int itemWidth = (ScreenUtils.getScreenWidth(mContext) - 8 * 4) / 3;
            int itemHeight = (int) (itemWidth * 1.5);
            layoutParams.width = itemWidth;
            layoutParams.height = itemHeight;
            ((MovieViewHolder) holder).imageMovieItem.setLayoutParams(layoutParams);
            Glide.with(mContext).load(baseMovies.get(position).getMovieImage()).into(((MovieViewHolder) holder).imageMovieItem);
            ((MovieViewHolder) holder).textMovieItem.setText(baseMovies.get(position).getMovieName() + "\n" + baseMovies.get(position).getMovieDesc());
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? baseMovies.size() : baseMovies.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    private BaseOnclickListener<BaseMovie> onclickListener;

    public void setOnclickListener(BaseOnclickListener<BaseMovie> onclickListener) {
        this.onclickListener = onclickListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMovieItem;
        TextView textMovieItem;

        MovieViewHolder(View view) {
            super(view);

            imageMovieItem = ((ImageView) view.findViewById(R.id.image_movie_item));
            textMovieItem = ((TextView) view.findViewById(R.id.text_movie_item));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickListener.onClick(getAdapterPosition(), baseMovies.get(getAdapterPosition()));
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
