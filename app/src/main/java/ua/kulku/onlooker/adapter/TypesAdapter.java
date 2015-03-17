package ua.kulku.onlooker.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import ua.kulku.onlooker.model.Question;

/**
 * Created by andrii.lavrinenko on 15.03.2015.
 */
public class TypesAdapter extends ArrayAdapter<Question> {

    public TypesAdapter(Context context, List<Question> activities) {
        super(context, android.R.layout.simple_spinner_item, activities);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
