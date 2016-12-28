package com.example.rahul.patient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihealth.communication.control.Bg5Control;
import com.ihealth.communication.control.Bg5Profile;
import com.ihealth.communication.manager.iHealthDevicesCallback;
import com.ihealth.communication.manager.iHealthDevicesManager;

import java.util.Timer;
import java.util.TimerTask;

 /*---------------------------------------------------------------------
        |  Class BG5
        |
        |  Purpose:  This class uses the established Bluetooth connecti-
        |                on to read data from the Glucometer (BG5) and
        |                print it to the screen.
        |
        |  Pre-condition:  You have to have a successful Bluetooth
        |                    connection to a BG5 device.
        |
        *----------------------------------------------------------------*/

public class BG5 extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Bg5";
    private Bg5Control bg5Control;
    private String deviceMac;
    private int clientCallbackId;
    private TextView tv_return;
    public String QRCode = "02ABCDE67C284BA29ACDFEE6E60A2FE43EDF0C";
    private TextView tv_battery;
    private ImageView mImageView;

    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg5);


        tv_battery = (TextView) findViewById(R.id.tv_Battery);
        Intent intent = getIntent();
        deviceMac = intent.getStringExtra("mac");

        tv_return = (TextView) findViewById(R.id.tv_msgReturn);
        mImageView = (ImageView) findViewById(R.id.imageView);

        clientCallbackId = iHealthDevicesManager.getInstance().registerClientCallback(miHealthDevicesCallback);
        /* Limited wants to receive notification specified device */
        iHealthDevicesManager.getInstance().addCallbackFilterForDeviceType(clientCallbackId, iHealthDevicesManager.TYPE_BG5);
        /* Get bg5 controller */
        bg5Control = iHealthDevicesManager.getInstance().getBg5Control(deviceMac);
        if (bg5Control != null) {
            bg5Control.getBattery();
        }
        if (bg5Control != null)
            bg5Control.startMeasure(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iHealthDevicesManager.getInstance().unRegisterClientCallback(clientCallbackId);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    private iHealthDevicesCallback miHealthDevicesCallback = new iHealthDevicesCallback() {

        @Override
        public void onDeviceConnectionStateChange(String mac,
                                                  String deviceType, int status, int errorID) {
            Log.i(TAG, "mac: " + mac);
            Log.i(TAG, "deviceType: " + deviceType);
            Log.i(TAG, "status: " + status);
            if (status == 2) {//disconnect
                closeTimer();
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = "disconnect" + "  deviceType: " + deviceType + "  mac: " + mac;
                myHandler.sendMessage(msg);
            }
        }

        @Override
        public void onUserStatus(String username, int userStatus) {
            Log.i(TAG, "username: " + username);
            Log.i(TAG, "userState: " + userStatus);
        }

        @Override
        public void onDeviceNotify(String mac, String deviceType,
                                   String action, String message) {
            Log.i(TAG, "mac: " + mac);
            Log.i(TAG, "deviceType: " + deviceType);
            Log.i(TAG, "action: " + action);
            Log.i(TAG, "message: " + message);

            Message msg = new Message();
            msg.what = HANDLER_MESSAGE;

            switch (action) {
                case Bg5Profile.ACTION_BATTERY_BG: {
                    String battery = message.replaceAll("[^0-9.]", "");
                    tv_battery.setText(battery + "%");
                    break;
                }
                case Bg5Profile.ACTION_SET_TIME:
                case Bg5Profile.ACTION_SET_UNIT:
                case Bg5Profile.ACTION_ERROR_BG:
                case Bg5Profile.ACTION_GET_BOTTLEID:
                case Bg5Profile.ACTION_GET_CODEINFO:
                case Bg5Profile.ACTION_HISTORICAL_DATA_BG:
                case Bg5Profile.ACTION_DELETE_HISTORICAL_DATA:
                case Bg5Profile.ACTION_SET_BOTTLE_MESSAGE_SUCCESS:
                case Bg5Profile.ACTION_START_MEASURE: {
                    mImageView.setImageResource(R.drawable.stripincut);


                }
                case Bg5Profile.ACTION_ONLINE_RESULT_BG: {
                    if (message.contains(",")) {
                        msg.obj = "Mesuring Gluco Reading!";
                        String[] readingSplit = message.split(",");
                        Log.d("READING-----------", " " + readingSplit[0]);
                        double reading = Double.parseDouble(readingSplit[0].replaceAll("[^0-9.]", ""));
                        Log.d("READING_DOUBLE-----", " " + reading);
                        reading = reading / 10;
                        Log.d("READING_DOUBLE/10---", " " + reading);
                        msg.obj = (Double.toString(reading)) + " mmol/L";
                        break;
                    }

                }
                case Bg5Profile.ACTION_KEEP_LINK:
                    msg.obj = message;
                    break;
                case Bg5Profile.ACTION_STRIP_IN:
                    msg.obj = "Strip in!";
                    mImageView.setImageResource(R.drawable.bloodincut);
                    break;
                case Bg5Profile.ACTION_GET_BLOOD:
                    msg.obj = "Get Blood!";
                    break;
                case Bg5Profile.ACTION_STRIP_OUT:
                    msg.obj = "Strip Out!";
                    break;
            }
            myHandler.sendMessage(msg);

        }
    };

    private static final int HANDLER_MESSAGE = 101;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_MESSAGE:
                    tv_return.setText((String) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    private void closeTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

}