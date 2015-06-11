package ua.kulku.onlooker.model;

import java.util.List;
import java.util.UUID;

import javax.inject.Singleton;

@Singleton
public interface Storage {
    List<Question> getAllQuestions();

    void add(Question question);

    void remove(Question question);

    void save();

    Question getQuestionById(UUID id);
}
