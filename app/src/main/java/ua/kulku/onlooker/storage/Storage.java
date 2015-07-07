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

    private DataSnapshot mUserQuestionsSnapshot;
    private final ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mUserQuestionsSnapshot = dataSnapshot;
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

    private Firebase userQuestionsFirebase() {
        return mRootFirebase.child("users").child(mRootFirebase.getAuth().getUid());
    }

    public void load(final OnLoaded onLoaded) {
        assert mRootFirebase.getAuth() != null;
        userQuestionsFirebase().addValueEventListener(mValueEventListener);
        userQuestionsFirebase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserQuestionsSnapshot = dataSnapshot;
                onLoaded.onLoaded();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public ArrayList<Question> getAllQuestions() {
        if (mUserQuestionsSnapshot == null)
            throw new IllegalStateException("mUserQuestionsSnapshot == null");

        Map<String, Question> allQuestions = mUserQuestionsSnapshot.getValue(
                new GenericTypeIndicator<Map<String, Question>>() {
                }
        );
        if (allQuestions == null)
            return new ArrayList<>();
        else
            return new ArrayList<>(allQuestions.values());
    }

    public void add(Question question) {
        mUserQuestionsSnapshot.getRef().push().setValue(question);
    }

    public void remove(Question question) {
        if (mUserQuestionsSnapshot == null)
            throw new IllegalStateException("mUserQuestionsSnapshot == null");

        for (DataSnapshot snapshot : mUserQuestionsSnapshot.getChildren()) {
            boolean equals = snapshot.getValue(Question.class).equals(question);
            if (equals) {
                snapshot.getRef().setValue(null);
            }
        }
    }

    public void update(Question question) {
        if (mUserQuestionsSnapshot == null)
            throw new IllegalStateException("mUserQuestionsSnapshot == null");

        for (DataSnapshot snapshot : mUserQuestionsSnapshot.getChildren()) {
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
            userQuestionsFirebase().removeEventListener(mValueEventListener);
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
