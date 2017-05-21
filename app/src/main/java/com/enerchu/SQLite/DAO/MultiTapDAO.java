package com.enerchu.SQLite.DAO;

import java.util.Random;

/**
 * Created by admin on 2017-05-01.
 */

public class MultiTapDAO {
    public String getNickName(String multiTapKey){
        return null;
    }
    public int getTotalOfMultiTap(){
        Random random = new Random();
        return random.nextInt(5)+1;
    }

    public void updateNickName(String s, String newNickname) {

    }
}
