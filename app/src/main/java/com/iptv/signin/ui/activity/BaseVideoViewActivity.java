package com.iptv.signin.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.iptv.signin.R;
import com.iptv.signin.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseVideoViewActivity extends BaseActivity {

    @BindView(R.id.videoView)
    VideoView videoView;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_video_view);
        ButterKnife.bind(this);
        baseUrl = getIntent().getStringExtra("URL");
        LogUtil.e(baseUrl);
        videoView.setVideoURI(Uri.parse(baseUrl));
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
    }
}
