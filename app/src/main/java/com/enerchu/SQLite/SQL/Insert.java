package com.enerchu.SQLite.SQL;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-27.
 */

public class Insert{
    static abstract class InsertSQL {
        public abstract String getSQL(ArrayList<String> params);
    }

    public static class insertMultitap extends InsertSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : multitapCode
            // params.get(1) : nickname

            String sql = "insert or ignore into multitap values('"+ params.get(0)+"', ";

            if (params.get(1) != null){
                sql += "'"+ params.get(1)+"');";
            }
            else{
                sql += "null);";
            }
            return sql;
        }
    }


    public static class insertMission extends InsertSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : missionType
            // params.get(1) : param

            Log.i("insertMission called", params.get(0) + " " + params.get(1));
            String sql = "insert or ignore into mission values((select date('now', 'localtime')), "+params.get(0)+", '"+params.get(1)+"', 'false');";
            return sql;
        }
    }

    public static class insertClient extends InsertSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : id
            // params.get(1) : pw

            String sql = "insert or ignore into client(id, pw) values('"+params.get(0)+"', '"+params.get(1)+"');";
            return sql;
        }
    }

    public static class insertBill extends InsertSQL{

        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : updatedAmountUsed
            // params.get(1) : multitapCode
            // params.get(2) : plugNumber

            String sql = "insert or replace into bill values('"+params.get(0)+"', "+params.get(1)+", date('now', 'localtime'), 0.0);";
            return sql;
        }
    }
}