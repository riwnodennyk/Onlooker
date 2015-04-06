package ua.kulku.onlooker.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private static final int RC_ANSWERS_LIST = 26;
    private TextView mTextView;

    public StatsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView = (TextView) view.findViewById(R.id.text_stats);
        setupList();
    }

    private void setupList() {
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
        mTextView.setText(sb.toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_stats, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), AnswersListActivity.class);
            startActivityForResult(intent, RC_ANSWERS_LIST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RC_ANSWERS_LIST:
                    setupList();
                    break;
            }

        }
    }
}
