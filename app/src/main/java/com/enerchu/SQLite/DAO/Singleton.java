package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.util.Log;

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

    private Singleton(){}

    public static void initializatin(Context context){
        if(billDAO == null){ billDAO = new BillDAO(context); }
        if(biiBillStateDAO == null){ biiBillStateDAO = new BillStateDAO(context); }
        if(clientDAO == null){ clientDAO = new ClientDAO(context); }
        if(missionDAO == null){ missionDAO = new MissionDAO(context); }
        if(multiTapDAO == null){ multiTapDAO = new MultiTapDAO(context); }
        if(plugDAO == null){ plugDAO = new PlugDAO(context); }
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
}
