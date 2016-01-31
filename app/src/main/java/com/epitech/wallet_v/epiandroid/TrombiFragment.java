package com.epitech.wallet_v.epiandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Tran David on 1/28/2016.
 */
public class TrombiFragment extends Fragment {

    private ArrayList<People> arraylist = new ArrayList<People>();
    private View fragView;
    private Spinner tek;
    private Spinner location;
    private Spinner year;
    private Button valid_trombi;
    class People {
       public String title;
       public String login;
       public String picture;
       public String location;
    }
    public TrombiFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_trombi, container, false);
        fragView = view;
        tek = (Spinner) view.findViewById(R.id.trombi_tek);
        location = (Spinner) view.findViewById(R.id.trombi_location);
        year = (Spinner) view.findViewById(R.id.trombi_year);
        valid_trombi = (Button) view.findViewById(R.id.trombi_button);
        valid_trombi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest(0);
            }
        });
        return view;
    }

    private void    makeRequest(final Integer offset) {
        EpitechApi.trombi(year.getSelectedItem().toString(), location.getSelectedItem().toString(), tek.getSelectedItem().toString(), offset, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("items")) {
                        fillInTrombi(response.getJSONArray("items"));
                        fillInTrombiView(fragView);
                        makeRequest(offset + 48);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.v("Fail request trombi", String.valueOf(errorResponse));
            }
        });

    }
    private void    fillInTrombi(JSONArray peoplearray)
    {
        try {
            for (int i = 0 ; i < peoplearray.length() ; i++) {
                JSONObject people_data = peoplearray.getJSONObject(i);
                People result = new People();
                result.title = people_data.getString("title");
                result.location = people_data.getString("location");
                result.picture = people_data.getString("picture");
                result.login = people_data.getString("login");
                System.out.println(result.title);
                arraylist.add(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void    fillInTrombiView(View view)
    {
        ArrayAdapter<People> adapter = new TrombiArrayAdapter();
        ListView list = (ListView) view.findViewById(R.id.trombiListView);
        list.setAdapter(adapter);
    }

    private class TrombiArrayAdapter extends ArrayAdapter<People> {
        public TrombiArrayAdapter() {
            super(getActivity(), R.layout.trombi_item, arraylist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater(null).inflate(R.layout.trombi_item, parent, false);
            }
            People currentone = arraylist.get(position);

            // Image View
            Picasso.with(getContext()).load(currentone.picture).into((ImageView) itemView.findViewById(R.id.item_imgtrombi));

            // Title Text
            TextView titleText = (TextView)itemView.findViewById(R.id.item_txttitle);
            titleText.setText(currentone.title);

            // Login Text
            TextView loginText = (TextView)itemView.findViewById(R.id.item_txtlogin);
            loginText.setText(currentone.login);

            // Location Text
            TextView locationText = (TextView)itemView.findViewById(R.id.item_txtlocation);
            locationText.setText(currentone.location);
            return itemView;
        }
    }
}
