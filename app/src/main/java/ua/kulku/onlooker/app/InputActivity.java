package ua.kulku.onlooker.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ua.kulku.onlooker.R;
import ua.kulku.onlooker.app.auth.LogoutActivity;


public class InputActivity extends LogoutActivity {

    private static final String TAG = InputActivity.class.getSimpleName();
    private InputFragment mInputFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new InputFragment())
                    .commit();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof InputFragment) {
            mInputFragment = (InputFragment) fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_input_mi:
                send();
                return true;
            case R.id.log_out_mi:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void send() {
        mInputFragment.send();
    }
}
