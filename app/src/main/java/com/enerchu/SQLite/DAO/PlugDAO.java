package com.enerchu.SQLite.DAO;

import java.util.Random;

/**
 * Created by admin on 2017-05-01.
 */

public class PlugDAO {
    public String getNickName(String multiTapKey, int numOfPlug){
        return "nick name"+String.valueOf(numOfPlug);
    }
    public boolean getState(String multiTapKey, int numOfPlug){
        Random random = new Random();
        return random.nextBoolean();
    }

    public int getLastMonthBill(String multiTapKey, int i) {
        Random random = new Random();
        return random.nextInt(20);
    }

    public int getThisMonthBill(String multiTapKey, int i) {
        Random random = new Random();
        return random.nextInt(20);
    }

    public void updateNickName(String s, int i, String newNickname) {
    }
}
