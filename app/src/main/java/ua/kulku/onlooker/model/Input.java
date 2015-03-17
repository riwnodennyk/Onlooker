package ua.kulku.onlooker.model;

import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

import ua.kulku.onlooker.MyApplication;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
public class Input implements Serializable {


    private int mAge;
    private Question mQuestion;
    private Answer mAnswer;
    private Gender mGender;

    public static void save(Input input) {
        read();
        //todo
        try {
            new ObjectMapper().writeValueAsString(input);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static void read() {
        SharedPreferences modelStorage = MyApplication.getSharedPreferences();
//        String string = modelStorage.getString(INPUTS, "[]");
//
//        JavaType javaType =   CollectionLikeType.construct();
//        try {
//            new ObjectMapper().readValue(string, javaType);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void setAge(int age) {
        mAge = age;
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }

    public void setGender(Gender gender) {
        mGender = gender;
    }

    public void setAnswer(Answer answer) {
        mAnswer = answer;
    }
}
