package ua.kulku.onlooker.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.model.Answer;
import ua.kulku.onlooker.model.Data;
import ua.kulku.onlooker.model.Question;

/**
 * A placeholder fragment containing a simple view.
 */
public class StatsFragment extends Fragment {

    public StatsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StringBuilder sb = new StringBuilder();
        for (Question question : Data.getAll()) {
            sb.append(question.getName()).append(":\n\n");
            int inputsCount = question.getInputsCount();
            for (Answer answer : question.getPossibleAnswers()) {
                int percent = (int) (100 * (answer.getInputs().size()) / (float) inputsCount);
                sb.append(answer.getName()).append("        ").append(percent).append("%\n");
            }
            sb.append("\n\n\n");
        }
        String text = sb.toString();
        ((TextView) view.findViewById(R.id.text_stats)).setText(text);
    }
}
