package com.enerchu;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.enerchu.ConnectWeb.PollingReceiver;
import com.enerchu.SQLite.DBHelper;
import com.enerchu.fragment.BillFragment;
import com.enerchu.fragment.FaceFragment;
import com.enerchu.fragment.MissionFragment;
import com.enerchu.fragment.PlugFragment;
import com.enerchu.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmSetting(); // setting polling

        DBHelper dbHelper = new DBHelper(MainActivity.this, "EnerChu.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.initialization(db);
        db = dbHelper.getReadableDatabase();
        Cursor c = db.query("bill", null, null, null, null, null, null);
        while(c.moveToNext()){
            Log.i(c.getString(c.getColumnIndex("multitapCode"))+"-"+String.valueOf(c.getInt(c.getColumnIndex("plugNumber")))+"-"+String.valueOf(c.getString(c.getColumnIndex("date"))), String.valueOf(c.getFloat(c.getColumnIndex("lastUpdatedUsed"))));
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_face:
                        fragment = new FaceFragment();
                        break;
                    case R.id.navigation_pulg:
                        fragment = new PlugFragment();
                        break;
                    case R.id.navigation_bill:
                        fragment = new BillFragment();
                        break;
                    case R.id.navigation_mission:
                        fragment = new MissionFragment();
                        break;
                    case R.id.navigation_setting:
                        fragment = new SettingFragment();
                        break;
                    default:
                        break;
                }
                if (fragment != null) {
                    replaceFragment(fragment);
                }
                return true;
            }
        });

            //replace SongsFragment
        replaceFragment(new FaceFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    private void alarmSetting(){
        AlarmManager processTimer = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, PollingReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,  intent, PendingIntent.FLAG_UPDATE_CURRENT);
        processTimer.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pendingIntent);
    }
}
