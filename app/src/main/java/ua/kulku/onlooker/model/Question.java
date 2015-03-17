package ua.kulku.onlooker.model;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;

import static ua.kulku.onlooker.Jackson.sObjectMapper;
import static ua.kulku.onlooker.MyApplication.getSharedPreferences;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */

public class Question {
    public static final Question ADD_MORE = new Question(MyApplication.getInstance().getString(R.string.add_more)) {
        @Override
        public void addPossibleAnswer(Answer answer) {
            throw new UnsupportedOperationException();
        }
    };
    private static final String SHARED_PREF_KEY = "questions in storage";
    private static List<Question> sQuestions;

    private String mName;
    private List<Answer> mPossibleAnswers = new ArrayList<>();

    public Question(String name) {
        this.mName = name;
    }

    public static List<Question> getAll() {
        restoreIfNeeded();
        return Collections.unmodifiableList(sQuestions);
    }

    public static void add(Question question) {
        restoreIfNeeded();
        sQuestions.add(question);
        save();
    }

    private static void restoreIfNeeded() {
        if (sQuestions == null) {
            try {
                String questionsJson = getSharedPreferences().getString(SHARED_PREF_KEY, "[]");
                sQuestions = sObjectMapper.readValue(questionsJson,
                        sObjectMapper.getTypeFactory().constructCollectionType(List.class, Question.class));
            } catch (IOException e) {
                e.printStackTrace();
                sQuestions = new ArrayList<>();
            }
        }
    }

    private static void save() {
        try {
            String questionsJson = sObjectMapper.writeValueAsString(sQuestions);
            getSharedPreferences().edit()
                    .putString(SHARED_PREF_KEY, questionsJson)
                    .commit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return mName;
    }

    public List<Answer> getPossibleAnswers() {
        return Collections.unmodifiableList(mPossibleAnswers);
    }

    public void addPossibleAnswer(Answer answer) {
        mPossibleAnswers.add(answer);
        save();
    }
}
