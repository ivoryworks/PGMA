package com.ivoryworks.pgma;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class PickImageFragmentTest {
    private UiDevice mDevice;
    private int mLoopCnt;

    @Before
    public void setup() {
        // Initialize
        mLoopCnt = Integer.valueOf(System.getProperty("loop", "1"));
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);
    }

    @Test
    public void testShareFail() {
        // open Navigation drawer
        Tools.openNavigationDrawer(mDevice);

        try {
            mDevice.findObject(new UiSelector().text("Pick image")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().description("share")).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(mDevice.findObject(new UiSelector().text("Share file can not be found.")).exists());

        SystemClock.sleep(5000);
    }
}
