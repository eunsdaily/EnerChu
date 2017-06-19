package com.enerchu.SQLite.SQL;

/**
 * Created by admin on 2017-05-27.
 */

public class CreateTrigger {
    abstract static class CreateTriggerSQL{
        public abstract String getSQL();
    }

    public static class makePlug extends CreateTriggerSQL{
        @Override
        public String getSQL() {
            String sql = "create trigger makePlug after insert on multitap " +
                    "begin " +
                    "   insert or ignore into plug values (new.multitapCode, 1, null, 0);" +
                    "   insert or ignore into plug values (new.multitapCode, 2, null, 0);" +
                    "   insert or ignore into plug values (new.multitapCode, 3, null, 0);" +
                    "   insert or ignore into plug values (new.multitapCode, 4, null, 0);" +
                    "end;";
            return sql;
        }
    }

    public static class makeBill extends CreateTriggerSQL{
        @Override
        public String getSQL() {
            String sql = "create trigger makeBill after insert on plug " +
                    "begin" +
                    "   insert into bill values(new.multitapCode, new.plugNumber,(select date('now', 'localtime')), 0.0);" +
                    "end;";
            return sql;
        }
    }

    public static class makeBillState extends CreateTriggerSQL{
        @Override
        public String getSQL() {
            String sql = "create trigger makeBillState after insert on bill " +
                    "begin " +
                    "   insert or ignore into billState values(new.date, new.amountUsed);" +
                    "end;";
            return sql;
        }
    }

    public static class check_and_rollBack_updateBill extends CreateTriggerSQL{
        @Override
        public String getSQL() {
            String sql = "create trigger check_and_rollBack_updateBill after update on bill " +
                    "when old.amountUsed > new.amountUsed "+
                    "begin " +
                    "   insert or replace into bill values(new.multitapCode, new.plugNumber, new.date, old.amountUsed);" +
                    "end;";
            return sql;
        }
    }

    public static class updateBillState extends CreateTriggerSQL{
        @Override
        public String getSQL() {
            String sql = "create trigger updateBillState after update on bill " +
                    "begin " +
                    "   insert or replace into billState" +
                    "   values( new.date, (new.amountUsed - old.amountUsed + (select amountUsedSum" +
                    "                                                         from billState" +
                    "                                                         where date = new.date)));" +
                    "end;";
            return sql;
        }
    }

    public static class check_and_setZero_updateGrade extends CreateTriggerSQL{
        @Override
        public String getSQL() {
            String sql =
                    "create trigger check_and_setZero_updateGrade after update on client " +
                            "when new.grade < 0 " +
                            "begin " +
                            "   update client set grade = 0 where client.id = new.id;" +
                            "end;";
            return sql;
        }
    }
}

