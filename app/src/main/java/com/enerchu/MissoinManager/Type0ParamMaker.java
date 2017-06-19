package com.enerchu.MissoinManager;

import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.Singleton.Singleton;

/**
 * Created by admin on 2017-06-19.
 */
class Type0ParamMaker implements ParamMaker{
    @Override
    public String makeParam(int type) {
        // 필요 정보 : 지난 미션에서 type가 0이고, 날짜가 가장 최근인 row의 param
        //            grade
        // param 결정 방식 : 등급이 높을 수록 절약을 잘 실천하고 있어 수치가 이미 높은 상태이다. 상승폭을 줄인다.
        //       grade > 75 : param * 1.1
        //  75 > grade > 50 : param * 1.15
        //  50 > grade > 25 : param * 1.20
        //  50 > grade      : param * 1.25
        MissionDAO missionDAO = Singleton.getMissionDAO();
        ClientDAO clientDAO = Singleton.getClientDAO();

        String lastParamStr = String.valueOf(missionDAO.getLastParam(type));
        float lastParam = 0;

        if (!lastParamStr.isEmpty()){
            lastParam = Float.parseFloat(lastParamStr);
        }

        boolean lastSuccess = missionDAO.getLastSuccess(type);
        int grade = clientDAO.getGrade();
        float newParam;

        if(lastParam == 0){
            lastParam = 10;
        }

        if (lastSuccess) {
            if (grade > 75) {
                newParam = lastParam * (float) 1.1;
            } else if (grade > 50) {
                newParam = lastParam * (float) 1.15;
            } else if (grade > 25) {
                newParam = lastParam * (float) 1.2;
            } else {
                newParam = lastParam * (float) 1.25;
            }
        }else{
            newParam = lastParam;
        }

        MissionChecker.setTodayMissionGoal(newParam);

        return String.valueOf(newParam);
    }
}
