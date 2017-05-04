package com.enerchu;

import java.util.Random;

/**
 * Created by admin on 2017-04-30.
 */

public class GradeManager {
    private int grade;

    public int getGrade(){
        Random random = new Random();
        grade = random.nextInt(100)+1;

        return grade;
    }
}
