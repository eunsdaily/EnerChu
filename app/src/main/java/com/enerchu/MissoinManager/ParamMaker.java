package com.enerchu.MissoinManager;


import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.SQLite.Singleton.Singleton;
import com.enerchu.SQLite.VO.PlugVO;

/**
 * Created by cse on 2017-06-06.
 */

public interface ParamMaker {
    public String makeParam(int type);
}

class GetParamMaker{
    ParamMaker getParamMaker(int type){
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

        return String.valueOf(newParam);
    }
}

// missionType 1 : 에너츄를 이용하여 ~개 플러그 전원 끄기
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
        int newParam = 0;

        if(lastSuccess && ((grade/2)>lastParam+1)){
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

// missionType 2 : 어제 가장 많은 전기를 먹은 ~ 끄기
class Type2ParamMaker implements ParamMaker{
    @Override
    public String makeParam(int type) {
        MultiTapDAO multiTapDAO = Singleton.getMultiTapDAO();
        PlugDAO plugDAO = Singleton.getPlugDAO();

        // 필요 정보 : 어제 가장 많은 전기 사용한 플러그의 pk
        PlugVO greedyPlug = plugDAO.getLastGreedy();
        String multitapCode = greedyPlug.getMultitapCode();
        String plugNum = String.valueOf(greedyPlug.getPlugNumber());

        // return value : multitapCode,multitapNumber
        return multitapCode+","+plugNum;
    }
}

// missionType 3 : 에너츄의 잔소리 무시하지 말기
class Type3ParamMaker implements ParamMaker{
    @Override
    public String makeParam(int type) {
        return "";
    }
}
