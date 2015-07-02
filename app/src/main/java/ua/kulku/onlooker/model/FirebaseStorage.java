package ua.kulku.onlooker.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by alavrinenko on 01.07.15.
 */
public class FirebaseStorage extends Storage {

    private DataSnapshot mRootSnapshot;
    private Firebase mFirebase;

    @Inject
    public FirebaseStorage(Firebase firebase) {
        mFirebase = firebase;
    }

    @Override
    public void getAllQuestions(final Callback<List<Question>> callback) {
        mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRootSnapshot = dataSnapshot;
                Collection<Question> questions = mRootSnapshot.getValue(
                        new GenericTypeIndicator<Map<String, Question>>() {
                        }
                ).values();
                callback.onLoaded(new ArrayList<>(questions));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void add(Question question) {
        mRootSnapshot.getRef().push().setValue(question);
    }

    @Override
    public void remove(Question question) {
        if (mRootSnapshot == null)
            throw new IllegalStateException("mRootSnapshot == null");

        for (DataSnapshot snapshot : mRootSnapshot.getChildren()) {
            boolean equals = snapshot.getValue(Question.class).equals(question);
            if (equals) {
                snapshot.getRef().setValue(null);
            }
        }
    }

    @Override
    public void save() {
        //todo
//        mRootSnapshot.getRef().setValue(mQuestions);
    }
}
