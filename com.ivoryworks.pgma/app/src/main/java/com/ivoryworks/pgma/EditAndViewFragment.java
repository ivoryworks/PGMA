package com.ivoryworks.pgma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EditAndViewFragment extends Fragment {

    public static String TAG = EditAndViewFragment.class.getSimpleName();
    private TextView mLogText;

    public static EditAndViewFragment newInstance() {
        return new EditAndViewFragment();
    }

    public EditAndViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_and_view, container, false);

        return view;
    }
}
