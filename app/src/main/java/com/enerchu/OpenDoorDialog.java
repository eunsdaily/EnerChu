package com.enerchu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.enerchu.Fragment.PlugFragment;
import com.enerchu.MissoinManager.MissionChecker;
import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.SQLite.Singleton.Singleton;

/**
 * Created by admin on 2017-06-19.
 */

public class OpenDoorDialog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        AlertDialog.Builder alter = new AlertDialog.Builder(this);
        TTSMaker ttsMaker = new TTSMaker(getApplicationContext(), "하.하.하.하.하.하");

        alter.setTitle("EnerChu");
        alter.setMessage("하 하 하 하 하 하");
        alter.setIcon(R.mipmap.ic_launcher_round);


        alter.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // call plug page
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("open door event", 1);
                getApplicationContext().startActivity(i);

                // MissionChecker
                MissionChecker.addTodayAcceptOpenDoorEvent();

                // Grade up
                ClientDAO clientDAO = Singleton.getClientDAO();
                clientDAO.upGrade(1);

            }
        });

        alter.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dowm up
                ClientDAO clientDAO = Singleton.getClientDAO();
                clientDAO.upGrade(-1);

                dialog.dismiss();

            }
        });


        alter.show();
    }
}


/*
*
* Intent i = new Intent(context.getApplicationContext(),MyAlertDialog.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
*
*
* */
