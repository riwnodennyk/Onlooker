package ua.kulku.onlooker.app;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener.SwipeListener;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.adapter.InputAdapter;

public class AnswersListFragment extends Fragment {

    private InputAdapter mAdapter;

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
        mAdapter = new InputAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new MySwipeListener());
        recyclerView.addOnItemTouchListener(swipeTouchListener);

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
                InputAdapter.Item removed = mAdapter.remove(position);
                removed.answer.removeInput(removed.input);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(getActivity(),
                        getString(R.string.deleted_template, removed.getString()),
                        Toast.LENGTH_SHORT).show();
            }
            mAdapter.notifyDataSetChanged();
            getActivity().setResult(Activity.RESULT_OK);
        }

        @Override
        public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
            onDismissedBySwipe(reverseSortedPositions);
        }
    }
}
