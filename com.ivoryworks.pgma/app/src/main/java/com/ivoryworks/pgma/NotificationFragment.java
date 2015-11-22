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
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;

import static android.support.v7.app.NotificationCompat.*;

public class NotificationFragment extends Fragment implements View.OnClickListener{

    public static String TAG = NotificationFragment.class.getSimpleName();
    private final int REQ_CODE_PROFILE = 10000;
    private final int NOTIFICATION_ICON_ONLY = R.id.button_simple;
    private final int NOTIFICATION_TEXT = R.id.button_text;
    private final int NOTIFICATION_CUSTOM = R.id.button_custom;
    private final int NOTIFICATION_INTENT = R.id.button_intent;
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

        Button btnSimple = (Button) view.findViewById(NOTIFICATION_ICON_ONLY);
        if (btnSimple != null) {
            btnSimple.setOnClickListener(this);
        }

        Button btnText = (Button) view.findViewById(NOTIFICATION_TEXT);
        if (btnText != null) {
            btnText.setOnClickListener(this);
        }

        Button btnCustom = (Button) view.findViewById(NOTIFICATION_CUSTOM);
        if (btnCustom != null) {
            btnCustom.setOnClickListener(this);
        }

        Button btnIntent = (Button) view.findViewById(NOTIFICATION_INTENT);
        if (btnIntent != null) {
            btnIntent.setOnClickListener(this);
        }

        Button btnRemove = (Button) view.findViewById(R.id.button_remove);
        if (btnRemove != null) {
            btnRemove.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Resources res = mContext.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);
        switch (v.getId()) {
        case NOTIFICATION_ICON_ONLY:
            manager.notify(NOTIFICATION_ICON_ONLY, builder.build());
            break;
        case NOTIFICATION_TEXT:
            builder.setContentTitle(res.getString(R.string.notification_title));    // 1行目
            builder.setContentText(res.getString(R.string.notification_text));      // 2行目
            builder.setSubText(res.getString(R.string.notification_subtext));       // 3行目
            builder.setContentInfo(res.getString(R.string.notification_info));      // 右下
            builder.setWhen(System.currentTimeMillis());                            // 時刻情報
            manager.notify(NOTIFICATION_TEXT, builder.build());
            break;
        case NOTIFICATION_CUSTOM:
            RemoteViews customView = new RemoteViews(mContext.getPackageName(), R.layout.notification_custom);
            String title = mContext.getResources().getString(R.string.notification_title);
            customView.setTextViewText(R.id.custom_title, title);
            builder.setContent(customView);
            manager.notify(NOTIFICATION_CUSTOM, builder.build());
            break;
        case NOTIFICATION_INTENT:
            // 電話帳プロフィールを表示する
            Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Profile.CONTENT_URI);
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, REQ_CODE_PROFILE,
                                                            intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(contentIntent);
            builder.setContentTitle(res.getString(R.string.notification_title_open_profile));
            builder.setAutoCancel(true);    // タップしたら削除
            manager.notify(NOTIFICATION_INTENT, builder.build());
            break;
        case R.id.button_remove:
            manager.cancel(NOTIFICATION_ICON_ONLY);
            manager.cancel(NOTIFICATION_TEXT);
            manager.cancel(NOTIFICATION_CUSTOM);
            manager.cancel(NOTIFICATION_INTENT);
            break;
        }
    }
}
