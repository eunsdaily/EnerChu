package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enerchu.MissoinManager.MissionChecker;
import com.enerchu.MissoinManager.MissionMaker;
import com.enerchu.SQLite.DBHelper;
import com.enerchu.SQLite.SQL.Insert;
import com.enerchu.SQLite.SQL.Update;
import com.enerchu.SQLite.VO.MissionVO;

import java.sql.Date;
import java.util.ArrayList;

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
            // static 변수 초기화
            ArrayList<String> parms = MissionMaker.createMission();
            db.execSQL(new Insert.insertMission().getSQL(parms));

            mission = MissionMaker.makeMissionString(Integer.valueOf(parms.get(0)), parms.get(1));

            MissionChecker.setTodayAcceptOpenDoorEvent(0);
            MissionChecker.setTodayMulOnOffNumber(0);
            MissionChecker.setTodaySuccess(false);
            MissionChecker.setTodayType(Integer.valueOf(getTodayMissionType()));
        }
        return mission;
    }

    public String getTodayMissionParm(){
        String sql = "select param from mission where date = date('now')";
        Cursor c = db.rawQuery(sql,null);

        String retrunVal = "";
        if(c!=null && c.getCount() !=0){
            c.moveToNext();
            retrunVal = c.getString(c.getColumnIndex("param"));
        }

        return retrunVal;
    }

    public String getTodayMissionType(){
        String sql = "select missionType from mission where date = date('now')";
        Cursor c = db.rawQuery(sql,null);

        String retrunVal = "";
        if(c!=null && c.getCount() !=0){
            c.moveToNext();
            retrunVal = c.getString(c.getColumnIndex("missionType"));
        }

        return retrunVal;
    }

    public String getLastParam(int type) {
        String sql = "select a.param " +
                     "from mission a " +
                     "where a.missionType = "+type+" and a.success = 'true' and a.date >= (select mission.date from mission where mission.missionType = "+type+" and success = 'true');";

        Cursor c = db.rawQuery(sql, null);
        String lastParams;
        if(c.getCount() != 0 && c != null) {
            c.moveToNext();
            lastParams = c.getString(c.getColumnIndex("param"));
        }
        else{
            lastParams = "0";
        }
        return lastParams;
    }

    public boolean getLastSuccess(int type) {
        String sql = "select a.success " +
                "from mission a " +
                "where a.missionType = "+type+" and a.date > (select mission.date from mission where mission.missionType = "+type+");";
        Cursor c = db.rawQuery(sql, null);

        boolean lastSuccess;
        if(c.getCount() != 0 && c != null) {
            c.moveToNext();
            lastSuccess = Boolean.getBoolean(c.getString(c.getColumnIndex("success")));
        }
        else{
            lastSuccess = false;
        }
        return lastSuccess;
    }

    private Cursor getTotalMissionDesc(){
        String sql = "select * from mission order by date desc;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    private Cursor getTotalMissionAcs(){
        String sql = "select * from mission order by date;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    // 날짜역순 + 성공 + 실패
    public ArrayList<MissionVO> getPastMissionTTT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();
        Cursor c = getTotalMissionDesc();

        if(c.getCount() != 0) {
            while(c.moveToNext()){
                MissionVO tmp = new MissionVO();
                tmp.setDate(Date.valueOf(c.getString(c.getColumnIndex("date"))));
                tmp.setMissionType(c.getInt(c.getColumnIndex("missionType")));
                tmp.setParam(c.getString(c.getColumnIndex("param")));
                tmp.setSuccess(Boolean.getBoolean(c.getString(c.getColumnIndex("success"))));
                missionVOArray.add(tmp);
            }
        }

        return missionVOArray;
    }

    // 날짜역순 + 성공
    public ArrayList<MissionVO> getPastMissionTTF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();
        String sql = "select * from mission where success = 'true' order by date desc;";
        Cursor c = getTotalMissionDesc();

        if(c.getCount() != 0) {
            while(c.moveToNext()){
                if(Boolean.getBoolean(c.getString(c.getColumnIndex("success")))) {
                    MissionVO tmp = new MissionVO();
                    tmp.setDate(Date.valueOf(c.getString(c.getColumnIndex("date"))));
                    tmp.setMissionType(c.getInt(c.getColumnIndex("missionType")));
                    tmp.setParam(c.getString(c.getColumnIndex("param")));
                    tmp.setSuccess(Boolean.getBoolean(c.getString(c.getColumnIndex("success"))));
                    missionVOArray.add(tmp);
                }
            }
        }

        return missionVOArray;
    }

    // 날짜역순 + 실패
    public ArrayList<MissionVO> getPastMissionTFT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();
        String sql = "select * from mission order by date desc;";
        Cursor c = db.rawQuery(sql, null);

        if(c.getCount() != 0 && c != null){
            while(c.moveToNext()){
                if(!Boolean.getBoolean(c.getString(c.getColumnIndex("success")))) {
                    MissionVO tmp = new MissionVO();
                    tmp.setDate(Date.valueOf(c.getString(c.getColumnIndex("date"))));
                    tmp.setMissionType(c.getInt(c.getColumnIndex("missionType")));
                    tmp.setParam(c.getString(c.getColumnIndex("param")));
                    tmp.setSuccess(Boolean.getBoolean(c.getString(c.getColumnIndex("success"))));
                    Log.i("makeMissionVOList", tmp.toString());
                    missionVOArray.add(tmp);
                }
            }
        }

        return missionVOArray;
    }

    // 아무것도 출력X
    public ArrayList<MissionVO> getPastMissionATFF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    // 날짜순서 + 성공 + 실패
    public ArrayList<MissionVO> getPastMissionFTT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();
        Cursor c = getTotalMissionAcs();

        if(c.getCount() != 0 && c != null) {
            while(c.moveToNext()){
                MissionVO tmp = new MissionVO();
                tmp.setDate(Date.valueOf(c.getString(c.getColumnIndex("date"))));
                tmp.setMissionType(c.getInt(c.getColumnIndex("missionType")));
                tmp.setParam(c.getString(c.getColumnIndex("param")));
                tmp.setSuccess(Boolean.getBoolean(c.getString(c.getColumnIndex("success"))));
                missionVOArray.add(tmp);
            }
        }

        return missionVOArray;
    }

    // 날짜순서 + 성공
    public ArrayList<MissionVO> getPastMissionFTF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();
        Cursor c = getTotalMissionAcs();

        if(c.getCount() != 0) {
            while(c.moveToNext()){
                if(Boolean.getBoolean(c.getString(c.getColumnIndex("success")))) {
                    MissionVO tmp = new MissionVO();
                    tmp.setDate(Date.valueOf(c.getString(c.getColumnIndex("date"))));
                    tmp.setMissionType(c.getInt(c.getColumnIndex("missionType")));
                    tmp.setParam(c.getString(c.getColumnIndex("param")));
                    tmp.setSuccess(Boolean.getBoolean(c.getString(c.getColumnIndex("success"))));
                    missionVOArray.add(tmp);
                }
            }
        }

        return missionVOArray;
    }

    // 날짜순서 + 실패
    public ArrayList<MissionVO> getPastMissionFFT() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();
        Cursor c = getTotalMissionAcs();

        if(c.getCount() != 0 && c != null){
            while(c.moveToNext()){
                if(!Boolean.getBoolean(c.getString(c.getColumnIndex("success")))) {
                    MissionVO tmp = new MissionVO();
                    tmp.setDate(Date.valueOf(c.getString(c.getColumnIndex("date"))));
                    tmp.setMissionType(c.getInt(c.getColumnIndex("missionType")));
                    tmp.setParam(c.getString(c.getColumnIndex("param")));
                    tmp.setSuccess(Boolean.getBoolean(c.getString(c.getColumnIndex("success"))));
                    Log.i("makeMissionVOList", tmp.toString());
                    missionVOArray.add(tmp);
                }
            }
        }

        return missionVOArray;
    }

    // 아무것도 출력X
    public ArrayList<MissionVO> getPastMissionFFF() {
        ArrayList<MissionVO> missionVOArray = new ArrayList<>();

        return missionVOArray;
    }

    public void close(){
        db.close();
        dbHelper.close();
    }

    public void setTodaySuccessToTrue() {
        db.execSQL(new Update.updateMissionSuccess().getSQL(new ArrayList<String>()));

    }
}
