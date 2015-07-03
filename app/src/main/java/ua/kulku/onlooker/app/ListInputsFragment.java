package ua.kulku.onlooker.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener.SwipeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;
import ua.kulku.onlooker.adapter.ListInputsAdapter;
import ua.kulku.onlooker.adapter.ListInputsAdapter.Item;
import ua.kulku.onlooker.model.Answer;
import ua.kulku.onlooker.model.Input;
import ua.kulku.onlooker.model.Question;
import ua.kulku.onlooker.storage.Storage;

public class ListInputsFragment extends Fragment {

    private Storage mStorage;
    private ListInputsAdapter mAdapter;
    private Question mQuestion;

    public ListInputsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorage = MyApplication.sComponent.storage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_inputs, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UUID id = (UUID) getActivity().getIntent().getExtras().getSerializable(ListInputsActivity.E_QUESTION_ID);
        mStorage.getQuestionById(id, new Storage.Callback<Question>() {
            @Override
            public void onLoaded(Question question) {
                if (question == null)
                    throw new IllegalArgumentException("ListInputsActivity.E_QUESTION_ID not found in the retained IDs list.");
                mQuestion = question;
                android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(mQuestion.getName());
                }
                mAdapter = new ListInputsAdapter(getItems(question.getPossibleAnswers()));
                final RecyclerView recyclerView = (RecyclerView) getView();
                assert recyclerView != null;
                recyclerView.setAdapter(mAdapter);
                recyclerView.addItemDecoration(new DividerDecoration());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                SwipeableRecyclerViewTouchListener swipeTouchListener =
                        new SwipeableRecyclerViewTouchListener(recyclerView,
                                new MySwipeListener());
                recyclerView.addOnItemTouchListener(swipeTouchListener);
            }
        });
    }

    private List<Item> getItems(Collection<Answer> possibleAnswers) {
        ArrayList<Item> items = new ArrayList<>();
        for (Answer answer : possibleAnswers) {
            for (Input input : answer.getInputs()) {
                Item object = new Item();
                object.answer = answer;
                object.input = input;
                items.add(object);
            }
        }
        Collections.sort(items, new Comparator<Item>() {
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
        Collections.reverse(items);
        return items;
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
            for (final int position : reverseSortedPositions) {

                final Item removed = mAdapter.remove(position);
                mAdapter.notifyItemRemoved(position);

                removed.answer.removeInput(removed.input);
                mStorage.update(mQuestion);

                Snackbar.make(getView(),
                        getString(R.string.deleted_template, removed.getString()),
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAdapter.add(position, removed);
                                mAdapter.notifyItemInserted(position);

                                removed.answer.addInput(removed.input);
                                mStorage.update(mQuestion);
                            }
                        }).show();
            }
            mAdapter.notifyDataSetChanged();
            getActivity().setResult(Activity.RESULT_OK);
        }

        @Override
        public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
            onDismissedBySwipe(reverseSortedPositions);
        }
    }

    private class DividerDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider = getActivity().getResources().getDrawable(android.R.drawable.divider_horizontal_bright);

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }

    }
}
