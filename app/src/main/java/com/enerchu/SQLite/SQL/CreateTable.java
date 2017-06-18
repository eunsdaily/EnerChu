package com.enerchu.SQLite.SQL;

/**
 * Created by admin on 2017-05-27.
 */

public  class CreateTable{
    abstract static class CreateTableSQL {
        public abstract String getSQL();
    }

    public static class CreateMulitap extends CreateTableSQL {
        @Override
        public String getSQL() {
            String sql = "create table multitap(" +
                    "    multitapCode text unique," +
                    "    nickname text," +
                    "    primary key(multitapCode)" +
                    ");";
            return sql;
        }
    }

    public static class CreatePlug extends CreateTableSQL {
        @Override
        public String getSQL() {
            String sql = "create table plug(" +
                    "    multitapCode text," +
                    "    plugNumber integer," +
                    "    nickname text," +
                    "    onoff boolean," +
                    "    primary key(multitapCode, plugNumber)" +
                    "    foreign key(multitapCode) " +
                    "       references multitap(multitapCode)" +
                    "           on delete cascade" +
                    "           on update cascade" +
                    ");";
            return sql;
        }
    }

    public static class CreateBill extends CreateTableSQL {
        @Override
        public String getSQL() {
            String sql = "create table bill(" +
                    "    multitapCode text," +
                    "    plugNumber integer," +
                    "    date Date," +
                    "    amountUsed float," +
                    "    primary key(multitapCode, plugNumber, date)," +
                    "    foreign key(multitapCode, plugNumber) " +
                    "       references plug(multitapCode,plugNumber)" +
                    "           on delete cascade" +
                    "           on update cascade" +
                    ");";
            return sql;
        }
    }

    public static class CreateBillState extends CreateTableSQL {
        @Override
        public String getSQL() {
            String sql = "create table billState(" +
                    "    date Date," +
                    "    amountUsedSum float," +
                    "    primary key(date)" +
                    ");";
            return sql;
        }
    }

    public static class CreateMission extends CreateTableSQL {
        @Override
        public String getSQL() {
            String sql = "create table mission(" +
                    "   date Date," +
                    "    missionType integer," +
                    "    param text," +
                    "    success boolean," +
                    "    primary key(date)" +
                    ");";
            return sql;
        }
    }

    public static class CreateClient extends CreateTableSQL {
        @Override
        public String getSQL() {
            String sql = "create table client(" +
                    "    goal float default 0.0," +
                    "   id text," +
                    "   pw text," +
                    "   grade integer default 50," +
                    "   primary key(id)" +
                    ");";
            return sql;
        }
    }
};
