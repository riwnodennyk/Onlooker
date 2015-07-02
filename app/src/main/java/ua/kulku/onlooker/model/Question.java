package ua.kulku.onlooker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
@JsonTypeName
public class Question {
    public static final Question ADD_MORE = new Question(MyApplication.sInstance.getString(R.string.add_more)) {
        @Override
        public void addPossibleAnswer(Answer answer) {
            throw new UnsupportedOperationException();
        }
    };

    public UUID getId() {
        return id;
    }

    @JsonProperty
    private UUID id;
    @JsonProperty
    private String name;
    @JsonProperty
    private ArrayList<Answer> possibleAnswers = new ArrayList<>();

    public Question(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Question() {
        upgrade();
    }

    private void upgrade() {
        if (id == null)
            id = UUID.randomUUID();
    }

    @JsonIgnore
    public String getAnswerStats() {
        StringBuilder sb = new StringBuilder();
        int inputsCount = getInputsCount();
        for (Answer answer : getPossibleAnswers()) {
            int percent = (int) (100 * (answer.getInputs().size()) / (float) inputsCount);
            sb.append(answer.getName()).append("        ").append(percent).append("%\n");
        }
        return sb.toString();
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

    @JsonIgnore
    public int getInputsCount() {
        int count = 0;
        for (Answer answer : possibleAnswers) {
            count += answer.getInputs().size();
        }
        return count;
    }

    public void addPossibleAnswer(Answer answer) {
        possibleAnswers.add(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id.equals(question.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
