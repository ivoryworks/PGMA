package com.ivoryworks.pgma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class PinchFragment extends Fragment {

    public static String TAG = PinchFragment.class.getSimpleName();

    public static PinchFragment newInstance() {
        return new PinchFragment();
    }

    public PinchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pinch, container, false);

        return view;
    }
}
