package com.enerchu.SQLite.VO;

import java.sql.Date;

/**
 * Created by admin on 2017-05-26.
 */

public class MisstionVO {

    private Date    date;
    private int     missionType;
    private String  param;
    private boolean success;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMissionType() {
        return missionType;
    }

    public void setMissionType(int missionType) {
        this.missionType = missionType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
