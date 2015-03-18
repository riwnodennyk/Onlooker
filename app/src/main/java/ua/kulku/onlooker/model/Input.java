package ua.kulku.onlooker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */@JsonTypeName

   public class Input {
    @JsonProperty
    private int age;
    @JsonProperty
    private Gender gender;

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
