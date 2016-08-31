package ua.kulku.onlooker.storage;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.kulku.onlooker.model.Question;

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


    private Firebase userQuestionsFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        return mRootFirebase.child("users").child(currentUser.getUid());
    }

    public void load(final OnLoaded onLoaded) {
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

    public interface OnLoaded {
        void onLoaded();
    }
}
