package com.enerchu.SQLite.DAO;

import java.util.Random;

/**
 * Created by admin on 2017-05-01.
 */

public class BillDAO {
    public BillDAO(){};

    public float getBeforeBill(String mulitTap, String plug, int before){
        Random random = new Random();
        return random.nextInt(10)+10;
    }

    public float getTodayBill(String multiple, String plug){
        Random random = new Random();
        return random.nextInt(10)+5;
    }

    public float getTomorrowBill(String multiple, String plug){
        Random random = new Random();
        return random.nextInt(10)+10;
    }

    public float getLastMonthBillKWh(){
        return 410;
    }

    public float getLastMonthBillWon(){
        return 75430;
    }

    public float getThisMonthBillKWh(){
        return 215;
    }

    public float getThisMonthBillWon(){
        return 26230;
    }
}
