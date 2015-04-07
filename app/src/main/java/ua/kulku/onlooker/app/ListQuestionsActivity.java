package ua.kulku.onlooker.app;

import android.app.Activity;
import android.os.Bundle;

import ua.kulku.onlooker.R;

public class ListQuestionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ListQuestionsFragment())
                    .commit();
        }
    }

}
