package com.enerchu.DAO;

import java.util.Random;

/**
 * Created by admin on 2017-05-01.
 */

public class BillDAO {
    public BillDAO(){};

    public int getBeforeBill(String mulitTap, String plug, int before){
        Random random = new Random();
        return random.nextInt(100)+400;
    }

    public int getTodayBill(String multiple, String plug){
        Random random = new Random();
        return random.nextInt(100)+300;
    }

    public int getTomorrowBill(String multiple, String plug){
        Random random = new Random();
        return random.nextInt(100)+400;
    }
}
