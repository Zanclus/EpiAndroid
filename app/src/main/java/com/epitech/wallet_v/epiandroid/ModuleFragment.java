package com.epitech.wallet_v.epiandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Tran David on 1/31/2016.
 */
public class ModuleFragment extends Fragment {

    class ModuleInfo {
        String  status;
        String  semester;
        String  title;
        String  code;
        String  end;
        String  credits;
        String  location;
        String  registration;
        String  rights;
    }

    ArrayList<ModuleInfo>   modsarray = new ArrayList<>();

    public ModuleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_module, container, false);
        EpitechApi.allmodules(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.toString());
                    fillInModule(response.getJSONArray("items"));
                    fillInTrombiView(view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.v("Fail request allmodules", String.valueOf(errorResponse));
            }
        });

        return view;
    }

    private void    fillInModule(JSONArray modules) {
        try {
            for (int i = 0 ; i < modules.length() ; i++) {
                JSONObject  module = modules.getJSONObject(i);
                if (module.getInt("scolaryear") == EpitechApi.getYear()) {
                    ModuleInfo  newmodule = new ModuleInfo();
                    if (module.has("status"))
                        newmodule.status = module.getString("status");
                    else
                        newmodule.status = "Special";
                    newmodule.semester = module.getString("semester");
                    newmodule.title = module.getString("title");
                    newmodule.code = module.getString("code");
                    newmodule.end = module.getString("end");
                    newmodule.credits = module.getString("credits");
                    newmodule.location = module.getString("location_title");
                    newmodule.registration = module.getString("open");
                    newmodule.rights = module.get("rights").toString();
                    modsarray.add(newmodule);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void    fillInTrombiView(View view)
    {
        ArrayAdapter<ModuleInfo> adapter = new ModuleArrayAdapter();
        ListView list = (ListView) view.findViewById(R.id.module_list);
        list.setAdapter(adapter);
    }

    private class ModuleArrayAdapter extends ArrayAdapter<ModuleInfo> {
        public ModuleArrayAdapter() {
            super(getActivity(), R.layout.trombi_item, modsarray);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater(null).inflate(R.layout.module_item, parent, false);
            }
            ModuleInfo currentone = modsarray.get(position);

            // Image View
            ImageView   statusView = (ImageView)itemView.findViewById(R.id.module_status);
            if (currentone.status.equals("notregistered"))
                statusView.setImageResource(R.drawable.ic_action_social_person);
            else if (currentone.status.equals("valid"))
                statusView.setImageResource(R.drawable.ic_action_hardware_keyboard_arrow_down);
            else if (currentone.status.equals("ongoing"))
                statusView.setImageResource(R.drawable.ic_action_social_valid);
            else if (currentone.status.equals("fail"))
                statusView.setImageResource(R.drawable.ic_action_content_backspace);
            else if (currentone.status.equals("Special"))
                statusView.setImageResource(R.drawable.ic_action_special);

            // Status View
            TextView    statusText = (TextView)itemView.findViewById(R.id.module_txt_status);
            statusText.setText(currentone.status);

            // Semester View
            TextView    semesterText = (TextView)itemView.findViewById(R.id.module_semester);
            semesterText.setText(currentone.semester);

            // Title View
            TextView    titleText = (TextView)itemView.findViewById(R.id.module_title);
            titleText.setText(currentone.title);

            // Code View
            TextView    codeText = (TextView)itemView.findViewById(R.id.module_code);
            codeText.setText(currentone.code);

            // End View
            TextView    endText = (TextView)itemView.findViewById(R.id.module_end);
            endText.setText(currentone.end);

            // Credits View
            TextView    creditsText = (TextView)itemView.findViewById(R.id.module_credits);
            creditsText.setText(currentone.credits);

            // Location View
            TextView    locationText = (TextView)itemView.findViewById(R.id.module_location);
            locationText.setText(currentone.location);

            // Registration View
            TextView    registrationText = (TextView)itemView.findViewById(R.id.module_registration);
            registrationText.setText(currentone.registration);

            // Rights View
            TextView    rightsText = (TextView)itemView.findViewById(R.id.module_rights);
            rightsText.setText(currentone.rights);

            return itemView;
        }
    }
}
