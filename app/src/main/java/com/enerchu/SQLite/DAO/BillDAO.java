package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.enerchu.MainActivity;
import com.enerchu.SQLite.DBHelper;

import java.util.Random;

/**
 * Created by admin on 2017-05-01.
 */

public class BillDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public BillDAO(Context context){
        dbHelper = new DBHelper(context, "EnerChu.db", null, 1);
        db = dbHelper.getReadableDatabase();
    }

    private float getReturnVal(Cursor c) {
        float returnVal = 0;
        if(c.getCount() != 0){
            c.moveToNext();
            returnVal = c.getFloat(c.getColumnIndex("sum"));
        }
        return returnVal;
    }

    public float getAmountUsed_lastMonth_kWh(String multitapCode){
        String sql = "select sum(amountUsed) as sum " +
                "from bill " +
                "where date >= (select date('now','start of month','-1 month','localtime')) " +
                "  and date <= (select date('now','start of month','-1 day','localtime'))" +
                "  and multitapCode='"+multitapCode+"';";
        Cursor c = db.rawQuery(sql, null);

        return getReturnVal(c);
    }

    public float getAmountUsed_lastMonth_kWh(String multitapCode, int plugNumber){
        String sql = "select sum(amountUsed) as sum " +
                "from bill " +
                "where date >= (select date('now','start of month','-1 month','localtime')) " +
                "  and date <= (select date('now','start of month','-1 day','localtime'))" +
                "  and multitapCode='"+multitapCode+"'" +
                "  and plugNumber="+plugNumber+";";
        Cursor c = db.rawQuery(sql, null);

        return getReturnVal(c);
    }

    public float getAmountUsed_thisMonth_kWh(String multitapCode){
        String sql = "select sum(amountUsedSum) as sum " +
                "from billState " +
                "where date >= (select date('now','start of month','localtime')) " +
                "  and date <= (select date('now','start of month','+1 month','-1 day','localtime'));"+
                "  and multitapCode='"+multitapCode+"';";

        Cursor c = db.rawQuery(sql, null);

        return getReturnVal(c);
    }

    public float getAmountUsed_thisMonth_kWh(String multitapCode, int plugNumber){
        String sql = "select sum(amountUsed) as sum " +
                "from bill " +
                "where date >= (select date('now','start of month','localtime')) " +
                "  and date <= (select date('now','start of month','+1 month','-1 day','localtime'));"+
                "  and multitapCode='"+multitapCode+"'" +
                "  and plugNumber="+plugNumber+";";
        Cursor c = db.rawQuery(sql, null);

        return getReturnVal(c);
    }

    public float getAmountUsed_yesterday_kWh(String multitapCode, int plugNumber){
        String sql = "select sum(amountUsed) as sum " +
                "from bill " +
                "where date = (select date('now','localtime', '-1 day')) " +
                "  and multitapCode='"+multitapCode+"'" +
                "  and plugNumber="+plugNumber+";";
        Cursor c = db.rawQuery(sql, null);

        return getReturnVal(c);
    }

    public float getAmountUsed_today_kWh(String multitapCode, int plugNumber){
        String sql = "select sum(amountUsed) as sum " +
                "from bill " +
                "where date = (select date('now','localtime')) " +
                "  and multitapCode='"+multitapCode+"'" +
                "  and plugNumber="+plugNumber+";";
        Cursor c = db.rawQuery(sql, null);

        return getReturnVal(c);
    }

    public void close(){
        db.close();
        dbHelper.close();
    }
}
