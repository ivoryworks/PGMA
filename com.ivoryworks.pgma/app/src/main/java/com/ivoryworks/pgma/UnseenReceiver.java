package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UnseenReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.getType();
    }
}
