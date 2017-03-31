package com.iptv.signin.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iptv.signin.R;
import com.iptv.signin.bean.LoginData;

import java.util.List;

import static com.iptv.signin.SignInApplication.mContext;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class UserFriendsListAdapter extends RecyclerView.Adapter<UserFriendsListAdapter.MViewHolder> {

    Context context;
    List<LoginData> loginDatas;


    public UserFriendsListAdapter(Context context, List<LoginData> loginDatas) {
        super();
        this.context = context;
        this.loginDatas = loginDatas;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_user_friends, parent, false);
        final MViewHolder mViewHolder = new MViewHolder(inflate);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {

        final LoginData loginData = loginDatas.get(position);
        Glide.with(context).load(loginData.getUserHeadImage()).into(holder.imageMineHeader);
        holder.textNameMineHeader.setText(loginData.getUserName());
        holder.textDescMineContent.setText(loginData.getUserDesc());
    }

    @Override
    public int getItemCount() {
        return loginDatas.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMineHeader;
        TextView textNameMineHeader;
        TextView textDescMineContent;

        public MViewHolder(View itemView) {
            super(itemView);
            imageMineHeader = ((ImageView) itemView.findViewById(R.id.image_mine_header));
            textNameMineHeader = ((TextView) itemView.findViewById(R.id.text_name_mine_header));
            textDescMineContent = ((TextView) itemView.findViewById(R.id.text_desc_mine_content));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnclickListener.onClick(getAdapterPosition(), loginDatas.get(getAdapterPosition()));
                }
            });
        }
    }

    ItemOnclickListener itemOnclickListener;

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    public interface ItemOnclickListener {
        void onClick(int position, LoginData loginData);
    }
}
