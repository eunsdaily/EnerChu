package com.enerchu.MissoinManager;

import com.enerchu.SQLite.DAO.BillStateDAO;
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




    static void checkTodayMissionSuccess(){
        if(!todaySuccess) {
            if (todayType == 0) {
                // missionType 0 : 오늘 사용 전력량 ~ kwh 넘지 않기
                BillStateDAO billStateDAO = Singleton.getBillStateDAO();
                MissionDAO missionDAO = Singleton.getMissionDAO();
                float today = billStateDAO.getAmountUsedSum_today();

                if (today >= todayMissionGoal) {
                    todaySuccess = true;
                    missionDAO.setTodaySuccessToTrue();
                }
            } else if (todayType == 1) {
                // missionType 1 : 에너츄를 이용하여 ~개 플러그 전원 끄기
                MissionDAO missionDAO = Singleton.getMissionDAO();
                if(todayMulOnOffNumber >= Integer.getInteger(missionDAO.getTodayMissionParm())){
                    todaySuccess = true;
                    missionDAO.setTodaySuccessToTrue();
                }
            } else if (todayType == 2) {
                // missionType 2 : 어제 가장 많은 전기를 먹은 ~ 끄기
                // will check on onclick button

            } else if (todayType == 3) {
                // missionType 3 : 에너츄의 잔소리 무시하지 말기
                // will check on door open event
            } else {

            }
        }
    }
}
