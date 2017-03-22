package com.iptv.signin.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.iptv.signin.R;

import static com.iptv.signin.SignInApplication.mContext;

public class SignInService extends Service {

    private Notification notification;

    public SignInService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent();
        intent.setAction("com.iptv.ADDTIME");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.remote_view);
        Notification build = new NotificationCompat.Builder(this)
//                .setContentTitle("Title")
//                .setContentText("Content")
                .setContent(remoteViews)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.micon)
                .setContentIntent(pendingIntent).build();
        startForeground(1, build);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
