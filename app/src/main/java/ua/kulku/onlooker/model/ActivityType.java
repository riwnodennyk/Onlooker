package ua.kulku.onlooker.model;

import java.util.ArrayList;
import java.util.List;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */

public class ActivityType {
    public static final ActivityType ADD_MORE = new ActivityType(MyApplication.getInstance().getString(R.string.add_more));
    private String mName;

    public ActivityType(String name) {
        this.mName = name;
    }

    public static List<ActivityType> getAll() {
        List<ActivityType> activityTypes = new ArrayList<>();
        activityTypes.add(ADD_MORE);
        return activityTypes;
    }

    @Override
    public String toString() {
        return mName;
    }
}
