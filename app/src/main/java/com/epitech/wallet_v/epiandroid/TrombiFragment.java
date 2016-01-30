package com.epitech.wallet_v.epiandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
        EpitechApi.trombi("2015", "FR/BDX", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.toString());
                    fillInTrombi(response.getJSONArray("items"));
                    fillInTrombiView(view);
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
        return view;
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
