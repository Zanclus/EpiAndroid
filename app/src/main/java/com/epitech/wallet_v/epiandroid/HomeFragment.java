package com.epitech.wallet_v.epiandroid;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
                    Picasso.with(getContext()).load(response.get("picture").toString()).into((ImageView) view.findViewById(R.id.profile_picture));
                    EpitechApi.setCourse(response.getString("course_code"));
                    EpitechApi.setLocation(response.getString("location"));
                    EpitechApi.setYear(Calendar.getInstance().get(Calendar.YEAR) - 1);
                    EpitechApi.set_logTime(response.getJSONObject("nsstat").getDouble("active"));
                    TextView logTime = (TextView) view.findViewById(R.id.log_time);
                    logTime.append(String.valueOf(EpitechApi.get_logTime()));
                    logTime.append("h");
                    System.out.println(EpitechApi.get_logTime());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.v("Fail requete info", String.valueOf(errorResponse));
            }
        });

        Message message = new Message(view, getContext());
        System.out.println("toto");
        ArrayList<Message> listMessage = message.getAListOfMessage();
        System.out.println("tata");


        return view;
    }

    public class Message {
        public String photo;
        public String message;
        public View view;
        public Context context;

        public Message(View v, Context c) {
            view = v;
            context = c;
        }

        public Message(String p, String m){
            photo = p;
            message = m;
        }

        public ArrayList<Message> getAListOfMessage() {
            final ArrayList<Message> listMessage = new ArrayList<Message>();

            EpitechApi.messages(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray history = response.getJSONArray("history");
                        for (int i = 0; i < history.length(); i++) {
                            listMessage.add(new Message(history.getJSONObject(i).getJSONObject("user").getString("picture"), history.getJSONObject(i).getString("title")));
                        }
                        MessageAdapter adapter = new MessageAdapter(context, listMessage);
                        ListView list = (ListView) view.findViewById(R.id.listView);
                        list.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.v("Fail requete info", String.valueOf(errorResponse));
                }
            });

            return listMessage;
        }
    }

    public class MessageAdapter extends BaseAdapter {
        private List<Message> _listMessage;
        private Context _context;
        private LayoutInflater _layoutInflater;

        public MessageAdapter(Context context, List<Message> listMessage) {
            _context = context;
            _listMessage = listMessage;
            _layoutInflater = LayoutInflater.from(_context);
        }

        public int getCount() {
            return _listMessage.size();
        }

        public Object getItem(int position) {
            return (_listMessage.get(position));
        }

        public long getItemId(int position) {
            return (position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout layoutItem;

            if (convertView == null) {
                layoutItem = (LinearLayout) _layoutInflater.inflate(R.layout.message_item, parent, false);
            } else {
                layoutItem = (LinearLayout)convertView;
            }

            ImageView messageP = (ImageView)layoutItem.findViewById(R.id.photo_message);
            TextView messageM = (TextView)layoutItem.findViewById(R.id.message_message);
            String toChange = _listMessage.get(position).photo;
            if (toChange != "null")
            {
                toChange = toChange.replace("userprofil/", "userprofil/profilview/");
                toChange = toChange.replace(".bmp", ".jpg");
                Picasso.with(_context).load(toChange).into(messageP);
            }
            messageM.setText(Html.fromHtml(_listMessage.get(position).message));
            return layoutItem;
        }
    }
}
