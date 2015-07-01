package ua.kulku.onlooker.model;

import com.firebase.client.Firebase;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by alavrinenko on 01.07.15.
 */
public class FirebaseStorage implements Storage {

    private final Firebase myFirebaseRef;

    @Inject
    public FirebaseStorage() {
        myFirebaseRef = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com/");
    }

    @Override
    public List<Question> getAllQuestions() {
        return null;
    }

    @Override
    public void add(Question question) {

    }

    @Override
    public void remove(Question question) {

    }

    @Override
    public void save() {

    }

    @Override
    public Question getQuestionById(UUID id) {
        return null;
    }
}
