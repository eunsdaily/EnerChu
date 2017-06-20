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
        ConnectWebforSync task = new ConnectWebforSync();

        task.getTabInfoFromWeb("tab", "userid", "mul2");

        String[] str = task.getTabInfoFromWeb("door", "userid");
        if (str[3].equals("open")){
            //insert open action
        }
    }
}

