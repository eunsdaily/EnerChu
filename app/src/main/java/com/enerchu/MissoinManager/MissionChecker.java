package com.enerchu.MissoinManager;

import android.util.Log;

import com.enerchu.SQLite.DAO.BillStateDAO;
import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.Singleton.Singleton;

/**
 * Created by cse on 2017-06-18.
 */

public class MissionChecker {

    static float todayMissionGoal;
    static int todayMulOnOffNumber;
    static int todayAcceptOpenDoorEvent;
    static String yesterdayGreedyMultitapCode;
    static int yesterdayGreedyPlugNum;
    static int todayType;
    static boolean todaySuccess;

    public static void setTodayMissionGoal(float todayMissionGoal) {
        MissionChecker.todayMissionGoal = todayMissionGoal;
    }
    public static void setTodayMulOnOffNumber(int todayMulOnOffNumber) {
        MissionChecker.todayMulOnOffNumber = todayMulOnOffNumber;
    }
    public static void setTodayAcceptOpenDoorEvent(int todayAcceptOpenDoorEvent) {
        MissionChecker.todayAcceptOpenDoorEvent = todayAcceptOpenDoorEvent;
    }
    public static void setYesterdayGreedyMultitapCode(String yesterdayGreedyMultitapCode) {
        MissionChecker.yesterdayGreedyMultitapCode = yesterdayGreedyMultitapCode;
    }
    public static void setYesterdayGreedyPlugNum(int yesterdayGreedyPlugNum) {
        MissionChecker.yesterdayGreedyPlugNum = yesterdayGreedyPlugNum;
    }
    public static void setTodayType(int todayType) {
        MissionChecker.todayType = todayType;
    }
    public static void setTodaySuccess(boolean todaySuccess) {
        MissionChecker.todaySuccess = todaySuccess;
    }

    public static void addTodayMulOnOffNumber(){
        todayMulOnOffNumber++;
    }

    public static void addTodayAcceptOpenDoorEvent(){
        todayAcceptOpenDoorEvent++;
    }

    public static void checkGreedyPlug(String multitapCode, int plugNum){
        if (multitapCode.equals(yesterdayGreedyMultitapCode) && plugNum == yesterdayGreedyPlugNum){
            todaySuccess = true;

            MissionDAO missionDAO = Singleton.getMissionDAO();
            missionDAO.setTodaySuccessToTrue();
        }
    }




    public static void checkTodayMissionSuccess(){

        // check existance today mission
        MissionDAO missionDAO = Singleton.getMissionDAO();
        if(missionDAO.isExistTodayMission()) {
            setTodayType(Integer.valueOf(missionDAO.getTodayMissionType()));
            Log.i("mission checker", "mission checker called");
            if (!todaySuccess) {
                Log.i("mission checker", "ongoing");
                if (todayType == 0) {
                    // missionType 0 : 오늘 사용 전력량 ~ kwh 넘지 않기
                    BillStateDAO billStateDAO = Singleton.getBillStateDAO();
                    float today = billStateDAO.getAmountUsedSum_today();

                    if (today >= todayMissionGoal) {
                        Log.i("mission checker", "type0 success mission");
                        todaySuccess = true;
                        missionDAO.setTodaySuccessToTrue();
                        ClientDAO clientDAO = Singleton.getClientDAO();
                        clientDAO.upGrade(2);
                    }
                } else if (todayType == 1) {
                    // missionType 1 : 에너츄를 이용하여 ~개 플러그 전원 끄기
                    if (todayMulOnOffNumber >= Integer.valueOf(missionDAO.getTodayMissionParm())) {
                        Log.i("mission checker", "type1 success mission");

                        missionDAO.setTodaySuccessToTrue();
                        ClientDAO clientDAO = Singleton.getClientDAO();
                        clientDAO.upGrade(2);
                    }
                } else if (todayType == 2) {
                    // missionType 2 : 어제 가장 많은 전기를 먹은 ~ 끄기
                    // will check on onclick button

                } else if (todayType == 3) {
                    // missionType 3 : 에너츄의 잔소리 무시하지 말기
                    // will check on door open event
                    if(todayAcceptOpenDoorEvent == 1){
                        Log.i("mission checker", "type3 success mission");
                        missionDAO.setTodaySuccessToTrue();
                        ClientDAO clientDAO = Singleton.getClientDAO();
                        clientDAO.upGrade(2);
                        todaySuccess = true;
                    }
                } else {
                }
            }
        }
    }
}
