package com.enerchu.MissoinManager;

import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.SQLite.DAO.Singleton;
import com.enerchu.SQLite.VO.PlugVO;

/**
 * Created by cse on 2017-06-06.
 */


public class ParamMaker {

    public abstract class paramMaker {
        private int type;

        public paramMaker(int type) {
            this.type = type;
        }
        public abstract String makeParam(int type);
    }

    // missionType 0 : 오늘 사용 전력량 ~ kwh 넘지 않기
    public class Type0ParamMaker extends paramMaker{
        public Type0ParamMaker(int type){
            super(type);
        }

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

            float lastParam = Float.parseFloat(missionDAO.getLastParam(type));
            boolean lastSuccess = MissionDAO.getLastSuccess(type);
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
    public class Type1ParamMaker extends paramMaker{
        public Type1ParamMaker(int type){
            super(type);
        }

        @Override
        public String makeParam(int type) {
            // 필요 정보 : 지난 미션에서 type가 1이고, 날짜가 가장 최근인 row의 param과 성공여부
            //            grade
            // param 결정 방식
            //      지난 미션에 성공하였다면 지난 미션 갯수+1, 이 때의 수는 자신의 등급의 절반을 넘어서는 안 된다.
            //      지난 미션에 실패 하였다면 지난 미션과 같은 갯수
//            int lastParam = Integer.getInteger(MissionDAO.getLastParam(type));
            int lastParam = 0;
            boolean lastSuccess = MissionDAO.getLastSuccess(type);
//            int grade = ClientDAO.getGrade();
            int grade = 50;
            int newParam = 0;

            if(lastSuccess && ((grade/2)>lastParam+1)){
                newParam = lastParam + 1;
            }else if(lastSuccess){
                newParam = grade/2;
            }else{
                newParam = lastParam;
            }

            return String.valueOf(newParam);
        }
    }

    // missionType 2 : 어제 가장 많은 전기를 먹은 ~ 끄기
    public class Type2ParamMaker extends paramMaker{
        public Type2ParamMaker(int type){
            super(type);
        }

        @Override
        public String makeParam(int type) {
            // 필요 정보 : 어제 가장 많은 전기 사용한 플러그의 pk
//            PlugVO greedyPlug = PlugDAO.getLastGreedy();
//            String mulitapName = MultiTapDAO.getNickName(greedyPlug.getMultitapCode());
//
//            if(greedyPlug.getNickname().isEmpty() &){
//
//            }
            return "";
        }
    }
}
