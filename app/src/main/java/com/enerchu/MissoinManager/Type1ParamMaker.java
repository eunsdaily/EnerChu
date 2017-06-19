package com.enerchu.MissoinManager;

import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.Singleton.Singleton;

/**
 * Created by admin on 2017-06-19.
 */ // missionType 1 : 에너츄를 이용하여 ~개 플러그 전원 끄기
class Type1ParamMaker implements ParamMaker{
    @Override
    public String makeParam(int type) {
        // 필요 정보 : 지난 미션에서 type가 1이고, 날짜가 가장 최근인 row의 param과 성공여부
        //            grade
        // param 결정 방식
        //      지난 미션에 성공하였다면 지난 미션 갯수+1, 이 때의 수는 자신의 등급의 절반을 넘어서는 안 된다.
        //      지난 미션에 실패 하였다면 지난 미션과 같은 갯수
//            int lastParam = Integer.getInteger(MissionDAO.getLastParam(type));
        MissionDAO missionDAO = Singleton.getMissionDAO();
        ClientDAO clientDAO = Singleton.getClientDAO();
        int lastParam = Integer.valueOf(missionDAO.getLastParam(1));
        boolean lastSuccess = missionDAO.getLastSuccess(type);
        int grade = clientDAO.getGrade();
        int newParam;

        if(lastParam == 0){
            newParam = 1;
        }
        else if(lastSuccess && ((grade/2)>lastParam+1)){
            newParam = lastParam + 1;
        }else if(lastSuccess){
            newParam = grade/2;
        }else{
            newParam = lastParam;
        }

        MissionChecker.todayMulOnOffNumber = 0;

        return String.valueOf(newParam);
    }
}
