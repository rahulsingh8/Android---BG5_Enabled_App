package com.example.rahul.patient;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rahul on 12-Nov-16.
 */

/*---------------------------------------------------------------------
        |  Class MessageFragment
        |
        |  Purpose:  This class is responsible on the messaging feature
        |                for the application
        |
        *----------------------------------------------------------------*/

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    public String message;
    Button send;
    EditText text;
    String pID = null;
    String cID = null;

    TextView senderName = null;
    String selectedPatientName = null;
    String logedInUserName = null;
    private DatabaseReference childDatabaseReference;
    private String temp_key;
    private ListView chatView;
    private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter chatAdapter;
    private ArrayList<HashMap<String, String>> chatList;
    private HashMap<String, String> chatHashMap;
    TextView msgText;
    TextView sender;
    private EditText typedMessage;
    private static final String KEY_LOGIN_ID = "Login";

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        msgText = (TextView) rootView.findViewById(R.id.message_text);
        sender = (TextView) rootView.findViewById(R.id.sender);
        typedMessage = (EditText) rootView.findViewById(R.id.MessageText);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", 0);
        logedInUserName = (sharedPreferences.getString(KEY_LOGIN_ID, ""));

        childDatabaseReference = FirebaseDatabase.getInstance().getReference().child(logedInUserName);
        send = (Button) rootView.findViewById(R.id.Send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = (EditText) getActivity().findViewById(R.id.MessageText);
                message = text.getText().toString();

                Map<String, Object> map = new HashMap<String, Object>();

                map.put("Name", logedInUserName);
                map.put("Msg", message);
                map.put("Time", ServerValue.TIMESTAMP);

                String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                Log.v("CurrentDateTime>>>>>>", "" + currentDateTime);

                DatabaseReference childOfchild = childDatabaseReference.child(currentDateTime);

                childOfchild.updateChildren(map);


                typedMessage.setText("");

            }
        });


        childDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    private String chat_msg, chat_userName, dateTime;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {

            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_userName = (String) ((DataSnapshot) i.next()).getValue();
            dateTime = String.valueOf(((DataSnapshot) i.next()).getValue());

            msgText.append(chat_msg + "\n");
            sender.append(chat_userName + "\n");

        }

    }

    public void onResume() {
        super.onResume();
        getActivity().setTitle("Message");
    }

}
