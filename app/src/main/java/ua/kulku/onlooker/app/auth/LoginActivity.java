package ua.kulku.onlooker.app.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.app.InputActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 242;

    public static Intent loginIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        letUserLoginOrSync();
    }

    private void letUserLoginOrSync() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            MyApplication.sComponent.storage()
                    .load(() -> {
                        startActivity(new Intent(LoginActivity.this, InputActivity.class));
                        finish();
                    });
        } else {
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setProviders(AuthUI.GOOGLE_PROVIDER)
                            .setTosUrl("https://www.google.com/policies/terms/")
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            switch (resultCode) {
                case Activity.RESULT_CANCELED:
                    finish();
                    break;
                default:
                    letUserLoginOrSync();
                    break;
            }
        }
    }
}
