package com.epitech.wallet_v.epiandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Tran David on 1/29/2016.
 */
public class SearchFragment extends Fragment {
    private android.widget.SearchView search;
    private ArrayList<PeopleS> arraylist = new ArrayList<PeopleS>();
    class PeopleS {
        public String title;
        public String login;
        public String picture;
        public String location;
        public String course;
        public String email;
        public String gpa;
        public String semester;
        public String credits;
        public String promo;
        public String phone;
    }

    public SearchFragment ()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        search = (android.widget.SearchView)view.findViewById(R.id.login_search);
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        search.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EpitechApi.user(query, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        System.out.println(response.toString());
                        fillInSearch(response);
                        fillInSearchView(view);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.v("Fail request user", String.valueOf(errorResponse));
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private void    fillInSearch(JSONObject peopledata)
    {
        try {
                arraylist = new ArrayList<>();
                JSONObject people_data = peopledata;
                PeopleS result = new PeopleS();
                result.title = people_data.getString("title");
                result.location = people_data.getString("location");
                result.picture = people_data.getString("picture");
                result.login = people_data.getString("login");
                result.course = people_data.getString("course_code");
                result.email = people_data.getString("internal_email");
                result.gpa = people_data.getJSONArray("gpa").getJSONObject(0).getString("gpa");
                result.semester = people_data.getString("semester");
                result.credits = people_data.getString("credits");
                result.promo = people_data.getString("promo");
                if (people_data.getJSONObject("userinfo").has("telephone"))
                    result.phone = people_data.getJSONObject("userinfo").getJSONObject("telephone").getString("value");
            arraylist.add(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void    fillInSearchView(View view)
    {
        ArrayAdapter<PeopleS> adapter = new SearchArrayAdapter();
        ListView list = (ListView) view.findViewById(R.id.search_item);
        list.setAdapter(adapter);
    }


    private class SearchArrayAdapter extends ArrayAdapter<PeopleS> {
        public SearchArrayAdapter() {
            super(getActivity(), R.layout.trombi_item, arraylist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater(null).inflate(R.layout.search_item, parent, false);
            }
            PeopleS currentone = arraylist.get(position);

            // Image View
            Picasso.with(getContext()).load(currentone.picture).into((ImageView) itemView.findViewById(R.id.search_img));

            // Title Text
            TextView titleText = (TextView)itemView.findViewById(R.id.search_title);
            titleText.setText(currentone.title);

            // Login Text
            TextView loginText = (TextView)itemView.findViewById(R.id.search_login);
            loginText.setText(currentone.login);

            // Location Text
            TextView locationText = (TextView)itemView.findViewById(R.id.search_location);
            locationText.setText(currentone.location);

            // Course Text
            TextView courseText = (TextView)itemView.findViewById(R.id.search_course);
            courseText.setText(currentone.course);

            // Email Text
            TextView emailText = (TextView)itemView.findViewById(R.id.search_email);
            emailText.setText(currentone.email);

            // gpa Text
            TextView gpaText = (TextView)itemView.findViewById(R.id.search_gpa);
            gpaText.setText(currentone.gpa);

            // Semester Text
            TextView semesterText = (TextView)itemView.findViewById(R.id.search_semester);
            semesterText.setText(currentone.semester);

            // Crédits Text
            TextView creditsText = (TextView)itemView.findViewById(R.id.search_credits);
            creditsText.setText(currentone.credits);

            // Crédits Text
            TextView promoText = (TextView)itemView.findViewById(R.id.search_promo);
            promoText.setText(currentone.promo);

            // Phone Text
            TextView phoneText = (TextView)itemView.findViewById(R.id.search_phone);
            phoneText.setText(currentone.phone);
            return itemView;
        }
    }

}
