package com.enerchu;

import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MissionDAO;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 2017-06-05.
 */

public class MissionMaker {

    private static final int totalMissionNUm = 4;

    // missionType 0 : 오늘 사용 전력량 ~ kwh 넘지 않기
    // missionType 1 : 에너츄를 이용하여 ~개 플러그 전원 끄기
    // missionType 2 : 어제 가장 많은 전기를 먹은 ~ 끄기
    // missionType 3 : 에너츄의 잔소리 무시하지 말기

    public static String makeMissionString(int missionType, String param) {
        String mission = "";

        if(missionType == 0){
            mission += "오늘 사용 전력량 "+param+" kWh 넘지 않기";
        }else if(missionType == 1){
            mission += "에너츄를 이용하여 "+param+"개 플러그 전원 끄기";
        }else if(missionType == 2) {
            mission += "어제 가장 많은 전기를 먹은 " + param + "전원 끄기";
        }else if(missionType == 3){
            mission += "에너츄의 잔소리 무시하지 말기";
        }

        return mission;
    }

    public static ArrayList<String> createMission() {
        ArrayList<String> newMission = new ArrayList<>();
        newMission.add(0, "0");
        newMission.add(1, "25");

        Random random = new Random();
        int newType = random.nextInt(totalMissionNUm);

        if(newType == 0){  }
        else if(newType == 1){ }
        else if(newType == 2){ }
        else if(newType == 3){ }

        return newMission;
    }

}
