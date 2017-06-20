package com.enerchu.SQLite.Singleton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enerchu.SQLite.DAO.BillDAO;
import com.enerchu.SQLite.DAO.BillStateDAO;
import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.SQLite.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017-06-10.
 */

public class Singleton {
    private static BillDAO billDAO;
    private static BillStateDAO biiBillStateDAO;
    private static ClientDAO clientDAO;
    private static MissionDAO missionDAO;
    private static MultiTapDAO multiTapDAO;
    private static PlugDAO plugDAO;
    private static DBHelper dbHelper;

    private Singleton(){}

    public static void initializatin(Context context){
        if(billDAO == null){ billDAO = new BillDAO(context); }
        if(biiBillStateDAO == null){ biiBillStateDAO = new BillStateDAO(context); }
        if(clientDAO == null){ clientDAO = new ClientDAO(context); }
        if(missionDAO == null){ missionDAO = new MissionDAO(context); }
        if(multiTapDAO == null){ multiTapDAO = new MultiTapDAO(context); }
        if(plugDAO == null){ plugDAO = new PlugDAO(context); }
        if(dbHelper == null){ dbHelper = new DBHelper(context, "EnerChu.db", null, 1); }

        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
        String str = dayTime.format(new Date(time));
        billDAO.today = str;
    }

    public static BillDAO getBillDAO(){
        if(billDAO == null){
            Log.i("singleton", "BillDAO is empty");
        }
        return billDAO;
    }

    public static BillStateDAO getBillStateDAO(){
        if(biiBillStateDAO == null){
            Log.i("singleton", "BillStateDAO is empty");
        }
        return biiBillStateDAO;
    }

    public static ClientDAO getClientDAO(){
        if(clientDAO == null){
            Log.i("singleton", "ClientDAO is empty");
        }
        return clientDAO;
    }

    public static MissionDAO getMissionDAO(){
        if(missionDAO == null){
            Log.i("singleton", "MissionDAO is empty");
        }
        return missionDAO;
    }

    public static MultiTapDAO getMultiTapDAO(){
        if(multiTapDAO == null){
            Log.i("singleton", "MultiTapDAO is empty");
        }
        return multiTapDAO;
    }

    public static PlugDAO getPlugDAO(){
        if(plugDAO == null){
            Log.i("singleton", "PlugDAO is empty");
        }
        return plugDAO;
    }

    public static DBHelper getDBHelper(){

        if(dbHelper == null){
            Log.i("singleton", "DBHelper is empty");
        }
        return dbHelper;
    }
}
