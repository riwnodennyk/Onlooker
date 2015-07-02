package ua.kulku.onlooker.model;

import java.util.List;
import java.util.UUID;

public abstract class Storage {
    public abstract List<Question> getAllQuestions();

    public abstract void add(Question question);

    public abstract void remove(Question question);

    public abstract void save();

    public Question getQuestionById(UUID id) {
        for (Question question : getAllQuestions()) {
            if (question.getId().equals(id))
                return question;
        }
        return null;
    }
}
