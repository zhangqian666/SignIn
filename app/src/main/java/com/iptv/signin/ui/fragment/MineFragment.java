package com.iptv.signin.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iptv.signin.R;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import butterknife.ButterKnife;

import static com.iptv.signin.bean.CommonData.EXCHANGE_NAME;
import static com.iptv.signin.bean.CommonData.HOSTNAME;
import static com.iptv.signin.bean.CommonData.PASSWORD;
import static com.iptv.signin.bean.CommonData.PORT;
import static com.iptv.signin.bean.CommonData.USERNAME;
import static com.iptv.signin.bean.CommonData.VIRTUALHOST;
import static com.iptv.signin.bean.CommonData.iptvQueue;
import static io.rong.imageloader.core.ImageLoader.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mRootView;
    private Button mBtn;
    private TextView mText;
    private ConnectionFactory factory;
    private Thread subscribeThread;
    private Connection connection;
    private Channel channel;
    private Toolbar mToolBar;


    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, mRootView);
        mToolBar = ((Toolbar) mRootView.findViewById(R.id.toolbar_mine_fragment));
        initActionBar();
        return mRootView;
    }
    private void initActionBar() {
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);
        ActionBar supportActionBar = activity.getSupportActionBar();
        supportActionBar.setTitle("个人页面");
    }

    private void connectRabbitmq() {
        setupConnectionFactory();
        subscribe(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, "----- handleMessage: " + msg);
            }
        });

    }

    private void setupConnectionFactory() {
        factory = new ConnectionFactory();
        factory.setHost(HOSTNAME);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUALHOST);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setRequestedChannelMax(1);
    }

    void subscribe(final Handler handler) {
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        connection = factory.newConnection();
                        channel = connection.createChannel();
                        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
                        AMQP.Queue.DeclareOk q = channel.queueDeclare(iptvQueue + "stbid-------", true, false, false, null);
                        String queueName = q.getQueue();
                        channel.queueBind(queueName, EXCHANGE_NAME, "chat");
                        QueueingConsumer consumer = new QueueingConsumer(channel);
                        channel.basicConsume(queueName, true, consumer);

                        while (true) {
                            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                            String routingKey = delivery.getEnvelope().getRoutingKey();
                            String message = new String(delivery.getBody());
                            Log.e("TAG", "RoutingKey： " + routingKey + "客户端收到服务器传送来的消息: " + message);
                            Message msg = handler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", message);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e1) {
                        Log.d("TAG", "Connection broken: " + e1.getClass().getName());
                        try {
                            Thread.sleep(5000); //sleep and then try again
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        });
        subscribeThread.start();
    }
}


