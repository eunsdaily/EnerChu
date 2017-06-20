package com.enerchu.ConnectWeb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.enerchu.MissoinManager.MissionChecker;
import com.enerchu.OpenDoorDialog;
import com.enerchu.SQLite.DAO.BillDAO;
import com.enerchu.SQLite.Singleton.Singleton;

/**
 * Created by samsung on 2017-06-06.
 */

public class PollingReceiver extends BroadcastReceiver {
    public PollingReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent){
        ConnectWebforSync task = new ConnectWebforSync();

        task.getTabInfoFromWeb("tab", "userid", "mul2");

        task.getTabInfoFromWeb("door", "userid");
        Intent i = new Intent(context.getApplicationContext(), OpenDoorDialog.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(i);

        MissionChecker.checkTodayMissionSuccess();
        BillDAO billDAO = Singleton.getBillDAO();
        billDAO.checkAndMakeBill();
    }
}
