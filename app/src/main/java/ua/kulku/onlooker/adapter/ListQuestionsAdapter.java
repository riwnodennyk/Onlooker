package ua.kulku.onlooker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.model.Answer;
import ua.kulku.onlooker.model.Question;

public class ListQuestionsAdapter extends RecyclerView.Adapter<ListQuestionsAdapter.MyViewHolder> {
    private final List<Question> mQuestions;

    public ListQuestionsAdapter(List<Question> questions) {
        mQuestions = questions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Question question = mQuestions.get(i);

        myViewHolder.title.setText(question.getName());

        StringBuilder sb = new StringBuilder();
        int inputsCount = question.getInputsCount();
        for (Answer answer : question.getPossibleAnswers()) {
            int percent = (int) (100 * (answer.getInputs().size()) / (float) inputsCount);
            sb.append(answer.getName()).append("        ").append(percent).append("%\n");
        }
        String text = sb.toString();
        myViewHolder.answers.setText(text);
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView answers;

        public MyViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_list, parent, false));
            title = (TextView) itemView.findViewById(R.id.title_item_question_list);
            answers = (TextView) itemView.findViewById(R.id.answers_item_question_list);
        }
    }
}
