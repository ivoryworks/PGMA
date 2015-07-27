package com.ivoryworks.pgma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FrameworkDrawalbeAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<FrameworkDrawable> mDrawableList;

    public FrameworkDrawalbeAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDrawableList(ArrayList<FrameworkDrawable> drawableList) {
        mDrawableList = drawableList;
    }

    @Override
    public int getCount() {
        return mDrawableList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDrawableList.get(position).getResId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.listitem_drawable_info, parent, false);

        ((TextView)convertView.findViewById(R.id.res_name)).setText(mDrawableList.get(position).getResName());
        ((ImageView)convertView.findViewById(R.id.thumbnail)).setImageResource(mDrawableList.get(position).getResId());

        return convertView;
    }
}
