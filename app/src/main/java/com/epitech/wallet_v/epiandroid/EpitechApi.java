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
    private static String _login;
    private final static String URL = "https://epitech-api.herokuapp.com/";

    public static void setToken(String _token) {
        EpitechApi._token = _token;
    }

    public static String getToken() {
        return (_token);
    }

    public static void login(String login, String password, JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        _login = login;
        requestParams.put("login", login);
        requestParams.put("password", password);
        client.post(EpitechApi.URL + "login", requestParams, callback);
    }

    public static void infos(JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("token", _token);
        requestParams.put("user", _login);
        client.get(EpitechApi.URL + "user", requestParams, callback);
    }

    public static void trombi(String year, String location, JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("token", _token);
        requestParams.put("year", year);
        requestParams.put("location", location);
        requestParams.put("promo", "tek3");
        client.get(EpitechApi.URL + "trombi", requestParams, callback);
    }
}
