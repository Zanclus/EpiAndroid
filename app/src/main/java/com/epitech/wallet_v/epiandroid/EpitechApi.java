package com.epitech.wallet_v.epiandroid;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.callback.Callback;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wallet_v on 24/01/2016.
 */

public class EpitechApi {
    private static String _token;

    public static void setToken(String _token) {
        EpitechApi._token = _token;
    }

    public static String getToken() {
        return (_token);
    }

    public static void login(String login, String password, JsonHttpResponseHandler callback)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("login", login);
        requestParams.put("password", password);
        Log.v("login", login);
        Log.v("password", password);
        Log.v("login", "before the post");
        client.post("https://epitech-api.herokuapp.com/login", requestParams, callback);
        Log.v("login", "after the post");
    }
}
