package ua.kulku.onlooker.storage;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

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
        public void onCancelled(DatabaseError databaseError) {

        }

    };
    private DatabaseReference mRootFirebase;

    @Inject
    public Storage(DatabaseReference rootFirebase) {
        mRootFirebase = rootFirebase;
    }


    private DatabaseReference userQuestionsFirebase() {
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
            public void onCancelled(DatabaseError databaseError) {

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

    public Question getQuestionById(String id) {
        for (Question question : getAllQuestions()) {
            if (question.getId().equals(id)) {
                return question;
            }
        }
        return null;
    }

    public boolean logout() {
        userQuestionsFirebase().removeEventListener(mValueEventListener);
        return true;
    }

    public interface OnLoaded {
        void onLoaded();
    }
}
