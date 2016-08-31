package ua.kulku.onlooker.app.auth;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.R;

public class LogoutActivity extends AppCompatActivity {

    protected void logout() {
        if (MyApplication.sComponent.storage().logout()) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogoutActivity.this, R.string.logged_out, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LogoutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}
