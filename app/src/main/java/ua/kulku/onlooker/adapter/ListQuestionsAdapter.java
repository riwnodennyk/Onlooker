package ua.kulku.onlooker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.model.Question;

public abstract class ListQuestionsAdapter extends RecyclerView.Adapter<ListQuestionsAdapter.MyViewHolder> {
    private final List<Question> mQuestions;

    public ListQuestionsAdapter(List<Question> questions) {
        mQuestions = new ArrayList<>(questions);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        final Question question = mQuestions.get(position);

        myViewHolder.title.setText(question.getName());
        myViewHolder.answers.setText(question.getAnswerStats());
        myViewHolder.popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return ListQuestionsAdapter.this.onMenuItemClick(item, question);
            }
        });
    }

    protected abstract boolean onMenuItemClick(MenuItem item, Question question);

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public void remove(Question question) {
        mQuestions.remove(question);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView answers;
        private final PopupMenu popup;

        public MyViewHolder(final ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_list, parent, false));
            title = (TextView) itemView.findViewById(R.id.title_item_question_list);
            answers = (TextView) itemView.findViewById(R.id.answers_item_question_list);

            View options = itemView.findViewById(R.id.options_item_question_list);
            popup = new PopupMenu(parent.getContext(), options);
            popup.getMenuInflater().inflate(R.menu.menu_list_questions, popup.getMenu());
            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.show();
                }
            });
        }
    }
}
