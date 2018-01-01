package com.nicoconi.zhicode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Util.startAlipayWithZhicode(context, R.string.zhicode_hongbao);
        Util.cancelNotification(context, 0);
    }
}
