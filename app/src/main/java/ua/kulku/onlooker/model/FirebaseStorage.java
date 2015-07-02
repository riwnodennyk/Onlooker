package ua.kulku.onlooker.model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alavrinenko on 01.07.15.
 */
public class FirebaseStorage extends Storage {

    private DataSnapshot mRootSnapshot;

    @Inject
    public FirebaseStorage(Firebase firebase) {
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRootSnapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //todo make it more synchronous
    }

    @Override
    public List<Question> getAllQuestions() {
        if (mRootSnapshot == null) {
            return Collections.emptyList();
            //todo remove
        }

        return mRootSnapshot.getValue(
                new GenericTypeIndicator<ArrayList<Question>>() {
                }
        );
    }

    @Override
    public void add(Question question) {
        mRootSnapshot.getRef().push().setValue(question);
    }

    @Override
    public void remove(Question question) {
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
