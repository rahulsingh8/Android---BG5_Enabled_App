package com.example.rahul.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import static android.app.Activity.RESULT_OK;


/*---------------------------------------------------------------------
        |  Class NewEntry
        |
        |  Purpose:  This class is currently no where near completion.
        |             So the only thing we have in it is a button that
        |             will take you to the Bluetooth activity.
        |
        *----------------------------------------------------------------*/

public class NewEntry extends Fragment {

    protected ImageButton mImageButton;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code


    public NewEntry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.new_entry, container, false);

        mImageButton = (ImageButton) rootView.findViewById(R.id.btn_Bluetooth);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), BluetoothActivity.class);
                startActivityForResult(intent, PICK_CONTACT_REQUEST);
            }
        });

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

            }
        }
    }

    public void onResume() {
        super.onResume();
        getActivity().setTitle("New Entry");
    }
}
