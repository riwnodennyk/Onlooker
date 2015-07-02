//package ua.kulku.onlooker.model;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import ua.kulku.onlooker.JacksonUtils;
//import ua.kulku.onlooker.MyApplication;
//
///**
// * Created by aindrias on 18.03.2015.
// */
//public class SharedPreferencesStorage extends Storage {
//    private static final String SHARED_PREF_KEY = "questions in storage";
//    private ArrayList<Question> mQuestions;
//    private SharedPreferences mModelStorage;
//
//    @Inject
//    public SharedPreferencesStorage(Context context) {
//        mModelStorage = context.getSharedPreferences("ModelStorage", MyApplication.MODE_PRIVATE);
//    }
//
//    public List<Question> getAllQuestions() {
//        restoreIfNeeded();
//        return Collections.unmodifiableList(mQuestions);
//    }
//
//    public void add(Question question) {
//        restoreIfNeeded();
//        mQuestions.add(question);
//        save();
//    }
//
//    public void remove(Question question) {
//        restoreIfNeeded();
//        mQuestions.remove(question);
//        save();
//    }
//
//    private void restoreIfNeeded() {
//        if (mQuestions == null) {
//            String questionsJson = mModelStorage.getString(SHARED_PREF_KEY, "[]");
//            try {
//                mQuestions = JacksonUtils.asQuestions(questionsJson);
//            } catch (IOException e) {
//                e.printStackTrace();
//                mQuestions = new ArrayList<>();
//            }
//        }
//    }
//
//    public void save() {
//        try {
//            mModelStorage.edit()
//                    .putString(SHARED_PREF_KEY, JacksonUtils.toString(mQuestions))
//                    .commit();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
