package com.ivoryworks.pgma;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo = null;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMainActivity() {
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.waitForActivity(MainActivity.class);
        solo.clickOnActionBarHomeButton();

        ListView listView = (ListView) solo.getView(R.id.navigation_drawer);
        View SwitchOrganizations = listView.getChildAt(4);
        solo.clickOnView(SwitchOrganizations);
    }
}