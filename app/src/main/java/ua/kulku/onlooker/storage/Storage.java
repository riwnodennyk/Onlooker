package ua.kulku.onlooker.storage;

import java.util.List;
import java.util.UUID;

import ua.kulku.onlooker.model.Question;

public abstract class Storage {
    public abstract void getAllQuestions(Callback<List<Question>> callback);

    public abstract void add(Question question);

    public abstract void update(Question question);

    public abstract void remove(Question question);

    public void getQuestionById(final UUID id, final Callback<Question> callback) {
        getAllQuestions(new Callback<List<Question>>() {
            @Override
            public void onLoaded(List<Question> questions) {
                for (Question question : questions) {
                    if (question.getId().equals(id)) {
                        callback.onLoaded(question);
                        return;
                    }
                }
                callback.onLoaded(null);
            }
        });
    }


    public interface Callback<T> {
        void onLoaded(T t);
    }
}
