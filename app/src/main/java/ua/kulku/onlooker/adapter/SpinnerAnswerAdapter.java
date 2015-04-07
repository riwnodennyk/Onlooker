package ua.kulku.onlooker.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import ua.kulku.onlooker.model.Answer;

/**
 * Created by andrii.lavrinenko on 15.03.2015.
 */
public class SpinnerAnswerAdapter extends ArrayAdapter<Answer> {

    public SpinnerAnswerAdapter(Context context, List<Answer> activityTypes) {
        super(context, android.R.layout.simple_spinner_item, activityTypes);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
