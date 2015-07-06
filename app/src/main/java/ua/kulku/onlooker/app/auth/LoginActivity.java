package ua.kulku.onlooker.app.auth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.Plus;

import java.io.IOException;

import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.app.InputActivity;
import ua.kulku.onlooker.app.util.GoogleApiClientActivity;
import ua.kulku.onlooker.storage.Storage;

public class LoginActivity extends GoogleApiClientActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();


    private void getGoogleOAuthTokenAndLogin() {
        /* Get OAuth token in Background */
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            String errorMessage = null;

            @Override
            protected String doInBackground(Void... params) {
                String token = null;

                try {
                    String scope = String.format("oauth2:%s", Scopes.PLUS_LOGIN);
                    token = GoogleAuthUtil.getToken(LoginActivity.this, Plus.AccountApi.getAccountName(getGoogleApiClient()), scope);
                } catch (IOException transientEx) {
                    /* Network or server error */
                    Log.e(TAG, "Error authenticating with Google: " + transientEx);
                    errorMessage = "Network error: " + transientEx.getMessage();
                } catch (UserRecoverableAuthException e) {
                    Log.w(TAG, "Recoverable Google OAuth error: " + e.toString());
                    /* We probably need to ask for permissions, so start the intent if there is none pending */
                    resolve(e);
                } catch (GoogleAuthException authEx) {
                    /* The call is not ever expected to succeed assuming you have already verified that
                     * Google Play services is installed. */
                    Log.e(TAG, "Error authenticating with Google: " + authEx.getMessage(), authEx);
                    errorMessage = "Error authenticating with Google: " + authEx.getMessage();
                }

                return token;
            }

            @Override
            protected void onPostExecute(String token) {
                if (token != null) {
                    MyApplication.sComponent.storage()
                            .init(token, new Storage.OnInit() {
                                @Override
                                public void onLoaded() {
                                    startActivity(new Intent(LoginActivity.this, InputActivity.class));
                                    finish();
                                }
                            });
                } else
                    finish();
            }
        };
        task.execute();
    }


    @Override
    public void onConnected(final Bundle bundle) {
        getGoogleOAuthTokenAndLogin();
    }

}
