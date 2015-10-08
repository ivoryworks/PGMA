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

public class EditAndViewFragment extends Fragment implements TextWatcher {

    public static String TAG = EditAndViewFragment.class.getSimpleName();
    EditText mEditText;
    TextView mTextView;

    public static EditAndViewFragment newInstance() {
        return new EditAndViewFragment();
    }

    public EditAndViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_and_view, container, false);

        mTextView = (TextView) view.findViewById(R.id.textView);

        mEditText = (EditText) view.findViewById(R.id.editText);
        mEditText.addTextChangedListener(this);

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTextView.setText(mEditText.getText());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
