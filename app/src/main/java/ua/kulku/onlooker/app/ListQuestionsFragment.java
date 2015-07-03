package ua.kulku.onlooker.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;
import ua.kulku.onlooker.adapter.ListQuestionsAdapter;
import ua.kulku.onlooker.model.Question;
import ua.kulku.onlooker.storage.Storage;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListQuestionsFragment extends Fragment {

    private static final int RC_ANSWERS_LIST = 26;

    Storage storage;

    public ListQuestionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        storage = MyApplication.sComponent.storage();
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

    private void startInputs(Question question) {
        Intent intent = new Intent(getActivity(), ListInputsActivity.class)
                .putExtra(ListInputsActivity.E_QUESTION_ID, question.getId());

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
        final RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.list_answers_stats);
        storage.getAllQuestions(new Storage.Callback<List<Question>>() {
            @Override
            public void onLoaded(List<Question> questions) {
                final ListQuestionsAdapter adapter = new ListQuestionsAdapter(questions) {
                    @Override
                    protected boolean onMenuItemClick(MenuItem item, final Question question) {
                        switch (item.getItemId()) {
                            case R.id.action_answers_list_questions:
                                startInputs(question);
                                return true;
                            case R.id.action_remove_questions:
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle(question.getName());
                                builder.setMessage(R.string.want_to_remove_question);
                                builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //nothing to do. cancelled
                                    }
                                });
                                builder.setNegativeButton(R.string.remove, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        remove(question);
                                        notifyDataSetChanged();
                                        storage.remove(question);
                                    }
                                });
                                builder.show();
                                return true;
                        }
                        return false;
                    }
                };
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }
        });
    }
}
