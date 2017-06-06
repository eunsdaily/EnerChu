package com.enerchu.ConnectWeb;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by samsung on 2017-06-06.
 */

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent){
        AlarmManager processTimer = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intents = new Intent(context, PollingReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,  intents, PendingIntent.FLAG_UPDATE_CURRENT);
        processTimer.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),5000, pendingIntent);
    }
}
