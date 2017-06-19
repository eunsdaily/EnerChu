package com.enerchu.MissoinManager;

import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.SQLite.Singleton.Singleton;
import com.enerchu.SQLite.VO.PlugVO;

/**
 * Created by admin on 2017-06-19.
 */ // missionType 2 : 어제 가장 많은 전기를 먹은 ~ 끄기
class Type2ParamMaker implements ParamMaker{
    @Override
    public String makeParam(int type) {
        MultiTapDAO multiTapDAO = Singleton.getMultiTapDAO();
        PlugDAO plugDAO = Singleton.getPlugDAO();

        // 필요 정보 : 어제 가장 많은 전기 사용한 플러그의 pk
        PlugVO greedyPlug = plugDAO.getLastGreedy();
        String multitapCode = greedyPlug.getMultitapCode();
        String plugNum = String.valueOf(greedyPlug.getPlugNumber());

        MissionChecker.setYesterdayGreedyMultitapCode(multitapCode);
        MissionChecker.setYesterdayGreedyPlugNum(Integer.valueOf(plugNum));

        // return value : multitapCode,multitapNumber
        return multitapCode+","+plugNum;
    }
}
