package com.enerchu.SQLite.SQL;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-27.
 */

public class Delete{
    static abstract class DeleteSQL {
        public abstract String getSQL(ArrayList<String> params);
    }

    public static class deleteMultitap extends DeleteSQL{
        @Override
        public String getSQL(ArrayList<String> params) {
            // params.get(0) : multitapCode

            String sql = "delete from multitap where multitap.multitapCode = '"+params.get(0)+"';";
            return sql;
        }
    }
}


