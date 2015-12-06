package com.ivoryworks.pgma;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class TabFragment extends Fragment {

    private View mView;

    public static String TAG = TabFragment.class.getSimpleName();

    public static TabFragment newInstance() {
        return new TabFragment();
    }

    public TabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab, container, false);

        Button btnSimple = (Button) mView.findViewById(R.id.button_simple);
        btnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSimple(5);
            }
        });

        Button btnIcon = (Button) mView.findViewById(R.id.button_icon);
        btnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabIcon(3);
            }
        });

        Switch swScrollable = (Switch) mView.findViewById(R.id.switch_scrollable);
        swScrollable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
                tabLayout.setTabMode(isChecked ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
            }
        });

        setTabSimple(3);
        return mView;
    }

    private void setTabSimple(int tabNum) {
        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        for (int i = 0; i < tabNum; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("tab " + i));
        }
    }

    private void setTabIcon(int tabNum) {
        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        for (int i = 0; i < tabNum; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("tab " + i).setIcon(R.drawable.octocat));
        }
    }
}
