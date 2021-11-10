package com.moutamid.broadcastreceiver.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.moutamid.broadcastreceiver.activities.AttractionsActivity;
import com.moutamid.broadcastreceiver.activities.RestaurantsActivity;

public class CustomBroadCastReceiver extends BroadcastReceiver {

    String RESTAURANT = "restaurant";
    String ATTRACTION = "attraction";

    String CUSTOM_INTENT_ACTION = "custom_intent_action";
    String CUSTOM_EXTRA_TEXT = "custom_extra_text";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (("com.moutamid.broadcastsender." + CUSTOM_INTENT_ACTION).equals(intent.getAction())) {
            String receivedText = intent.getStringExtra("com.moutamid.broadcastsender." + CUSTOM_EXTRA_TEXT);

            Intent i;

            if (receivedText.equals(RESTAURANT)) {
                i = new Intent(context, RestaurantsActivity.class);
//                context.startActivity(new Intent(context, RestaurantsActivity.class));
            } else {// (receivedText.equals(ATTRACTION)) {
//                context.startActivity(new Intent(context, AttractionsActivity.class));
                i = new Intent(context, AttractionsActivity.class);
            }

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);

        }
    }
}
