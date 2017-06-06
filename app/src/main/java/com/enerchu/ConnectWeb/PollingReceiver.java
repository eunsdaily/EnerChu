package com.enerchu.ConnectWeb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by samsung on 2017-06-06.
 */

public class PollingReceiver extends BroadcastReceiver {
    public PollingReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent){
        ConnectWeb task = new ConnectWeb();
        task.connectToWeb("tab", "eun", "tab1", "1 1 1 1");
    }
}
