package ua.kulku.onlooker.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener.SwipeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.model.Answer;
import ua.kulku.onlooker.model.Data;
import ua.kulku.onlooker.model.Input;
import ua.kulku.onlooker.model.Question;

public class AnswersListFragment extends Fragment {

    private ChronologicalInputAdapter mAdapter;

    public AnswersListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answers_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.answers_recycle_view);
        mAdapter = new ChronologicalInputAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new MySwipeListener());
        recyclerView.addOnItemTouchListener(swipeTouchListener);

    }

    private static class ChronologicalInputAdapter extends RecyclerView.Adapter<ChronologicalInputAdapter.MyViewHolder> {
        private final List<Pair<String, GregorianCalendar>> mItems;

        public ChronologicalInputAdapter() {
            mItems = new ArrayList<>();

            for (Question question : Data.getAll()) {
                for (Answer answer : question.getPossibleAnswers()) {
                    String str = question.getName() + ": " + answer.getName();
                    for (Input input : answer.getInputs()) {
                        mItems.add(new Pair<>(str, input.getCreateDate()));
                    }
                }
            }
            Collections.sort(mItems, new Comparator<Pair<String, GregorianCalendar>>() {
                @Override
                public int compare(Pair<String, GregorianCalendar> lhs, Pair<String, GregorianCalendar> rhs) {
                    if (lhs == null && rhs == null) return 0;
                    if (lhs == null) return -1;
                    if (rhs == null) return 1;
                    return lhs.second.compareTo(rhs.second);
                }
            });
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(viewGroup);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            myViewHolder.text.setText(mItems.get(i).first);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public Pair<String, GregorianCalendar> remove(int position) {
            return mItems.remove(position);
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView text;

            public MyViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
                text = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }

    private class MySwipeListener implements SwipeListener {
        @Override
        public boolean canSwipe(int position) {
            return true;
        }

        @Override
        public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
            onDismissedBySwipe(reverseSortedPositions);
        }

        private void onDismissedBySwipe(int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                Pair<String, GregorianCalendar> removed = mAdapter.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(getActivity(),
                        getString(R.string.deleted_template, removed.first),
                        Toast.LENGTH_SHORT).show();
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
            onDismissedBySwipe(reverseSortedPositions);
        }
    }
}
