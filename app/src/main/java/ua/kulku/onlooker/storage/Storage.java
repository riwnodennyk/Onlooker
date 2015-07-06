package ua.kulku.onlooker.storage;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.kulku.onlooker.model.Question;

/**
 * Created by alavrinenko on 01.07.15.
 */
@Singleton
public class Storage {

    private DataSnapshot mRootSnapshot;
    private final ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mRootSnapshot = dataSnapshot;
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    };
    private Firebase mFirebase;

    @Inject
    public Storage(Firebase firebase) {
        mFirebase = firebase;
    }


    public void loginAndLoad(String token, final OnLoaded onLoaded) {
        mFirebase.authWithOAuthToken("google", token,
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        mFirebase.addValueEventListener(mValueEventListener);
                        load(onLoaded);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                    }
                }
        );
    }

    public void load(final OnLoaded onLoaded) {
        mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRootSnapshot = dataSnapshot;
                onLoaded.onLoaded();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public ArrayList<Question> getAllQuestions() {
        if (mRootSnapshot == null)
            throw new IllegalStateException("mRootSnapshot == null");

        Collection<Question> questions = mRootSnapshot.getValue(
                new GenericTypeIndicator<Map<String, Question>>() {
                }
        ).values();
        return new ArrayList<>(questions);
    }

    public void add(Question question) {
        mRootSnapshot.getRef().push().setValue(question);
    }

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

    public void update(Question question) {
        if (mRootSnapshot == null)
            throw new IllegalStateException("mRootSnapshot == null");

        for (DataSnapshot snapshot : mRootSnapshot.getChildren()) {
            boolean equals = snapshot.getValue(Question.class).equals(question);
            if (equals) {
                snapshot.getRef().setValue(question);
            }
        }
    }

    public Question getQuestionById(UUID id) {
        for (Question question : getAllQuestions()) {
            if (question.getId().equals(id)) {
                return question;
            }
        }
        return null;
    }

    public boolean logout() {
        if (mFirebase.getAuth() != null) {
            mFirebase.removeEventListener(mValueEventListener);
            mFirebase.unauth();
            return true;
        }
        return false;
    }

    public boolean isLoggedIn() {
        boolean isLoggedIn = mFirebase.getAuth() != null;
        Log.d("Storage", "Is logged in: " + isLoggedIn);
        return isLoggedIn;
    }

    public interface OnLoaded {
        void onLoaded();
    }
}
