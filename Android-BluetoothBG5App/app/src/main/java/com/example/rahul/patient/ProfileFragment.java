package com.example.rahul.patient;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/*---------------------------------------------------------------------
        |  Class ProfileFragment
        |
        |  Purpose:  This Fragment is responsible for showing the informa-
        |            tion of logged in person. Information is retrieved from
        |            the database
        |
        *----------------------------------------------------------------*/

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private TextView stickyView;
    private View heroImageView;
    private View stickyViewSpacer;
    private ListView profilelistView;

    // Retriving caretaker info parameters
    private String JSON_STRING;
    public String caretakerID = "2";
    public static final String KEY_CT_ID = "caretakerID";
    private String Caretaker_FNAME = "User_FirstName";
    private String Caretaker_LNAME = "User_LastName";
    private String Caretaker_EMAIL = "User_Email";
    private String Caretaker_Type = "User_Type";
    private String Caretaker_Age = "User_Age";
    private String Caretaker_DOB = "User_DOB";
    private String Caretaker_patient_Num = "NumofPatient";
    private ProgressDialog pDialog;
    private ListAdapter lAdapter;
    private String lname = "";
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    // Setting image change on click
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        heroImageView = rootView.findViewById(R.id.profileImageView);
        stickyView = (TextView)rootView.findViewById(R.id.stickyViewProfile);
        profilelistView = (ListView) rootView.findViewById(R.id.profilelist);


        new getProfileInfo().execute();

        stickyView.setText(lname);

        LayoutInflater in = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = in.inflate(R.layout.header, null);
        Log.v("ListHeader========", "ListHeader:" + listHeader);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        profilelistView.addHeaderView(listHeader);

        profilelistView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /* Check if the first item is already reached to top.*/
                if (profilelistView.getFirstVisiblePosition() == 0) {
                    View firstChild = profilelistView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0, heroTopY + topY));

            /* Set the image to scroll half of the amount that of ListView */
                    heroImageView.setY(topY + 0.5f);
                }
            }
        });



        return rootView;
    }


    private class getProfileInfo extends AsyncTask<Void, Void, String>
    {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ProfileFragment.this.getActivity(), "Fetching Data", "Wait.....", false, false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh = new RequestHandler();
            HashMap<String, String> hashmap = new HashMap<>();
            hashmap.put(KEY_CT_ID, caretakerID);
            String s = rh.sendPostRequest(Configuration.URL_GET_PROFILEINFO, hashmap);

            if (s != null) {

                try {
                    JSONArray result = new JSONArray(s);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        JSONObject jo1 = result.getJSONObject(i);
                        int numofpatients = jo1.getInt(Caretaker_patient_Num);
                        String fname = jo.getString(Caretaker_FNAME);
                        lname = jo.getString(Caretaker_LNAME);
                        String email = jo.getString(Caretaker_EMAIL);
                        String usertype = jo.getString(Caretaker_Type);
                        String age = jo.getString(Caretaker_Age);
                        String dob = jo.getString(Caretaker_DOB);
                        String nump = Integer.toString(numofpatients);

                        HashMap<String, String> caretakerProfile = new HashMap<>();
                        caretakerProfile.put(Caretaker_FNAME, fname);
                        caretakerProfile.put(Caretaker_LNAME, lname);
                        caretakerProfile.put(Caretaker_EMAIL, email);
                        caretakerProfile.put(Caretaker_Type, usertype);
                        caretakerProfile.put(Caretaker_Age, age);
                        caretakerProfile.put(Caretaker_DOB, dob);
                        caretakerProfile.put(Caretaker_patient_Num, nump);
                        list.add(caretakerProfile);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.v("Service Handler", "No Data from URL");
            }

            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    ProfileFragment.this.getActivity(), list, R.layout.profile_page_list,
                    new String[]{Caretaker_FNAME, Caretaker_LNAME, Caretaker_EMAIL, Caretaker_Type, Caretaker_Age, Caretaker_DOB, Caretaker_patient_Num},
                    new int[]{R.id.caretaker_FName, R.id.caretaker_LName, R.id.caretaker_emailAdd, R.id.user_type, R.id.caretaker_age, R.id.caretaker_DOB, R.id.number_of_patients});

            profilelistView.setAdapter(adapter);

        }

    }



    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Profile");
    }
}

