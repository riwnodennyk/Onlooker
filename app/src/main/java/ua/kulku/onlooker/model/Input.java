package ua.kulku.onlooker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.GregorianCalendar;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
@SuppressWarnings("FieldCanBeLocal")
@JsonTypeName

public class Input {
    @JsonProperty
    private int age;
    @JsonProperty
    private Gender gender;
    @JsonProperty
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
