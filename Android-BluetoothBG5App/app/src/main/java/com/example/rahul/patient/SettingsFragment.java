package com.example.rahul.patient;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*---------------------------------------------------------------------
        |  Class SettingsFragment
        |
        |  Purpose:  This Fragment is responsible for the Settings page in
        |               the application.It doesn't have many functionality
        |               for now. It supports the Logout button that will
        |               log u out and erase your credentials from shared -
        |               Preferences
        |
        *----------------------------------------------------------------*/


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public Button logout;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        logout = (Button) rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putString("Status", "Logout");
                editor.commit();
                Log.v("SharedPreferences----->", ":" + sharedPreferences);


                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
                startActivity(intent);
            }
        });


        return rootView;
    }

    public void onResume() {
        super.onResume();
        getActivity().setTitle("Settings");
    }

}
