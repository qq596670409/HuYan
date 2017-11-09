package me.peng.huyan.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import me.peng.huyan.Bean.GlobalData;
import me.peng.huyan.R;

/**
 * Created by Administrator on 2017/11/9.
 */

public class LongRunningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //启用前台服务，主要是startForeground()
        Notification notification = new Notification.Builder(this)
                .setContentTitle(GlobalData.inform_title)
                .setContentText(GlobalData.inform_content)
                .setSmallIcon(R.drawable.mice)
                .setLargeIcon(GlobalData.inform_bitmap)
                .build();
        //设置振动
        notification.vibrate = GlobalData.all_vibrate_type[GlobalData.vibrate_type_number];
        startForeground(1, notification);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //读者可以修改此处的Minutes从而改变提醒间隔时间
        //此处是设置每隔55分钟启动一次
        //这是55分钟的毫秒数
        int minutes = GlobalData.inform_time * 60 * 1000;
        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + minutes;
        //此处设置开启AlarmReceiver这个BroadcastReceiver
        Intent ARIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, ARIntent, 0);
        //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //在Service结束后关闭AlarmManager
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        manager.cancel(pendingIntent);
    }
}
