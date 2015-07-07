package ua.kulku.onlooker.app.auth;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.plus.Plus;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.app.util.GoogleApiClientActivity;

public class LogoutActivity extends GoogleApiClientActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGoogleApiClient();
    }

    protected void logout() {
        if (MyApplication.sComponent.storage().logout()) {
            if (getGoogleApiClient().isConnected()) {
                Plus.AccountApi.clearDefaultAccount(getGoogleApiClient());
                getGoogleApiClient().disconnect();
            }
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

}
