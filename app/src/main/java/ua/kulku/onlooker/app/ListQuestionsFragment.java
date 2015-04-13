package ua.kulku.onlooker.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.adapter.ListQuestionsAdapter;
import ua.kulku.onlooker.model.Data;
import ua.kulku.onlooker.model.Question;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListQuestionsFragment extends Fragment {

    private static final int RC_ANSWERS_LIST = 26;

    public ListQuestionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_questions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_questions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_answers_list_questions) {
            startInputs(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startInputs(Question question) {
        Intent intent = new Intent(getActivity(), ListInputsActivity.class);
        startActivityForResult(intent, RC_ANSWERS_LIST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RC_ANSWERS_LIST:
                    setAdapter();
                    break;
            }

        }
    }

    private void setAdapter() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.list_answers_stats);
        ListQuestionsAdapter adapter = new ListQuestionsAdapter(Data.getAllQuestions()) {
            @Override
            protected boolean onMenuItemClick(MenuItem item, Question question) {
                switch (item.getItemId()) {
                    case R.id.action_answers_list_questions:
                        startInputs(question);
                        return true;
                }
                return false;
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}
