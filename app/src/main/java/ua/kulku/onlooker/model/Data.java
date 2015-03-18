package ua.kulku.onlooker.model;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ua.kulku.onlooker.Jackson.sObjectMapper;
import static ua.kulku.onlooker.MyApplication.getSharedPreferences;

/**
 * Created by aindrias on 18.03.2015.
 */
public final class Data {
    private static final String SHARED_PREF_KEY = "questions in storage";
    private static ArrayList<Question> sQuestions;

    public static List<Question> getAll() {
        restoreIfNeeded();
        return Collections.unmodifiableList(sQuestions);
    }

    public static void add(Question question) {
        restoreIfNeeded();
        sQuestions.add(question);
        Data.save();
    }

    private static void restoreIfNeeded() {
        if (sQuestions == null) {
            try {
                String questionsJson = getSharedPreferences().getString(SHARED_PREF_KEY, "[]");
                sQuestions = sObjectMapper.readValue(questionsJson,
                        sObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Question.class));
            } catch (IOException e) {
                e.printStackTrace();
                sQuestions = new ArrayList<>();
            }
        }
    }

    static void save() {
        try {
            String questionsJson = sObjectMapper.writeValueAsString(sQuestions);
            getSharedPreferences().edit()
                    .putString(SHARED_PREF_KEY, questionsJson)
                    .commit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
