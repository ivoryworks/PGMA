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

import java.lang.reflect.Type;
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

        // assetに持つアプリ独自フォント(Roboto)
        String[] fontNameArray = getActivity().getBaseContext()
                .getResources().getStringArray(R.array.font_names);
        for (String fontName : fontNameArray) {
            FontItem font = new FontItem();
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontName + ".ttf");
            font.setTypeface(tf);
            font.setSampleText(fontName);
            list.add(font);
        }

        // frameworksフォント
        Typeface[] fontFamilys = {Typeface.DEFAULT, Typeface.DEFAULT_BOLD, Typeface.MONOSPACE,
                Typeface.SANS_SERIF, Typeface.SERIF};
        String[] fontFamilyNames = {"DEFAULT", "DEFAULT_BOLD", "MONOSPACE", "SANS_SERIF", "SERIF"};
        int[] fontStyles = {Typeface.NORMAL, Typeface.BOLD, Typeface.BOLD_ITALIC, Typeface.ITALIC};
        String[] fontStyleNames = {"NORMAL", "BOLD", "BOLD_ITALIC", "ITALIC"};

        int ffIndex = 0;
        for (Typeface ff : fontFamilys) {
            int fsIndex = 0;
            for (int fs : fontStyles) {
                FontItem font = new FontItem();
                font.setTypeface(ff, fs);
                font.setSampleText(fontFamilyNames[ffIndex] + " + " + fontStyleNames[fsIndex++]);
                list.add(font);
            }
            ffIndex++;
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
            TextView fontName = (TextView)convertView.findViewById(R.id.font_name);
            fontName.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            fontName.setText(mFontList.get(position).getSampleText());

            TextView sampleText = (TextView)convertView.findViewById(R.id.sample_text);
            sampleText.setTypeface(mFontList.get(position).getFontFamily(),
                    mFontList.get(position).getFontStyle());
            sampleText.setText(R.string.sample_text);
            return convertView;
        }
    }

    private class FontItem {
        private long mId;
        private String mFontName;
        private String mSampleText;
        private Typeface mFontFamily;
        private int mFontStyle;

        public long getId() {
            return mId;
        }

        public void setId(long id) {
            mId = id;
        }

        public String getFontName() {
            return mFontName;
        }

        public void setFontName(String name) {
            mFontName = name;
        }

        public String getSampleText() {
            return mSampleText;
        }

        public void setSampleText(String text) {
            mSampleText = text;
        }

        public Typeface getFontFamily() {
            return mFontFamily;
        }

        public int getFontStyle() {
            return mFontStyle;
        }


        public void setTypeface(Typeface fontFamily) {
            mFontFamily = fontFamily;
        }
        public void setTypeface(Typeface fontFamily, int fontStyle) {
            mFontFamily = fontFamily;
            mFontStyle = fontStyle;
        }
    }
}
