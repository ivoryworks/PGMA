package com.ivoryworks.pgma;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import static android.support.v7.app.NotificationCompat.*;

public class NotificationFragment extends Fragment implements View.OnClickListener{

    public static String TAG = NotificationFragment.class.getSimpleName();
    private final int REQ_CODE_PROFILE = 10000;
    private final int NOTIFICATION_ICON_ONLY = 1;
    private final int NOTIFICATION_TEXT = 2;
    private final int NOTIFICATION_CUSTOM = 3;
    private final int NOTIFICATION_INTENT = 4;
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

        Button btnText = (Button) view.findViewById(R.id.button_text);
        btnText.setOnClickListener(this);

        Button btnCustom = (Button) view.findViewById(R.id.button_custom);
        btnCustom.setOnClickListener(this);

        Button btnIntent = (Button) view.findViewById(R.id.button_intent);
        btnIntent.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Resources res = mContext.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);
        switch (v.getId()) {
        case R.id.button_simple:
            manager.notify(NOTIFICATION_ICON_ONLY, builder.build());
            break;
        case R.id.button_text:
            builder.setContentTitle(res.getString(R.string.notification_title));    // 1行目
            builder.setContentText(res.getString(R.string.notification_text));      // 2行目
            builder.setSubText(res.getString(R.string.notification_subtext));       // 3行目
            builder.setContentInfo(res.getString(R.string.notification_info));      // 右下
            builder.setWhen(System.currentTimeMillis());                            // 時刻情報
            manager.notify(NOTIFICATION_TEXT, builder.build());
            break;
        case R.id.button_custom:
            RemoteViews customView = new RemoteViews(mContext.getPackageName(), R.layout.notification_custom);
            String title = mContext.getResources().getString(R.string.notification_title);
            customView.setTextViewText(R.id.custom_title, title);
            builder.setContent(customView);
            manager.notify(NOTIFICATION_CUSTOM, builder.build());
            break;
        case R.id.button_intent:
            // 電話帳プロフィールを表示する
            Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Profile.CONTENT_URI);
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, REQ_CODE_PROFILE, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(contentIntent);
            builder.setContentTitle(res.getString(R.string.notification_title_open_profile));
            builder.setAutoCancel(true);    // タップしたら削除
            manager.notify(NOTIFICATION_INTENT, builder.build());
            break;
        }
    }
}
