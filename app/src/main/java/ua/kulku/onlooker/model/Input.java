package ua.kulku.onlooker.model;

import java.util.GregorianCalendar;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
@SuppressWarnings("FieldCanBeLocal")

public class Input {
    private int age;
    private Gender gender;
    private GregorianCalendar createDate;

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setCreateDate(GregorianCalendar createDate) {
        this.createDate = createDate;
    }

    public GregorianCalendar getCreateDate() {
        return createDate;
    }
}
