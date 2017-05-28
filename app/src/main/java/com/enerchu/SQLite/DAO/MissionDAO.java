package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.enerchu.SQLite.DBHelper;

import java.util.Random;

/**
 * Created by admin on 2017-05-15.
 */

public class MissionDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public MissionDAO(Context context) {
        dbHelper = new DBHelper(context, "EnerChu.db", null, 1);
        db = dbHelper.getReadableDatabase();
    }
    public int getTotalMission(){
        String sql = "select count(date) from mission group by date;";
        Cursor c = db.rawQuery(sql, null);

        int returnVal = 0;
        if(c.getCount() != 0) {
            c.moveToNext();
            returnVal = c.getInt(c.getColumnIndex("goal"));
        }
        return returnVal;
    }

    public void close(){
        db.close();
        dbHelper.close();
    }

    public String getTodayMisson() {
        String mission = "";

        String sql = "select * from mission where date=(select date('now', 'localtime'));";
        Cursor c = db.rawQuery(sql, null);

        if(c.getCount() != 0){
            if(c.getInt(c.getColumnIndex("success")) == 0){
                mission = makeMissionSt(c.getInt(c.getColumnIndex("missionType")), c.getString(c.getColumnIndex("param")));
            }
            else if(c.getInt(c.getColumnIndex("success")) == 1){
                mission = "오늘의 미션을 이미 완료하였습니다 ('~')/";
            }
        }
        else{

        }
        return mission;
    }

    private String makeMissionSt(int missionType, String param) {
        String mission = "";

        if(missionType == 0){

        }else if(missionType == 1){

        }else if(missionType == 2){

        }

        return mission;
    }
}
