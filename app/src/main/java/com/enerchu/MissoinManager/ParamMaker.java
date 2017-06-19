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
    String makeParam(int type);
}


