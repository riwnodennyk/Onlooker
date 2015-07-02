package ua.kulku.onlooker.app;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import dagger.Component;
import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;
import ua.kulku.onlooker.adapter.SpinnerAnswerAdapter;
import ua.kulku.onlooker.adapter.SpinnerQuestionAdapter;
import ua.kulku.onlooker.di.LocalScope;
import ua.kulku.onlooker.model.Answer;
import ua.kulku.onlooker.model.Gender;
import ua.kulku.onlooker.model.Input;
import ua.kulku.onlooker.model.Question;
import ua.kulku.onlooker.model.Storage;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class InputFragment extends Fragment {
    private static final int RC_CREATE_NEW_ANSWER = 21234;
    private static final int RC_CREATE_NEW_TYPE = 961;
    private static final int RC_LIST_QUESTIONS = 72;

    @Inject
    Storage storage;

    private TextView mAgeTextView;
    private Spinner mQuestionSpinner;
    private RadioGroup mGenderView;
    private Spinner mAnswerSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputFragment fragment = this;
        LocalComponent.Instance.get().inject(fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQuestionSpinner = (Spinner) view.findViewById(R.id.type_input);
        setupQuestionSpinner();
        mAgeTextView = (TextView) view.findViewById(R.id.age_input);
        mGenderView = (RadioGroup) view.findViewById(R.id.gender_input);

        mAnswerSpinner = (Spinner) view.findViewById(R.id.answer_input);

        view.findViewById(R.id.show_polls_input)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ListQuestionsActivity.class);
                        startActivityForResult(intent, RC_LIST_QUESTIONS);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_CREATE_NEW_TYPE: {
                    String name = data.getStringExtra(CreateDialog.NAME_R);
                    final Question question = new Question(name);
                    storage.add(question);
                    setupQuestionSpinner();
                    storage.getAllQuestions(new Storage.Callback<List<Question>>() {
                        @Override
                        public void onLoaded(List<Question> questions) {
                            mQuestionSpinner.setSelection(questions.indexOf(question));
                        }
                    });
                    break;
                }
                case RC_CREATE_NEW_ANSWER: {
                    String name = data.getStringExtra(CreateDialog.NAME_R);
                    Answer answer = new Answer(name);
                    ((Question) mQuestionSpinner.getSelectedItem()).addPossibleAnswer(answer);
                    storage.save();
                    setupAnswerSpinner();
                    break;
                }
            }
        }
        switch (requestCode) {
            case RC_LIST_QUESTIONS:
                setupQuestionSpinner();
                break;
        }
    }

    private void showCreateNewAnswerDialog() {
        DialogFragment newFragment = CreateDialog.newInstance(getString(R.string.new_answer));
        newFragment.setTargetFragment(this, RC_CREATE_NEW_ANSWER);
        newFragment.show(getFragmentManager(), "new answer dialog");
    }

    private void setupQuestionSpinner() {
        storage.getAllQuestions(new Storage.Callback<List<Question>>() {
            @Override
            public void onLoaded(List<Question> questions) {
                questions = new ArrayList<>(questions);
                questions.add(Question.ADD_MORE);
                final SpinnerQuestionAdapter adapter = new SpinnerQuestionAdapter(getActivity(), questions);
                mQuestionSpinner.setAdapter(adapter);
                mQuestionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Question selectedQuestion = adapter.getItem(position);
                        if (selectedQuestion == Question.ADD_MORE) {
                            showCreateNewTypeDialog();
                        }
                        setupAnswerSpinner();
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    private void setupAnswerSpinner() {
        Question selectedQuestion = ((Question) mQuestionSpinner.getSelectedItem());
        List<Answer> possibleAnswers = new ArrayList<>(selectedQuestion.getPossibleAnswers());
        possibleAnswers.add(Answer.ADD_MORE);
        final SpinnerAnswerAdapter adapter = new SpinnerAnswerAdapter(getActivity(), possibleAnswers);
        mAnswerSpinner.setAdapter(adapter);
        mAnswerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Answer selectedType = adapter.getItem(position);
                if (Question.ADD_MORE != mQuestionSpinner.getSelectedItem() && Answer.ADD_MORE == selectedType) {
                    showCreateNewAnswerDialog();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showCreateNewTypeDialog() {
        DialogFragment newFragment = CreateDialog.newInstance(getString(R.string.new_type));
        newFragment.setTargetFragment(this, RC_CREATE_NEW_TYPE);
        newFragment.show(getFragmentManager(), "new type dialog");
    }

    public void send() {
        String ageString = mAgeTextView.getText().toString();
        if (!TextUtils.isGraphic(ageString)) {
            Toast.makeText(getActivity(), R.string.invalid_age, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Input input = new Input();
        input.setAge(Integer.parseInt(ageString));
        Gender gender;
        switch (mGenderView.indexOfChild(mGenderView.findViewById(mGenderView.getCheckedRadioButtonId()))) {
            case 0:
                gender = Gender.MALE;
                break;
            case 1:
                gender = Gender.FEMALE;
                break;
            default:
                throw new IllegalStateException("Unsupported index for gender " + mGenderView.indexOfChild(mGenderView.findViewById(mGenderView.getCheckedRadioButtonId())));
        }
        input.setGender(gender);
        input.setCreateDate(new GregorianCalendar());

        ((Answer) mAnswerSpinner.getSelectedItem()).addInput(input);
        storage.save();

        mAgeTextView.setText("");
    }

    @Component(dependencies = MyApplication.AppComponent.class)
    @LocalScope
    public interface LocalComponent {
        class Instance{
            private static LocalComponent get() {
                return DaggerInputFragment_LocalComponent.builder()
                        .appComponent(MyApplication.sComponent)
                        .build();
            }
        }
        void inject(InputFragment fragment);
    }
}
