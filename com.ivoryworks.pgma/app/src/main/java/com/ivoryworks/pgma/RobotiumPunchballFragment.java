package com.ivoryworks.pgma;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RobotiumPunchballFragment extends Fragment implements View.OnClickListener {

    public static String TAG = "RobotiumPunchballFragment";
    Button mToastButton;
    Toast mToast;
    EditText mToastText;

    public static RobotiumPunchballFragment newInstance() {
        return new RobotiumPunchballFragment();
    }

    public RobotiumPunchballFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robotium_punchball, container, false);

        mToastButton = (Button) view.findViewById(R.id.btnToast);
        mToastButton.setOnClickListener(this);
        mToastButton.setEnabled(false);

        mToastText = (EditText) view.findViewById(R.id.editToastText);
        mToastText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mToastButton.setEnabled(s.toString().length() > 0);
            }
        });

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
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnToast:
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), mToastText.getText(), Toast.LENGTH_SHORT);
            mToast.show();
            break;
        }
    }
}
