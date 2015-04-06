package ua.kulku.onlooker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import ua.kulku.onlooker.model.Answer;
import ua.kulku.onlooker.model.Data;
import ua.kulku.onlooker.model.Input;
import ua.kulku.onlooker.model.Question;

/**
 * Created by andrii.lavrinenko on 06.04.2015.
 */
@SuppressWarnings("Convert2Lambda")
public class InputAdapter extends RecyclerView.Adapter<InputAdapter.MyViewHolder> {
    private final List<Item> mItems;

    public InputAdapter() {
        mItems = new ArrayList<>();

        for (Question question : Data.getAll()) {
            for (Answer answer : question.getPossibleAnswers()) {
                for (Input input : answer.getInputs()) {
                    Item object = new Item();
                    object.question = question;
                    object.answer = answer;
                    object.input = input;
                    mItems.add(object);
                }
            }
        }
        Collections.sort(mItems, new Comparator<Item>() {
            @Override
            public int compare(Item lhs, Item rhs) {
                GregorianCalendar thisData = lhs.input.getCreateDate();
                GregorianCalendar thatDate = rhs.input.getCreateDate();
                if (thisData == null && thatDate == null) return 0;
                if (thisData == null) return -1;
                if (thatDate == null) return 1;
                return thisData.compareTo(thatDate);
            }
        });
        Collections.reverse(mItems);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.text.setText(mItems.get(i).getString());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Item remove(int position) {
        return mItems.remove(position);
    }

    public static class Item {
        public Input input;
        public Question question;
        public Answer answer;

        public String getString() {
            return question.getName() + ": " + answer.getName();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        public MyViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
