package com.iptv.signin.ui.rongim;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.TextView;

import com.iptv.signin.R;
import com.iptv.signin.ui.activity.BaseActivity;

import java.util.Collection;
import java.util.Iterator;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class ConversationActivity extends BaseActivity {

    private String mTitle;
    private Toolbar mToolBar;
    private final int SET_TEXT_TYPING_TITLE = 1;
    private final int SET_VOICE_TYPING_TITLE = 2;
    private final int SET_TARGETID_TITLE = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET_TEXT_TYPING_TITLE:
                    mToolBar.setTitle("对方正在输入...");
                    break;
                case SET_VOICE_TYPING_TITLE:
                    mToolBar.setTitle("对方正在讲话...");
                    break;
                case SET_TARGETID_TITLE:
                    mToolBar.setTitle(mTitle);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initActionBar();

        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
                //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
//                if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
                //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
                int count = typingStatusSet.size();
                if (count > 0) {
                    Iterator iterator = typingStatusSet.iterator();
                    TypingStatus status = (TypingStatus) iterator.next();
                    String objectName = status.getTypingContentType();

                    MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                    MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                    //匹配对方正在输入的是文本消息还是语音消息
                    if (objectName.equals(textTag.value())) {
                        //显示“对方正在输入”
                        mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
                    } else if (objectName.equals(voiceTag.value())) {
                        //显示"对方正在讲话"
                        mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
                    }
                } else {
                    //当前会话没有用户正在输入，标题栏仍显示原来标题
                    mHandler.sendEmptyMessage(SET_TARGETID_TITLE);
                }
//                }
            }
        });

    }

    /**
     * 初始化
     */
    private void initActionBar() {
        mTitle = getIntent().getData().getQueryParameter("title");
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) mToolBar.findViewById(R.id.toolbar_title)).setText(mTitle);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        if (!fragment.onBackPressed()) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
