package com.enerchu.SQLite.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enerchu.MainActivity;
import com.enerchu.SQLite.DBHelper;
import com.enerchu.SQLite.SQL.Insert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by admin on 2017-05-01.
 */

public class BillDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public static String today;

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
        String sql = "select sum(amountUsed) as sum " +
                "from bill " +
                "where date >= (select date('now','start of month','localtime')) " +
                "  and date <= (select date('now','start of month','+1 month','-1 day','localtime'))"+
                "  and multitapCode='"+multitapCode+"';";

        Cursor c = db.rawQuery(sql, null);

        return getReturnVal(c);
    }

    public float getAmountUsed_thisMonth_kWh(String multitapCode, int plugNumber){
        String sql = "select sum(amountUsed) as sum " +
                "from bill " +
                "where date >= (select date('now','start of month','localtime')) " +
                "  and date <= (select date('now','start of month','+1 month','-1 day','localtime'))"+
                "  and multitapCode='"+multitapCode+"'" +
                "  and plugNumber="+plugNumber+";";
        Cursor c = db.rawQuery(sql, null);
        Log.i("bill", sql);

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

    public void printAllData() {
        Cursor c = db.query("bill", null, null, null, null, null, null);
        Log.i("c.getCount()",c.getCount()+"");

        while(c.moveToNext()){
            Log.i("bill",c.getString(c.getColumnIndex("multitapCode"))+"  "+c.getString(c.getColumnIndex("date"))+"  "+String.valueOf(c.getInt(c.getColumnIndex("plugNumber")))+"  "+String.valueOf(c.getFloat(c.getColumnIndex("amountUsed"))));
        }

    }

    public void makeBill(){
        String sql = "select multitapCode, plugNumber from bill where date = date('now', 'localtime', '-1 day');";
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()){
            insertBill(c.getString(c.getColumnIndex("multitapCode")), c.getInt(c.getColumnIndex("plugNumber")));
        }
    }

    public void checkAndMakeBill(){

        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
        String str = dayTime.format(new Date());

        if(!str.equals(today)) {
            Log.i("checkAndMakeBill", "next day!");
            Log.i("checkAndMakeBill", today + " is differ from "+ str);
            makeBill();
            today = str;
        }
    }


    public void insertBill(String multitapCode, int plugNumber){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(multitapCode);
        arrayList.add(String.valueOf(plugNumber));
        String sql = new Insert.insertBill().getSQL(arrayList);

        db.execSQL(sql);
    }
}
