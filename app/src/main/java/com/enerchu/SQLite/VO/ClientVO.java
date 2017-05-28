package com.enerchu.SQLite.VO;

/**
 * Created by admin on 2017-05-26.
 */

public class ClientVO {
    private String  id;
    private String  pw;
    private int     grade;
    private float   goal;

    public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
