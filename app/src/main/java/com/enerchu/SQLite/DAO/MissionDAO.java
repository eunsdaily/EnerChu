package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enerchu.MissionMaker;
import com.enerchu.SQLite.DBHelper;
import com.enerchu.SQLite.SQL.Insert;
import com.enerchu.SQLite.VO.MissionVO;

import java.util.ArrayList;
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
        String sql = "select count(date) as count from mission group by date;";
        Cursor c = db.rawQuery(sql, null);

        int returnVal = 0;
        if(c.getCount() != 0 && c != null) {
            c.moveToNext();
            returnVal = c.getInt(c.getColumnIndex("count"));
            Log.i("mission count", String.valueOf(returnVal));
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

        if(c != null && c.getCount() != 0){
            c.moveToNext();
            boolean success = (c.getInt(c.getColumnIndex("success")) == 0);
            if(success){  // 오늘의 미션이 존재하고 아직 미완
                mission = MissionMaker.makeMissionString(c.getInt(c.getColumnIndex("missionType")), c.getString(c.getColumnIndex("param")));
            }
            else{ // 오늘의 미션이 존재하고
                mission = "오늘의 미션을 이미 완료하였습니다 ('~')/";
            }
        }
        else{ // 오늘의 미션이 존해하지 않음
            // 새로운 미션을 생성하여 DB에 저장하고 해당 미션을 리턴
            ArrayList<String> parms = MissionMaker.createMission();
            db.execSQL(new Insert.insertMission().getSQL(parms));

            mission = MissionMaker.makeMissionString(Integer.getInteger(parms.get(0)), parms.get(1));
        }
        return mission;
    }

    public String getLastParam(int type) {
        String lastParams = "";
        return lastParams;
    }

    public static boolean getLastSuccess(int type) {
        return true;
    }

    public ArrayList<MissionVO> getPastMissionTTT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    public ArrayList<MissionVO> getPastMissionTTF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }


    public ArrayList<MissionVO> getPastMissionTFT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    public ArrayList<MissionVO> getPastMissionATFF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    public ArrayList<MissionVO> getPastMissionFTT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    public ArrayList<MissionVO> getPastMissionFTF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    public ArrayList<MissionVO> getPastMissionFFT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    public ArrayList<MissionVO> getPastMissionFFF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }
}
