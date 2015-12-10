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
    private TabLayout mTabLayout;

    public static String TAG = TabFragment.class.getSimpleName();
    private int TAB_STYLE_SIMPLE = 1;
    private int TAB_STYLE_ICON = 2;
    private int mLastStyle = TAB_STYLE_SIMPLE;

    public static TabFragment newInstance() {
        return new TabFragment();
    }

    public TabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab, container, false);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);

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
                mTabLayout.setTabMode(isChecked ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
            }
        });

        Switch swGravityCenter = (Switch) mView.findViewById(R.id.switch_gravity_center);
        swGravityCenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTabLayout.setTabGravity(isChecked ? TabLayout.GRAVITY_CENTER : TabLayout.GRAVITY_FILL);
            }
        });

        Button buttonAdd = (Button) mView.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTab();
            }
        });

        Button buttonRemove = (Button) mView.findViewById(R.id.button_remove);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTab();
            }
        });

        setTabSimple(3);
        return mView;
    }

    private void setTabSimple(int tabNum) {
        mLastStyle = TAB_STYLE_SIMPLE;
        mTabLayout.removeAllTabs();
        for (int i = 0; i < tabNum; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText("tab " + i));
        }
    }

    private void setTabIcon(int tabNum) {
        mLastStyle = TAB_STYLE_ICON;
        mTabLayout.removeAllTabs();
        for (int i = 0; i < tabNum; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText("tab " + i).setIcon(R.drawable.octocat));
        }
    }

    private void addTab() {
        if (mLastStyle == TAB_STYLE_ICON) {
            setTabIcon(mTabLayout.getTabCount() + 1);
        } else {
            setTabSimple(mTabLayout.getTabCount() + 1);
        }
    }

    private void removeTab() {
        int tabCnt = mTabLayout.getTabCount() - 1;
        if (tabCnt > 0) {
            if (mLastStyle == TAB_STYLE_ICON) {
                setTabIcon(tabCnt);
            } else {
                setTabSimple(tabCnt);
            }
        }
    }
}
