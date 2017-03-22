package com.iptv.signin.ui.conversation;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.iptv.signin.R;
import com.iptv.signin.ui.activity.BaseActivity;

public class ConversationActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        mToolbar = (Toolbar) findViewById(R.id.conversation_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("聊天界面");

    }
}
