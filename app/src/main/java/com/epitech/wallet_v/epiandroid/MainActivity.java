package com.epitech.wallet_v.epiandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView mLogin;
    private EditText mPassword;
    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin = (AutoCompleteTextView) findViewById(R.id.login);
        mPassword = (EditText) findViewById(R.id.password);
        focusView = null;
    }

    private boolean isPasswordValid(String password){
        return (password.length() >= 8);
    }

    private boolean isLoginValid(String login) {
        return (login.contains("_") && login.length() >= 6);
    }

    private boolean errorFieldLogIn () {
        String login = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        if (!isLoginValid(login)) {
            focusView = mLogin;
            mLogin.setError(getString(R.string.error_login));
            return (false);
        }
        else if (!isPasswordValid(password)) {
            focusView = mPassword;
            mPassword.setError(getString(R.string.error_password));
            return (false);
        }
        return (true);
    }

    private void logInSucces() {
        Intent intent = new Intent(MainActivity.this, NavigationDrawer.class);
        startActivity(intent);
    }

    public void logIn(View view) {
        mLogin.setError(null);
        mPassword.setError(null);

//        if (!errorFieldLogIn()) {
//            focusView.requestFocus();
//        } else {
            EpitechApi.login(/*mLogin.getText().toString()*/"tran_0",
                    /*mPassword.getText().toString()*/"9Pwl0|QS", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.v("Login function", "succes");
                            try {
                                Log.v("Login function", "succes 2");
                                Object token = response.get("token");
                                EpitechApi.setToken(token.toString());
                                logInSucces();
                            } catch (JSONException e) {
                                Log.v("Login function", "exception");
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            mPassword.setError("Invalid Password");
                            focusView = mPassword;
                            focusView.requestFocus();
                        }
                    });
        }
//    }
}
