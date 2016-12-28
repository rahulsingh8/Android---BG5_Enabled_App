package com.example.rahul.patient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
/**
 * Created by rahul on 12-Nov-16.
 */


/*---------------------------------------------------------------------
        |  Class HomeFragment
        |
        |  Purpose:  This is a simple home page with a button for a new
        |              entry (suger reading)
        |
        *---------------------------------------------------------------*/

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    protected ImageButton FAB;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        FAB = (ImageButton) rootView.findViewById(R.id.floatingActionButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new NewEntry();
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
                ft.hide(manager.findFragmentById(R.id.fragment_container));
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack("");
                ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return rootView;
    }

    public void onResume() {
        super.onResume();
        getActivity().setTitle("Home");
    }

}