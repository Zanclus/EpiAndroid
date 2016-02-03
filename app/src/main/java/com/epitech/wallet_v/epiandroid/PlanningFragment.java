package com.epitech.wallet_v.epiandroid;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PlanningFragment extends Fragment {

    private String dateOfDay;

    public class Activity {
        // Keys
        public String _title;
        public String _start;
        public String _end;
        public String _room;
    }

    private ArrayList<Activity> arraylist = new ArrayList<Activity>();
    private View fragView;

    public PlanningFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_planning, container, false);
        fragView = view;
        // Get Current Planning Date
        Date d = new Date();
        TextView cDate = (TextView) view.findViewById(R.id.currentDate);
        String myDateFormat = "EEEE d MMM";
        String apiDateFormat = "yyyy-MM-d";
        final SimpleDateFormat mySdf = new SimpleDateFormat(myDateFormat);
        SimpleDateFormat apiSdf = new SimpleDateFormat(apiDateFormat);
        cDate.setText(mySdf.format(d));
        dateOfDay = apiSdf.format(d);
        // Get Activities of the Current Planning Date
        String startRequest = apiSdf.format(d);
        String endRequest = apiSdf.format(d); // Same fmt & Same day
        getActivities(startRequest, endRequest);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mettre a jour les activités
                arraylist.clear();
                String dt = dateOfDay;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(dt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, 1);
                dateOfDay = sdf.format(c.getTime());
                // mettre a jour la date en haut
                TextView cDate = (TextView) view.findViewById(R.id.currentDate);
                cDate.setText(dateOfDay);
                getActivities(dateOfDay, dateOfDay);
            }
        });
        Button button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mettre a jour les activités
                arraylist.clear();
                String dt = dateOfDay;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(dt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, -1);
                dateOfDay = sdf.format(c.getTime());
                // mettre a jour la date en haut
                TextView cDate = (TextView) view.findViewById(R.id.currentDate);
                cDate.setText(dateOfDay);
                getActivities(dateOfDay, dateOfDay);
            }
        });
        return view;
    }

    private void getActivities(String start, String end) {
        System.out.println(start);
        System.out.println(end);
        EpitechApi.planning(start, end, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (response != null) {
                    System.out.println("ON SUCCESS OVERRIDDEN");
                    setActivitiesArray(response);
                    setActivitiesListView(fragView);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.v("Fail request planning", String.valueOf(errorResponse));
            }
        });
    }

    private void setActivitiesArray(JSONArray activitiesArray) {
        try {
            for (int chill=0; chill < activitiesArray.length(); chill++) {
                System.out.println("SET ACTIVITIES ARRAY");
                JSONObject assoChill = activitiesArray.getJSONObject(chill);
                Activity myChill = new Activity();
                myChill._title = assoChill.getString("acti_title");
                myChill._start = assoChill.getString("start");
                myChill._end = assoChill.getString("end");
                myChill._room = assoChill.getString("codemodule"); // TEST
                System.out.println(myChill._title);
                arraylist.add(myChill);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActivitiesListView(View v) {
        ArrayAdapter<Activity> activitiesAdapter = new ActivityArrayAdapter();
        ListView list = (ListView) v.findViewById(R.id.activitiesListView);
        list.setAdapter(activitiesAdapter);
        System.out.println("SET ACTIVITIES LIST VIEW");
    }

    // Adapter definition
    private class ActivityArrayAdapter extends ArrayAdapter<Activity> {
        public ActivityArrayAdapter() {
            super(getActivity(), R.layout.activity_item, arraylist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater(null).inflate(R.layout.activity_item, parent, false);
            }
            Activity chill = arraylist.get(position);

            // title key
            TextView activityTitle = (TextView)itemView.findViewById(R.id.item_activityTitle);
            activityTitle.setText(chill._title);
            // start key
            TextView activityStart = (TextView)itemView.findViewById(R.id.item_activityStart);
            activityStart.setText(chill._start);
            activityStart.append(" - ");
            // end key
            TextView activityEnd = (TextView)itemView.findViewById(R.id.item_activityEnd);
            activityEnd.setText(chill._end);
            // room key (jeu de mot)
            TextView activityRoom = (TextView)itemView.findViewById(R.id.item_activityRoom);
            activityRoom.setText(chill._room);

            return itemView;
        }
    }
}
