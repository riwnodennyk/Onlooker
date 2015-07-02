package ua.kulku.onlooker.model;

import java.util.List;
import java.util.UUID;

public abstract class Storage {
    public abstract void getAllQuestions(Callback<List<Question>> callback);

    public abstract void add(Question question);

    public abstract void remove(Question question);

    public abstract void save();

    public void getQuestionById(final UUID id, final Callback<Question> callback) {
        getAllQuestions(new Callback<List<Question>>() {
            @Override
            public void onLoaded(List<Question> questions) {
                for (Question question : questions) {
                    if (question.getId().equals(id))
                        callback.onLoaded(question);
                }
                callback.onLoaded(null);
            }
        });
    }

    public interface Callback<T> {
        void onLoaded(T t);
    }
}
