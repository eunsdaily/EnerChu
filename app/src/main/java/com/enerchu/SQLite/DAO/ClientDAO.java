package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.enerchu.SQLite.DBHelper;
import com.enerchu.SQLite.SQL.Update;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-05.
 */

public class ClientDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ClientDAO(Context context) {
        dbHelper = new DBHelper(context, "EnerChu.db", null, 1);
        db = dbHelper.getReadableDatabase();
    }

    public float getGoalBillKWh(){
        String sql = "select goal from client;";
        Cursor c = db.rawQuery(sql, null);

        float returnVal = 0;
        if(c.getCount() != 0) {
            c.moveToNext();
            returnVal = c.getFloat(c.getColumnIndex("goal"));
        }
        return returnVal;
    }

    public int getGoalBillWon(){
        return getAmountUsedWon(getGoalBillKWh());
    }

    private int getAmountUsedWon(float kwh) {
        int won = 0;

        if(kwh == 0){
            // do nothing
        }
        else if ( 0 < kwh && kwh <= 200){
            won += 910;
            won += (int) Math.floor(kwh*93.3);
            if(won < 5000) won = 1000;
            else won -= 4000;
        } else if (200 < kwh && kwh <= 400) {
            won += 1600;
            won += (int) Math.floor(200*93.3);
            won += (int) Math.floor((kwh-200)*187.9);
        } else{
            won += 7300;
            won += (int) Math.floor(200*93.3);
            won += (int) Math.floor(200*187.9);
            won += (int) Math.floor((kwh-400)*280.6);
        }
        won += (int) Math.floor(((won*0.1)+(won*0.037)));
        won = won/10 * 10;

        return won;
    }

    public void setGoal(float goal) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(0, "id");
        params.add(1, String.valueOf(goal));
        db.execSQL(new Update.updateGoal().getSQL(params));
    }
    public void close(){
        db.close();
        dbHelper.close();
    }

    public int getGrade() {
        String sql = "select grade from client where id = 'id';";
        Cursor c = db.rawQuery(sql, null);

        int returnVal = 50;
        if(c.getCount() != 0) {
            c.moveToNext();
            returnVal = c.getInt(c.getColumnIndex("grade"));
        }
        return returnVal;
    }

    public void upGrade(int i){
        int nowGrade = getGrade();
        String sql = "update client set grade = " +(getGrade() + i)+ ";";
        db.execSQL(sql);
    }
}
