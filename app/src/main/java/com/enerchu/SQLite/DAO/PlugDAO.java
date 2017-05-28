package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enerchu.SQLite.DBHelper;
import com.enerchu.SQLite.SQL.Update;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 2017-05-01.
 */

public class PlugDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public PlugDAO(Context context) {
        dbHelper = new DBHelper(context, "EnerChu.db", null, 1);
        db = dbHelper.getReadableDatabase();
    }

    public String getNickName(String multitapCode, int plugNumber){
        String sql = "select nickname from plug where multitapCode = '"+multitapCode+"' and plugNumber = "+plugNumber+";";
        Cursor c = db.rawQuery(sql, null);

        String returnVal = "-";
        if (c != null && c.getCount() != 0) {
            c.moveToNext();
            returnVal = c.getString(c.getColumnIndex("nickname"));
        }

        return returnVal;
    }
    public boolean getState(String multitapCode, int plugNumber){
        String sql = "select onoff from plug where multitapCode = '"+multitapCode+"' and plugNumber = "+plugNumber+";";
        Cursor c = db.rawQuery(sql, null);

        boolean returnVal = false;
        if (c != null && c.getCount() != 0) {
            c.moveToNext();
            if(c.getInt(c.getColumnIndex("onoff")) ==1){
                returnVal = true;
            }
        }
        return returnVal;
    }

    public void updateNickName(String multitapCode, int plugNumber, String newNickname) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(0, multitapCode);
        params.add(1, String.valueOf(plugNumber));
        params.add(2, newNickname);

        db.execSQL(new Update.updatePlugNickname().getSQL(params));
    }

    public void updateState(String multitapCode, int plugNumber, boolean onoff) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(0, multitapCode);
        params.add(1, String.valueOf(plugNumber));

        if (onoff){params.add(2, String.valueOf(1));}
        else      {params.add(2, String.valueOf(0));}

        db.execSQL(new Update.updatePlugState().getSQL(params));
    }

    public void close(){
        db.close();
        dbHelper.close();
    }
}
