package ua.kulku.onlooker.model;

import java.io.Serializable;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
public class Input implements Serializable {

    private int mAge;
    private ActivityType mType;
    private Gender mGender;

//    public int getAge() {
//        return mAge;
//    }

    public void setAge(int age) {
        mAge = age;
    }

//    public ActivityType getType() {
//        return mType;
//    }

    public void setType(ActivityType type) {
        mType = type;
    }

//    public Gender getGender() {
//        return mGender;
//    }

    public void setGender(Gender gender) {
        mGender = gender;
    }
}
