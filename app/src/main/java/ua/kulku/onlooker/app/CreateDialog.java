package ua.kulku.onlooker.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import ua.kulku.onlooker.R;

/**
 * Created by andrii.lavrinenko on 15.03.2015.
 */
public class CreateDialog extends DialogFragment {
    public static final String NAME_R = "name result";
    private static final String TITLE_A = "title argument";
    private String mTitle;

    public static DialogFragment newInstance(String title) {
        CreateDialog dialog = new CreateDialog();
        Bundle args = new Bundle();
        args.putString(TITLE_A, title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(TITLE_A);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        return new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setView(inflater.inflate(R.layout.dialog_create, null))
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String input = ((TextView) getDialog().findViewById(R.id.name_create)).getText().toString();
                        Intent data = new Intent().putExtra(NAME_R, input);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateDialog.this.getDialog().cancel();
                    }
                }).create();
    }
}
