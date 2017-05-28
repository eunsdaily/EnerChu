package com.enerchu.SQLite.SQL;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-27.
 */

public class Update{
    static abstract class UpdateSQL {
        public abstract String getSQL(ArrayList<String> params);
    }

    public static class updateBill extends UpdateSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : updatedAmountUsed
            // params.get(1) : multitapCode
            // params.get(2) : plugNumber

            String sql =
                    "drop view if exists today; " +
                            "create view today as select date('now', 'localtime') as today;" +
                            "update bill set amountUsed = ("+params.get(0)+"+(select amountUsed " +
                                                                            " from bill, today" +
                                                                            " where bill.multitapCode='"+params.get(1)+"'" +
                                                                                    " and bill.plugNumber="+params.get(2)+
                                                                                    " and date = today.today)),"+
                                            " lastUpdatedUsed = "+params.get(0)+
                                        " where bill.multitapCode='"+params.get(1)+"' and bill.plugNumber="+params.get(2)+
                                                " and bill.date = (select date('now', 'localtime'));" +
                            " drop view if exists today;";
            Log.i("", sql);
            return sql;
        }
    }

    public static class updateGrade extends UpdateSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : id
            // params.get(1) : change

            String sql =
                    "create view nowGrade as select grade as grade from client where client.id = '"+ params.get(0)+"';"+
                            "update client set grade = ((select grade from nowGrade)-"+params.get(1)+")"+
                            "               where client.id = '"+ params.get(0)+"';"+
                            "drop if exists view nowGrade;";
            return sql;
        }
    }

    public static class updateGoal extends UpdateSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : id
            // params.get(1) : goal

            String sql = "update client set goal = "+params.get(1)+" where id = '"+params.get(0)+"';";
            return sql;
        }
    }

    public static class updateMultitapNickname extends UpdateSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : multitapCode
            // params.get(1) : input nickname

            String sql = "update multitap set nickname = '"+params.get(1)+"' where multitapCode = '"+params.get(0)+"';";
            Log.i("update sql", sql);
            return sql;
        }
    }

    public static class updatePlugNickname extends UpdateSQL {
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : multitapCode
            // params.get(1) : input plugNumber
            // params.get(2) : newNickname

            String sql = "update plug set nickname = '"+params.get(2)+"' where multitapCode = '"+params.get(0)+"' and plugNumber = "+params.get(1)+";";
            Log.i("update sql", sql);
            return sql;
        }
    }

    public static class updatePlugState extends UpdateSQL{
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : multitapCode
            // params.get(1) : plugNumber
            // params.get(2) : newState

            String sql = "update plug set onoff = "+params.get(2)+" where multitapCode = '"+params.get(0)+"' and plugNumber = "+params.get(1)+";";
            Log.i("update sql", sql);
            return sql;
        }
    }
}

