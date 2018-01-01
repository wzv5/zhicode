package com.nicoconi.zhicode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class NotificationDisableReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Util.disableNotification(context);
        Util.cancelNotification(context, 0);
    }
}
