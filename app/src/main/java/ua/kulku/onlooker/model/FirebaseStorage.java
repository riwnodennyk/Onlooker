package ua.kulku.onlooker.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by alavrinenko on 01.07.15.
 */
public class FirebaseStorage implements Storage {

    private final Firebase myFirebaseRef;
    public List<Question> mQuestions = new ArrayList<>();

    @Inject
    public FirebaseStorage() {
        myFirebaseRef = new Firebase("https://onlooker.firebaseio.com/");
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question[] value = dataSnapshot.getValue(Question[].class);
                mQuestions = Arrays.asList(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public List<Question> getAllQuestions() {
        return mQuestions;
    }

    @Override
    public void add(Question question) {
        mQuestions.add(question);

        //todo
    }

    @Override
    public void remove(Question question) {

        //todo
    }

    @Override
    public void save() {

        //todo
    }

    @Override
    public Question getQuestionById(UUID id) {
        //todo
        return null;
    }
}
