package com.enerchu.SQLite.VO;

import java.sql.Date;

/**
 * Created by admin on 2017-05-26.
 */

public class BillStateVO {

    private Date date;
    private float billSum;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getBillSum() {
        return billSum;
    }

    public void setBillSum(float billSum) {
        this.billSum = billSum;
    }
}
