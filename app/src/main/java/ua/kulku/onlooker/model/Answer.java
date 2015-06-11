package ua.kulku.onlooker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */

@JsonTypeName
public class Answer {
    public static final Answer ADD_MORE = new Answer(MyApplication.getInstance().getString(R.string.add_more));
    @JsonProperty
    private String name;
    @JsonProperty
    private ArrayList<Input> inputs = new ArrayList<>();

    public Answer() {
    }

    public Answer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<Input> getInputs() {
        return Collections.unmodifiableList(inputs);
    }

    @Override
    public String toString() {
        return name;
    }

    public void addInput(Input input) {
        inputs.add(input);
    }

    public void removeInput(Input input) {
        inputs.remove(input);
    }
}
