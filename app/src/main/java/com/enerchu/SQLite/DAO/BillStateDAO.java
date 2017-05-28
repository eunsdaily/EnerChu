package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.enerchu.SQLite.DBHelper;

import java.util.Random;

/**
 * Created by admin on 2017-05-28.
 */

public class BillStateDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public BillStateDAO(Context context){
        dbHelper = new DBHelper(context, "EnerChu.db", null, 1);
        db = dbHelper.getReadableDatabase();
    }

    public float getAmountUsedSum_severalDaysBefore(int days){
        String sql = "select amountUsedSum " +
                "from billState " +
                "where date = (select date('now', 'localtime', '-"+days+" day'));";
        Cursor c = db.rawQuery(sql, null);

        float returnVal = 0;
        if(c.getCount() != 0) {
            c.moveToNext();
            returnVal = c.getFloat(c.getColumnIndex("amountUsedSum"));
        }
        return returnVal;
    }

    public float getAmountUsedSum_today() {
        String sql = "select amountUsedSum " +
                "from billState " +
                "where date = (select date('now', 'localtime'));";
        Cursor c = db.rawQuery(sql, null);

        float returnVal = 0;
        if(c.getCount() != 0) {
            c.moveToNext();
            returnVal = c.getFloat(c.getColumnIndex("amountUsedSum"));
        }
        return returnVal;
    }

    public float getAmountUsedSum_tomorrow() {
        Random random = new Random();
        return random.nextInt(10)+10;
    }

    public float getAmountUsedSum_lastMonth_Kwh(){
        String sql = "select sum(amountUsedSum) as sum" +
                " from billState" +
                " where date >= (select date('now','start of month','-1 month','localtime')) " +
                "   and date <= (select date('now','start of month','-1 day','localtime'));";
        Cursor c = db.rawQuery(sql, null);

        float returnVal = 0;
        if(c.getCount() != 0){
            c.moveToNext();
            returnVal = c.getFloat(c.getColumnIndex("sum"));
        }
        return returnVal;
    }

    public float getAmountUsedSum_thisMonth_Kwh() {
        String sql = "select sum(amountUsedSum) as sum " +
                "from billState " +
                "where date >= (select date('now','start of month','localtime')) " +
                "  and date <= (select date('now','start of month','+1 month','-1 day','localtime'));";
        Cursor c = db.rawQuery(sql, null);

        float returnVal = 0;
        if(c.getCount() != 0){
            c.moveToNext();
            returnVal = c.getFloat(c.getColumnIndex("sum"));
        }
        return returnVal;
    }

    public int getAmountUsedSum_lastMonth_won() {
        return getAmountUsedWon(getAmountUsedSum_lastMonth_Kwh());
    }

    public int getAmountUsedSum_thisMonth_won() {
        return getAmountUsedWon(getAmountUsedSum_thisMonth_Kwh());
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

    public void close(){
        db.close();
        dbHelper.close();
    }

}
