package ua.kulku.onlooker.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ua.kulku.onlooker.R;

public class ListQuestionsActivity extends AppCompatActivity {

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
