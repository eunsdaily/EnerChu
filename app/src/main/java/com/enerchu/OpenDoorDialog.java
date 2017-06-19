package com.enerchu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by admin on 2017-06-19.
 */

public class OpenDoorDialog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        AlertDialog.Builder alter = new AlertDialog.Builder(this);
        TTSMaker ttsMaker = new TTSMaker(getApplicationContext(), "안녕하세요");

        alter.setTitle("EnerChu");
        alter.setMessage("멀티탭 좀 끄고 가실?");
        alter.setIcon(R.mipmap.ic_launcher_round);


        alter.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alter.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
