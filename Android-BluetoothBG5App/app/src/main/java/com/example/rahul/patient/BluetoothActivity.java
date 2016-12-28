package com.example.rahul.patient;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ihealth.communication.manager.iHealthDevicesCallback;
import com.ihealth.communication.manager.iHealthDevicesManager;

 /*---------------------------------------------------------------------
        |  Class BluetoothActivity
        |
        |  Purpose:  This class searches for Bluetooth devices nearby
        |               and ONLY connects to a BG5 device.
        |
        |  Pre-condition:  - Bluetooth must be turned on.
        |                  - Application will crash for the first time
        |                    due to permissions. Once enabled, it will
        |                    work.
        |
        *-------------------------------------------------------------------*/

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_PERMISSIONS = 0;
    private static final String FOUND = "Found a BG5 Device";
    private static final String DISCOVERY_FINISHED = "Discovery Finished";
    private static final String SEARCH = "Searching for a BG5 Device";
    private static final String CONNECT = "Connected to a BG5 Device";

    final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private int callbackId;
    Context mContext;
    String userName = "rodi_test@gmail.com";
    String clientId = "708bde5b65884f8d9e579e33e66e8e80";
    String clientSecret = "38ff62374a0d4aacadaf0e4fb4ed1931";
    Bundle mBundle = new Bundle();
    TextView mStatusText;

    private iHealthDevicesCallback miHealthDevicesCallback = new iHealthDevicesCallback() {
        @Override
        public void onScanDevice(String s, String s1, int i) {
            Log.i("OnScanDevice", "onScanDevice - mac:" + s + " - deviceType:" + s1 + " - rssi:" + i);
            mStatusText.setText(FOUND);
            mBundle.putString("mac", s);
            mBundle.putString("Type", s1);
            iHealthDevicesManager.getInstance().connectDevice(userName, s);
        }

        @Override
        public void onScanFinish() {
            Log.e("onScanFinish", "Scan Finished");
            mStatusText.setText(DISCOVERY_FINISHED);
        }

        @Override
        public void onDeviceConnectionStateChange(String s, String s1, int i, int i1) {
            Log.e("onDeviceConnStateChange", "mac:" + s + " deviceType:" + s1 + " status:" + i + " errorid:" + i1);
            mStatusText.setText(CONNECT);
            Intent intent = new Intent();
            intent.putExtra("mac", s);
            if (iHealthDevicesManager.TYPE_BG5.equals(s1)) {
                if (i == iHealthDevicesManager.DEVICE_STATE_CONNECTED) {
                    intent.setClass(BluetoothActivity.this, BG5.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onUserStatus(String s, int i) {
            super.onUserStatus(s, i);
        }

        @Override
        public void onDeviceNotify(String s, String s1, String s2, String s3) {
            super.onDeviceNotify(s, s1, s2, s3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        mStatusText = (TextView) findViewById(R.id.status);
        checkPermissions();
        startSearching();
        mContext = getApplicationContext();
    }

    private void startDiscovery() {
        mStatusText.setText(SEARCH);
        long discoveryType = iHealthDevicesManager.DISCOVERY_BG5;
        iHealthDevicesManager.getInstance().startDiscovery(discoveryType);
    }

    protected void startSearching() {
        iHealthDevicesManager.getInstance().init(this);
        callbackId = iHealthDevicesManager.getInstance().registerClientCallback(miHealthDevicesCallback);
        findViewById(R.id.button);
        iHealthDevicesManager.getInstance().addCallbackFilterForDeviceType(callbackId, iHealthDevicesManager.TYPE_BG5);
        iHealthDevicesManager.getInstance().sdkUserInAuthor(BluetoothActivity.this, userName, clientId, clientSecret, callbackId);
        startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
         * When the Activity is destroyed , need to call unRegisterClientCallback method to
         * unregister callback
         */
        iHealthDevicesManager.getInstance().unRegisterClientCallback(callbackId);
        /*
         * When the Activity is destroyed , need to call destroy method of iHealthDeivcesManager to
         * release resources
         */
        iHealthDevicesManager.getInstance().destroy();
    }

    private void checkPermissions() {
        StringBuilder tempRequest = new StringBuilder();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            tempRequest.append(android.Manifest.permission.WRITE_EXTERNAL_STORAGE + ",");
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            tempRequest.append(android.Manifest.permission.READ_PHONE_STATE + ",");
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            tempRequest.append(android.Manifest.permission.RECORD_AUDIO + ",");
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            tempRequest.append(android.Manifest.permission.ACCESS_FINE_LOCATION + ",");
        }
        if (tempRequest.length() > 0) {
            tempRequest.deleteCharAt(tempRequest.length() - 1);
            ActivityCompat.requestPermissions(this, tempRequest.toString().split(","), REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                Log.e("Test", "Test-----------");
                startDiscovery();

                break;
        }
    }
}
