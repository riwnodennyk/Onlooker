package ua.kulku.onlooker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
@JsonTypeName

public class Question {
    public static final Question ADD_MORE = new Question(MyApplication.getInstance().getString(R.string.add_more)) {
        @Override
        public void addPossibleAnswer(Answer answer) {
            throw new UnsupportedOperationException();
        }
    };
    @JsonProperty
    private String name;
    @JsonProperty
    private ArrayList<Answer> possibleAnswers = new ArrayList<>();

    public Question(String name) {
        this.name = name;
    }

    public Question() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Collection<Answer> getPossibleAnswers() {
        return Collections.unmodifiableList(possibleAnswers);
    }

    public int getInputsCount() {
        int count = 0;
        for (Answer answer : possibleAnswers) {
            count += answer.getInputs().size();
        }
        return count;
    }

    public void addPossibleAnswer(Answer answer) {
        possibleAnswers.add(answer);
        Data.save();
    }

}
