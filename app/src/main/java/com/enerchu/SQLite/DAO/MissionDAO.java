package com.enerchu.SQLite.DAO;

import java.util.Random;

/**
 * Created by admin on 2017-05-15.
 */

public class MissionDAO {

    public int getTotalMission(){
        Random random = new Random();
        return random.nextInt(50)+10;
    }
}
