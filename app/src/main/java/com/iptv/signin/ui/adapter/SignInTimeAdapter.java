package com.iptv.signin.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iptv.signin.R;
import com.iptv.signin.bean.SignInTime;

import java.util.List;

/**
 * Created by ZhangQian on 2017/2/19 0019.
 */

public class SignInTimeAdapter extends RecyclerView.Adapter<SignInTimeAdapter.ViewHolder> {
    private static final String TAG = "SignInTimeAdapter";
    private Context mContext;
    private List<SignInTime> mSignInTimes;

    public SignInTimeAdapter(List<SignInTime> signInTimes) {
        super();
        mSignInTimes = signInTimes;
    }

    @Override
    public SignInTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_sign_in, parent, false);
        final ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int adapterPosition = viewHolder.getAdapterPosition();
                SignInTime signInTime = mSignInTimes.get(adapterPosition);
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(v, adapterPosition, signInTime);
                }
                return true;
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SignInTime signInTime = mSignInTimes.get(position);
        if (!TextUtils.isEmpty(signInTime.getSignData())) {
            holder.userTime.setText(signInTime.getSignData());
        }
        if (!TextUtils.isEmpty(signInTime.getSignAdress())) {
            holder.userLocal.setText(signInTime.getSignAdress());
        }
    }


    @Override
    public int getItemCount() {
        return mSignInTimes.size();
    }

    public void setSignInTimeAdapterOnLongClickListener(SignInTimeAdapterOnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    SignInTimeAdapterOnLongClickListener onLongClickListener;

    public interface SignInTimeAdapterOnLongClickListener {
        void onLongClick(View v, int position, SignInTime signInTime);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userLocal;
        TextView userTime;

        public ViewHolder(View itemView) {
            super(itemView);
            userTime = (TextView) itemView.findViewById(R.id.user_time);
            userLocal = (TextView) itemView.findViewById(R.id.user_local);

        }
    }
}
