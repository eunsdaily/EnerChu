package com.enerchu.SQLite.VO;

import java.sql.Date;

/**
 * Created by admin on 2017-05-26.
 */

public class BillVO {

    private String  multitapCode;
    private int     plugNumber;
    private Date    date;
    private float amountUsed;
    private float lastUpdatedUsed;


    public String getMultitapCode() {
        return multitapCode;
    }

    public void setMultitapCode(String multitapCode) {
        this.multitapCode = multitapCode;
    }

    public int getPlugNumber() {
        return plugNumber;
    }

    public void setPlugNumber(int plugNumber) {
        this.plugNumber = plugNumber;
    }

    public float getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(float amountUsed) {
        this.amountUsed = amountUsed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getLastUpdatedUsed() {
        return lastUpdatedUsed;
    }

    public void setLastUpdatedUsed(float lastUpdatedUsed) {
        this.lastUpdatedUsed = lastUpdatedUsed;
    }
}
