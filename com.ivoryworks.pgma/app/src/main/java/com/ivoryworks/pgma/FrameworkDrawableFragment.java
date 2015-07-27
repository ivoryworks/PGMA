package com.ivoryworks.pgma;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FrameworkDrawableFragment extends Fragment implements AbsListView.OnItemClickListener {

    public static String TAG = FrameworkDrawableFragment.class.getSimpleName();
    private AbsListView mListView;
    private FrameworkDrawalbeAdapter mAdapter;

    public static FrameworkDrawableFragment newInstance() {
        return new FrameworkDrawableFragment();
    }

    public FrameworkDrawableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new FrameworkDrawalbeAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);

        ArrayList<FrameworkDrawable> list = new ArrayList<>();

        Field[] drawables = android.R.drawable.class.getFields();
        for (Field f : drawables) {
            try {
                FrameworkDrawable item = new FrameworkDrawable();
                item.setResId(getActivity().getResources().getIdentifier(f.getName(), "drawable", "android"));
                item.setResName("R.drawable." + f.getName());
                list.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mAdapter.setDrawableList(list);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }
}
