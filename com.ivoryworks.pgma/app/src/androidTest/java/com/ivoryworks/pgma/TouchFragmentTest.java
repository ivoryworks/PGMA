package com.ivoryworks.pgma;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TouchFragmentTest {
    private UiDevice mDevice;
    private int mWidth;
    private int mHeight;

    @Before
    public void setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mWidth = mDevice.getDisplayWidth();
        mHeight = mDevice.getDisplayHeight();

        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);

        // open Navigation drawer
        Tools.openNavigationDrawer(mDevice);
        try {
            mDevice.findObject(new UiSelector().text("Touch")).clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testUpDown() {
        mDevice.click(mWidth / 2, mHeight / 2);
    }

    @Test
    public void testSwipe() {
        mDevice.swipe(mWidth / 2, mHeight / 2, mWidth / 2 + 100, mHeight / 2, 30);

    }
}
