package com.ivoryworks.pgma;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FontsFragment extends Fragment {

    public static String TAG = FontsFragment.class.getSimpleName();

    public static FontsFragment newInstance() {
        return new FontsFragment();
    }

    public FontsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fonts, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        ArrayList<FontItem> list = new ArrayList<>();

        String[] fontNameArray = getActivity().getBaseContext()
                .getResources().getStringArray(R.array.font_names);
        for (String fontName : fontNameArray) {
            FontItem font = new FontItem();
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontName + ".ttf");
            font.setTypeface(tf);
            font.setSampleText(fontName);
            list.add(font);
        }

        Typeface[] typefaces = {Typeface.DEFAULT, Typeface.DEFAULT_BOLD, Typeface.MONOSPACE, Typeface.SANS_SERIF, Typeface.SERIF};
        String[] typefaceNames = {"Typeface.DEFAULT", "Typeface.DEFAULT_BOLD", "Typeface.MONOSPACE", "Typeface.SANS_SERIF", "Typeface.SERIF"};
        int idx = 0;
        for (Typeface tf : typefaces) {
            FontItem font = new FontItem();
            font.setTypeface(tf);
            font.setSampleText(typefaceNames[idx++]);
            list.add(font);
        }

        FontsAdapter adapter = new FontsAdapter(getActivity());
        adapter.setFontList(list);
        listView.setAdapter(adapter);

        return view;
    }

    public class FontsAdapter extends BaseAdapter {

        LayoutInflater mLayoutInflater;
        ArrayList<FontItem> mFontList;

        public FontsAdapter(Context context) {
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setFontList(ArrayList<FontItem> fontList) {
            mFontList = fontList;
        }

        @Override
        public int getCount() {
            return mFontList.size();
        }

        @Override
        public Object getItem(int position) {
            return mFontList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mFontList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mLayoutInflater.inflate(R.layout.listitem_font, parent,false);
            TextView fontName = (TextView)convertView.findViewById(R.id.sample_text);
            fontName.setTypeface(mFontList.get(position).getTypeface());
            fontName.setText(mFontList.get(position).getSampleText());
            return convertView;
        }
    }

    private class FontItem {
        long mId;
        String mSampleText;
        Typeface mTypeface;

        public long getId() {
            return mId;
        }

        public void setId(long id) {
            mId = id;
        }

        public String getSampleText() {
            return mSampleText;
        }

        public void setSampleText(String text) {
            mSampleText = text;
        }

        public Typeface getTypeface() {
            return mTypeface;
        }

        public void setTypeface(Typeface typeface) {
            mTypeface = typeface;
        }
    }
}
