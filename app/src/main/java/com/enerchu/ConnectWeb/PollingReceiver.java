package com.enerchu.ConnectWeb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.enerchu.SQLite.DAO.PlugDAO;

/**
 * Created by samsung on 2017-06-06.
 */

public class PollingReceiver extends BroadcastReceiver {
    public PollingReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent){
        ConnectWeb task = new ConnectWeb();

        PlugDAO plugdao = new PlugDAO(context);

        task.connectToWeb("tab", "eun", "tab1", "1 1 1 1");
    }
}
