package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enerchu.SQLite.DBHelper;
import com.enerchu.SQLite.SQL.Delete;
import com.enerchu.SQLite.SQL.Update;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-01.
 */

public class MultiTapDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public MultiTapDAO(Context context) {
        dbHelper = new DBHelper(context, "EnerChu.db", null, 1);
        db = dbHelper.getReadableDatabase();
    }

    public String getNickName(String multitapCode){
        String sql = "select nickname from multitap where multitapCode='"+multitapCode+"';";
        Cursor c = db.rawQuery(sql, null);

        String returnVal ="-";
        if (c != null && c.getCount() != 0) {
            c.moveToNext();
            returnVal = c.getString(c.getColumnIndex("nickname"));
        }
        return returnVal;
    }

    public int getTotalOfMultiTap(){
        String sql = "select count(multitapCode) as count from multitap;";
        Cursor c = db.rawQuery(sql, null);

        int returnVal = 0;
        if (c != null && c.getCount() != 0){
            c.moveToNext();
            Log.i("getCount", String.valueOf(c.getCount()));
            returnVal = c.getInt(c.getColumnIndex("count"));
        }
        return returnVal;
    }

    public ArrayList<String> getMultitapCodes(){
        db = dbHelper.getReadableDatabase();
        String sql = "select multitapCode from multitap;";
        Cursor c = db.rawQuery(sql, null);

        ArrayList<String> multitapCodes = new ArrayList<String>();
        if(c != null && c.getCount() != 0){
            while(c.moveToNext()){
                multitapCodes.add(c.getString(c.getColumnIndex("multitapCode")));
                Log.i("multitapCoeds", c.getString(c.getColumnIndex("multitapCode")));
            }
        }
        return multitapCodes;
    }

    public void updateNickName(String multitapCode, String newNickname) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(0, multitapCode); // multitapCode
        params.add(1, newNickname);
        Log.i("updateNickName", new Update.updateMultitapNickname().getSQL(params));
        db.execSQL(new Update.updateMultitapNickname().getSQL(params));
    }
    public void deleteMultiTap(String multitapCode) {
        ArrayList<String> params = new ArrayList<>();
        params.add(multitapCode);

        Log.i("deleteMultiTap", new Delete.deleteMultitap().getSQL(params));
        db.execSQL(new Delete.deleteMultitap().getSQL(params));
    }

    public void close(){
        db.close();
        dbHelper.close();
    }
}
