package ua.kulku.onlooker.model;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */

public class Answer {
    public static final Answer ADD_MORE = new Answer(MyApplication.getInstance().getString(R.string.add_more));
    private String mName;

    public Answer(String name) {
        this.mName = name;
    }

    @Override
    public String toString() {
        return mName;
    }

}
