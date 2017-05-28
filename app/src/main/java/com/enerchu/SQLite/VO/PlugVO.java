package com.enerchu.SQLite.VO;

/**
 * Created by admin on 2017-05-26.
 */

public class PlugVO {
    private String  multitapCode;
    private int     plugNumber;
    private String  nickname;
    private boolean onoff;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isOnoff() {
        return onoff;
    }

    public void setOnoff(boolean onoff) {
        this.onoff = onoff;
    }


}
