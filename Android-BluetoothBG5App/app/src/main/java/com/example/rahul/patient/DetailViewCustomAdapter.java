package com.example.rahul.patient;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rahul on 24-Nov-16.
 */

/*---------------------------------------------------------------------
        |  Class DetailViewCustomAdapter
        |
        |  Purpose:  This class only contains a custom list the is used
        |               in other classes.
        |
        *---------------------------------------------------------------*/

public class DetailViewCustomAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    Context context;
    int layoutResourceId;
    private FragmentActivity activity;

    public DetailViewCustomAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> homepatientList) {

        this.activity = activity;
        this.data = homepatientList;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        TextView listDate,listTime, listbsl, ARROW, time_taken;

        if (view == null) {
            // inflate UI from XML file
            LayoutInflater layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // convertView = inflater.inflate(R.layout.list_homefragment, null);
            view = layoutInflater.inflate(R.layout.historyfragment_list_layout, null);

        }

        listDate = (TextView)view.findViewById(R.id.listdate);
        listTime = (TextView)view.findViewById(R.id.listTime);
        listbsl = (TextView)view.findViewById(R.id.listbsl);
        ARROW = (TextView)view.findViewById(R.id.listarrow);
        time_taken = (TextView)view.findViewById(R.id.time_taken);

        HashMap<String, String> patientData;
        patientData = data.get(position);


        String BSR_Test_Taken = patientData.get("BSR_Test_Taken");;
        double BSR_Reading = Double.parseDouble(patientData.get("BR_Reading")); //converting BSR_Reading into double for comparison
        Log.v("BSR_Test_Taken=======", "====" + BSR_Test_Taken);
        String After_Meal = "After Meal";
        String Fasting = "Fasting"; //Done


        if (BSR_Test_Taken.equals(Fasting))
        {
            if (BSR_Reading >= 4.0 && BSR_Reading <= 7.0 )
            {
                final SpannableStringBuilder builder = new SpannableStringBuilder("▲");   //Setting Color to arrow
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0,128,0));
                builder.setSpan(fcs, 0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ARROW.setText(builder, TextView.BufferType.SPANNABLE);
            }
            else if(BSR_Reading <= 4.0)
            {
                final  SpannableStringBuilder builder = new SpannableStringBuilder("▼");
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255,0,0));
                builder.setSpan(fcs, 0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ARROW.setText(builder, TextView.BufferType.SPANNABLE);
            }
            else if (BSR_Reading >= 7.0)
            {
                final  SpannableStringBuilder builder = new SpannableStringBuilder("▲");
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255,0,0));
                builder.setSpan(fcs, 0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ARROW.setText(builder, TextView.BufferType.SPANNABLE);
            }

        }
        else if (BSR_Test_Taken.equals(After_Meal))
        {
            if (BSR_Reading <= 9)
            {
                final  SpannableStringBuilder builder = new SpannableStringBuilder("▲");
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0,128,0));
                builder.setSpan(fcs, 0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ARROW.setText(builder, TextView.BufferType.SPANNABLE);
            }
            else
            {
                final  SpannableStringBuilder builder = new SpannableStringBuilder("▲");
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255,0,0));
                builder.setSpan(fcs, 0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ARROW.setText(builder, TextView.BufferType.SPANNABLE);
            }
        }



        listDate.setText(patientData.get("BSR_Date"));
        listTime.setText(patientData.get("BSR_Time"));
        listbsl.setText(patientData.get("BR_Reading"));
        time_taken.setText(patientData.get("BSR_Test_Taken"));

        return view;
    }
}
