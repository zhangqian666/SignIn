package com.iptv.signin.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iptv.signin.R;
import com.iptv.signin.bean.CommonData;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.iptv.signin.SignInApplication.mContext;

public class PersonalDescActivity extends BaseActivity {

    @BindView(R.id.image_action_head)
    ImageView mImageActionHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_desc);
        ButterKnife.bind(this);
        Glide.with(mContext).load(CommonData.mImagePersonalDescHead).into(mImageActionHead);
    }
}
