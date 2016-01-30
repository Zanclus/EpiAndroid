package com.epitech.wallet_v.epiandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PlanningFragment extends Fragment {


    public PlanningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_planning, container, false);

        // Get Current Planning Date
        Date d = new Date();
        TextView cDate = (TextView) view.findViewById(R.id.currentDate);
        String myDateFormat = "EEEE d MMM";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat);
        cDate.setText(sdf.format(d));

        // Get Activities of the Current Planning Date


        return view;
    }


}
