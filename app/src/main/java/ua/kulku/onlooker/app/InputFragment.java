package ua.kulku.onlooker.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.model.ActivityType;
import ua.kulku.onlooker.model.Gender;
import ua.kulku.onlooker.model.Input;

/**
 * A placeholder fragment containing a simple view.
 */
public class InputFragment extends Fragment {
    //    private static final String INPUT_SS = "input saved state";
    //    private Input mInput;
    private TextView mAgeTextView;
    private Spinner mTypeSpinner;
    private RadioGroup mGenderView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) {
//            mInput = new Input();
//        } else {
//            mInput = (Input) savedInstanceState.getSerializable(INPUT_SS);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTypeSpinner = (Spinner) view.findViewById(R.id.type_input);
        setupTypeSpinner();
        mAgeTextView = (TextView) view.findViewById(R.id.age_input);
        mGenderView = (RadioGroup) view.findViewById(R.id.gender_input);
    }

    private void setupTypeSpinner() {
        final TypesAdapter adapter = new TypesAdapter();
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ActivityType selectedTYpe = adapter.getItem(position);
                if (selectedTYpe == ActivityType.ADD_MORE) {
                    showCreateNewTypeDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showCreateNewTypeDialog() {
        //todo
    }

    public Input getInput() {
        Input input = new Input();
        input.setType(((ActivityType) mTypeSpinner.getSelectedItem()));
        input.setAge(Integer.parseInt(mAgeTextView.getText().toString()));
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
        return input;
    }

    private class TypesAdapter extends ArrayAdapter<ActivityType> {
        public TypesAdapter() {
            super(getActivity(), android.R.layout.simple_spinner_item, ActivityType.getAll());
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
    }
}
