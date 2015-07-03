package ua.kulku.onlooker.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ua.kulku.onlooker.R;

public class ListInputsActivity extends AppCompatActivity {

    public static final java.lang.String E_QUESTION_ID = "extra question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inputs);
    }
}
