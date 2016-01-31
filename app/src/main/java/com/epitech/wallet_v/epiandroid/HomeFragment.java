package com.epitech.wallet_v.epiandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            final View view = inflater.inflate(R.layout.fragment_home, container, false);
        EpitechApi.infos(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Picasso.with(getContext()).load(response.get("picture").toString()).into((ImageView)view.findViewById(R.id.profile_picture));
                    EpitechApi.setCourse(response.getString("course_code"));
                    EpitechApi.setLocation(response.getString("location"));
                    EpitechApi.setYear(Calendar.getInstance().get(Calendar.YEAR) - 1);
                    System.out.println(EpitechApi.getYear());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.v("Fail requete info", String.valueOf(errorResponse));
            }
        });
        return view;
    }
}
