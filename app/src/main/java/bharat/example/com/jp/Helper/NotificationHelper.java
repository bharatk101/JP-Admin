package bharat.example.com.jp.Helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import bharat.example.com.jp.R;

/**
 * Created by bharat on 3/2/18.
 */

public class NotificationHelper extends ContextWrapper {

    private static final String JP_CHANNEL_ID = "bharat.example.com.jp";
    private static final String JP_CHANNEL_NAME = "JP";

    private NotificationManager manager;


    public NotificationHelper(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel jp = new NotificationChannel(JP_CHANNEL_ID,
                JP_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);

        jp.enableLights(false);
        jp.enableVibration(true);
        jp.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(jp);
    }

    public NotificationManager getManager() {
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public  Notification.Builder getJpChannelNotification(String title, String body, PendingIntent contextIntent,
                                                          Uri soundUri){
        return  new Notification.Builder(getApplicationContext(),JP_CHANNEL_ID)
                .setContentIntent(contextIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(soundUri)
                .setAutoCancel(false);

    }
}
