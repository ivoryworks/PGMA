package com.ivoryworks.pgma;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.support.v7.app.NotificationCompat.*;

public class NotificationFragment extends Fragment implements View.OnClickListener{

    public static String TAG = NotificationFragment.class.getSimpleName();
    private final int NOTIFICATION_ICON_ONLY = 1;
    private Context mContext;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    public NotificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        Button btnSimple = (Button) view.findViewById(R.id.button_simple);
        btnSimple.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button_simple:
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);
            manager.notify(NOTIFICATION_ICON_ONLY, builder.build());
            break;
        }
    }
}
