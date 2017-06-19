package com.enerchu.MissoinManager;


import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.SQLite.Singleton.Singleton;

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
            mission += "에너츄를 이용하여 플러그 "+param+"개 끄기";
        }else if(missionType == 2) {
            mission += "어제 가장 많은 전기를 먹은 " + getPlugId(param) + "전원 끄기";
        }else if(missionType == 3){
            mission += "에너츄의 잔소리 무시하지 말기";
        }

        return mission;
    }

    private static String getPlugId(String param) {
        PlugDAO plugDAO = Singleton.getPlugDAO();
        MultiTapDAO multiTapDAO = Singleton.getMultiTapDAO();

        String multitapCode = param.substring(0, param.indexOf(','));
        String plugNum = param.substring(param.indexOf(',')+1);

        String multitapNickName = multiTapDAO.getNickName(multitapCode);
        String plugNickName = plugDAO.getNickName(multitapCode, Integer.valueOf(plugNum));

        String returnVal = "";
        if(multitapNickName.equals("-")){
            returnVal += multitapCode;
        }else{
            returnVal += multitapNickName;
        }

        returnVal += "의 ";

        if(plugNickName.equals("-")){
            returnVal += plugNum;
        }else{
            returnVal += plugNickName;
        }
        return returnVal;
    }


    public static ArrayList<String> createMission() {

        Random random = new Random();
        MissionDAO missionDAO = Singleton.getMissionDAO();
        int newType;

        if(missionDAO.getTotalMission() == 0){
            newType = 1;
        }else{
            newType = random.nextInt(totalMissionNUm);
        }

        ParamMaker paramMaker = getParamMaker(newType);
        String newParm = paramMaker.makeParam(newType);

        ArrayList<String> newMission = new ArrayList<>();
        newMission.add(0, String.valueOf(newType));
        newMission.add(1, newParm);

        return newMission;
    }

    static ParamMaker getParamMaker(int type){
        ParamMaker paramMaker = null;
        if (type == 0) {
            paramMaker = new Type0ParamMaker();
        } else if (type == 1) {
            paramMaker = new Type1ParamMaker();
        } else if (type == 2) {
            paramMaker = new Type2ParamMaker();
        } else if (type == 3) {
            paramMaker = new Type3ParamMaker();
        }
        return paramMaker;
    }

}
