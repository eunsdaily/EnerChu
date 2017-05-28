package com.enerchu.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.enerchu.SQLite.SQL.CreateTable;
import com.enerchu.SQLite.SQL.CreateTrigger;
import com.enerchu.SQLite.SQL.Insert;
import com.enerchu.SQLite.SQL.Update;
import com.enerchu.SQLite.VO.PlugVO;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-26.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("DBHelper construct", "called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("onCreate", "called");
        // create table
        db.execSQL(new CreateTable.CreateMulitap().getSQL());
        db.execSQL(new CreateTable.CreatePlug().getSQL());
        db.execSQL(new CreateTable.CreateBill().getSQL());
        db.execSQL(new CreateTable.CreateMission().getSQL());
        db.execSQL(new CreateTable.CreateBillState().getSQL());
        db.execSQL(new CreateTable.CreateClient().getSQL());

        // create trigger
        db.execSQL(new CreateTrigger.makePlug().getSQL());
        db.execSQL(new CreateTrigger.makeBill().getSQL());
        db.execSQL(new CreateTrigger.makeBillState().getSQL());
        db.execSQL(new CreateTrigger.updateBillState().getSQL());
        db.execSQL(new CreateTrigger.check_and_rollBack_updateBill().getSQL());
        db.execSQL(new CreateTrigger.check_and_setZero_updateGrade().getSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBHelper", "onUpgrade");
    }

    public void initialization(SQLiteDatabase db){
        Log.i("initialization", "called");
        // insert testing data
        ArrayList<String> parms = new ArrayList<String>();
        parms.add(0, "id");
        parms.add(1, "pw");
        db.execSQL(new Insert.insertClient().getSQL(parms));

        parms.clear();
        parms.add(0, "mul1");
        parms.add(1, null);
        db.execSQL(new Insert.insertMultitap().getSQL(parms));

        parms.clear();
        parms.add(0, "mul2");
        parms.add(1, null);
        db.execSQL(new Insert.insertMultitap().getSQL(parms));

        parms.clear();
        parms.add(0, "5.0");
        parms.add(1, "mul1");
        parms.add(2, "1");
        db.execSQL(new Update.updateBill().getSQL(parms));
    }
}
