package com.example.rahul.patient;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rahul on 12-Nov-16.
 */


/*---------------------------------------------------------------------
        |  Class HistoryFragment
        |
        |  Purpose:  This class retrieves data from our database and
        |             display the history of all the Suger readings taken
        |             by the user
        |
        *----------------------------------------------------------------*/

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private String BS_DATETAKEN = "BSR_Date";
    private String BS_READING = "BR_Reading";
    private String BSR_TestTaken = "BSR_Test_Taken";
    private String BS_TIME = "BSR_Time";
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    public String patientSelectedID = "1";    // id of the patient selected is stored here
    public static final String KEY_PATIENT_ID = "patientID";

    private ListView listView;
    public DetailViewCustomAdapter detailViewCustomAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        listView = (ListView) rootView.findViewById(android.R.id.list);

        new getListofReadings().execute();

        return rootView;
    }


    private class getListofReadings extends AsyncTask<Void, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HistoryFragment.this.getActivity(), "Fetching Data", "Wait...", false, false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh = new RequestHandler();
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put(KEY_PATIENT_ID, patientSelectedID);
            Log.v("caretakerID------", "Value of caretaker" + hashmap);
            String s = rh.sendPostRequest(Configuration.URL_GET_DETAILVIEW, hashmap);

            if (s != null) {
                Log.v("Entred in if condition-", "Testing TESTING--");

                try {
                    Log.v("Entred in if condition-", "Testing in try......");

                    JSONArray result = new JSONArray(s);
                    Log.v("result--------------", "Value of jo" + result);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        Log.v("Testing--------------", "Value of jo" + jo);

                        String reading = jo.getString(BS_READING);
                        String bsr_test_taken = jo.getString(BSR_TestTaken);
                        String Date = jo.getString(BS_DATETAKEN);
                        String Time = jo.getString(BS_TIME);

                        HashMap<String, String> readinglist = new HashMap<>();
                        readinglist.put(BS_READING,reading);     // taken for future reference
                        readinglist.put(BS_DATETAKEN, Date);
                        readinglist.put(BS_TIME, Time);
                        readinglist.put(BSR_TestTaken, bsr_test_taken);
                        list.add(readinglist);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.v("Service Handler", "No Data from URL");
            }

            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            detailViewCustomAdapter = new DetailViewCustomAdapter(getActivity(), list);
            listView.setAdapter(detailViewCustomAdapter);

        }
    }



    public void onResume() {
        super.onResume();
        getActivity().setTitle("History");
    }



}
