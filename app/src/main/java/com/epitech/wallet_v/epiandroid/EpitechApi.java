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
    private static Integer _year;
    private static String _location;
    private static String _course;
    private final static String URL = "https://epitech-api.herokuapp.com/";

    public static void setToken(String _token) {
        EpitechApi._token = _token;
    }

    public static String getToken() {
        return (_token);
    }

    public static void setYear(Integer _year) {
        EpitechApi._year = _year;
    }

    public static Integer getYear() {
        return (_year);
    }

    public static void setLocation(String _location) {
        EpitechApi._location = _location;
    }

    public static String getLocation() {
        return (_location);
    }

    public static void setCourse(String _course) {
        EpitechApi._course = _course;
    }

    public static String getCourse() {
        return (_course);
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

    public static void trombi(String year, String location, String tek, Integer offset, JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("token", _token);
        requestParams.put("year", year);
        requestParams.put("location", location);
        requestParams.put("promo", tek);
        requestParams.put("offset", offset);
        client.get(EpitechApi.URL + "trombi", requestParams, callback);
    }

    public static void user(String user, JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("token", _token);
        requestParams.put("user", user);
        client.get(EpitechApi.URL + "user", requestParams, callback);
    }

    public static void planning(String start, String end, JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("token", _token);
        requestParams.put("start", start);
        requestParams.put("end", end);
        client.get(EpitechApi.URL + "planning", requestParams, callback);
    }

    public static void allmodules(JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.put("token", _token);
        requestParams.put("year", _year);
        requestParams.put("location", _location);
        requestParams.put("course", _course);
        requestParams.put("scolaryear", _year.toString());
        client.get(EpitechApi.URL + "allmodules", requestParams, callback);
    }
}
