package com.moutamid.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.IntentFilter;
import android.os.Bundle;

import com.moutamid.broadcastreceiver.broadcast.CustomBroadCastReceiver;

public class MainActivity extends AppCompatActivity {

    private CustomBroadCastReceiver broadCastReceiver = new CustomBroadCastReceiver();
    String CUSTOM_INTENT_ACTION = "custom_intent_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter("com.moutamid.broadcastsender." + CUSTOM_INTENT_ACTION);
        registerReceiver(broadCastReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadCastReceiver);
    }

    @Override
    public void onBackPressed() {
        if (true)
            return;

        super.onBackPressed();
    }
}