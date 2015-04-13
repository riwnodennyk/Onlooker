package ua.kulku.onlooker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ua.kulku.onlooker.model.Answer;
import ua.kulku.onlooker.model.Input;
import ua.kulku.onlooker.model.Question;

/**
 * Created by andrii.lavrinenko on 06.04.2015.
 */
@SuppressWarnings("Convert2Lambda")
public class ListInputsAdapter extends RecyclerView.Adapter<ListInputsAdapter.MyViewHolder> {
    private final List<Item> mItems;

    public ListInputsAdapter(List<Item> items) {
        mItems = items;
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
        public Answer answer;

        public String getString() {
            String formattedData = input.getCreateDate() == null ? "?" : SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(input.getCreateDate().getTime());
            return formattedData + ": " + answer.getName();
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
