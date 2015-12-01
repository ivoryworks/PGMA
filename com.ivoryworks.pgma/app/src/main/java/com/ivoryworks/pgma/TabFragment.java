package com.ivoryworks.pgma;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
                setTabSimple();
            }
        });

        Button btnIcon = (Button) mView.findViewById(R.id.button_icon);
        btnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabIcon();
            }
        });

        setTabSimple();
        return mView;
    }

    private void setTabSimple() {
        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("tab 3"));
    }

    private void setTabIcon() {
        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("tab 1").setIcon(R.drawable.octocat));
        tabLayout.addTab(tabLayout.newTab().setText("tab 2").setIcon(R.drawable.octocat));
        tabLayout.addTab(tabLayout.newTab().setText("tab 3").setIcon(R.drawable.octocat));
    }
}
