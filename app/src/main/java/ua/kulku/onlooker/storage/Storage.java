package ua.kulku.onlooker.storage;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
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

    private DataSnapshot mUserSnapshot;
    private final ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mUserSnapshot = dataSnapshot;
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    };
    private Firebase mRootFirebase;

    @Inject
    public Storage(Firebase rootFirebase) {
        mRootFirebase = rootFirebase;
    }


    public void loginAndLoad(String token, final OnLoaded onLoaded) {
        mRootFirebase.authWithOAuthToken("google", token,
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        load(onLoaded);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                    }
                }
        );
    }

    private Firebase userFirebase() {
        return mRootFirebase.child("users").child(mRootFirebase.getAuth().getUid());
    }

    public void load(final OnLoaded onLoaded) {
        assert mRootFirebase.getAuth() != null;
        userFirebase().addValueEventListener(mValueEventListener);
        userFirebase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserSnapshot = dataSnapshot;
                onLoaded.onLoaded();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public ArrayList<Question> getAllQuestions() {
        if (mUserSnapshot == null)
            throw new IllegalStateException("mUserSnapshot == null");

        Map<String, Question> allQuestions = mUserSnapshot.getValue(
                new GenericTypeIndicator<Map<String, Question>>() {
                }
        );
        if (allQuestions == null)
            return new ArrayList<>();
        else
            return new ArrayList<>(allQuestions.values());
    }

    public void add(Question question) {
        mUserSnapshot.getRef().push().setValue(question);
    }

    public void remove(Question question) {
        if (mUserSnapshot == null)
            throw new IllegalStateException("mUserSnapshot == null");

        for (DataSnapshot snapshot : mUserSnapshot.getChildren()) {
            boolean equals = snapshot.getValue(Question.class).equals(question);
            if (equals) {
                snapshot.getRef().setValue(null);
            }
        }
    }

    public void update(Question question) {
        if (mUserSnapshot == null)
            throw new IllegalStateException("mUserSnapshot == null");

        for (DataSnapshot snapshot : mUserSnapshot.getChildren()) {
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
        if (mRootFirebase.getAuth() != null) {
            userFirebase().removeEventListener(mValueEventListener);
            mRootFirebase.unauth();
            return true;
        }
        return false;
    }

    public boolean isLoggedIn() {
        boolean isLoggedIn = mRootFirebase.getAuth() != null;
        Log.d("Storage", "Is logged in: " + isLoggedIn);
        return isLoggedIn;
    }

    public interface OnLoaded {
        void onLoaded();
    }
}
