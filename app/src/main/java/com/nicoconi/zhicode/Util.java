package com.nicoconi.zhicode;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Util {

    public static void showNotification(Context context) {
        if (isTodayNotify(context)) {
            Log.d("zhicode", "今天已经弹过通知");
            return;
        }
        Log.d("zhicode", "显示通知");
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        PendingIntent onClick = PendingIntent.getBroadcast(context, 0,
                new Intent(context, NotificationClickReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent onDonate = PendingIntent.getBroadcast(context, 1,
                new Intent(context, NotificationDonateReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent onDisable = PendingIntent.getBroadcast(context, 2,
                new Intent(context, NotificationDisableReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action donateAction = new Notification.Action.Builder(
                R.drawable.ic_main, "土豪打钱通道",
                onDonate).build();
        Notification.Action disableAction = new Notification.Action.Builder(
                R.drawable.ic_main, "不再显示",
                onDisable).build();
        Notification notification = new Notification.Builder(context)
                .setContentTitle("求求你们点一下吧")
                .setContentText("领一下我的支付宝红包，就当免费捐赠了")
                .setSmallIcon(R.drawable.ic_main)
                .setContentIntent(onClick)
                .addAction(donateAction)
                .addAction(disableAction)
                .build();
        nm.notify(0, notification);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.pref_last_notify), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        editor.apply();
    }

    public static void startAlipayWithZhicode(Context context, String code) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        cm.setText(code);
        Intent alipay = context.getPackageManager()
                .getLaunchIntentForPackage(context.getResources().getString(R.string.alipay_package));
        if (alipay != null) {
            alipay.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alipay);
        }
        else {
            Toast.makeText(context, "没有安装支付宝", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startAlipayWithZhicode(Context context, int code) {
        startAlipayWithZhicode(context, context.getString(code));
    }

    public static void cancelNotification(Context context, int id) {
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.cancel(id);
    }

    public static void disableNotification(Context context) {
        Log.d("zhicode", "禁用通知");
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, BootReceiver.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, AlarmReceiver.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    public static void enableNotification(Context context) {
        Log.d("zhicode", "启用通知");
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, BootReceiver.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, AlarmReceiver.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        setAlarm(context);
    }

    public static boolean isDisableNotification(Context context) {
        ComponentName componentName = new ComponentName(context, BootReceiver.class);
        int state = context.getPackageManager().getComponentEnabledSetting(componentName);
        return state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    public static boolean isTodayNotify(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int lastday = prefs.getInt(context.getString(R.string.pref_last_notify), 0);
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == lastday;

    }

    public static void setAlarm(Context context) {
        Log.d("zhicode", "设置定时器");
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }
}
