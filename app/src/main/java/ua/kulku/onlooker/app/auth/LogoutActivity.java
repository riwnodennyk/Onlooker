package ua.kulku.onlooker.app.auth;

import com.google.android.gms.plus.Plus;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.app.util.GoogleApiClientActivity;

public class LogoutActivity extends GoogleApiClientActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    protected void logout() {
        if (MyApplication.sComponent.storage().logOut()) {
            if (getGoogleApiClient().isConnected()) {
                Plus.AccountApi.clearDefaultAccount(getGoogleApiClient());
                getGoogleApiClient().disconnect();
            }
            finish();
        }
    }

}
