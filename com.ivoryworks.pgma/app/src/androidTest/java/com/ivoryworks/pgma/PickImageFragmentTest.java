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

        // open Navigation drawer
        Tools.openNavigationDrawer(mDevice);
        try {
            mDevice.findObject(new UiSelector().text("Pick image")).clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testButtonExists() {
        UiSelector share = new UiSelector().description("share");
        UiSelector camera = new UiSelector().description("camera");
        UiSelector gallery = new UiSelector().description("gallery");

        assertTrue(mDevice.findObject(share).exists());
        assertTrue(mDevice.findObject(camera).exists());
        assertTrue(mDevice.findObject(gallery).exists());

        try {
            assertTrue(mDevice.findObject(share).isEnabled());
            assertTrue(mDevice.findObject(camera).isEnabled());
            assertTrue(mDevice.findObject(gallery).isEnabled());
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShareFail() {
        try {
            mDevice.findObject(new UiSelector().description("share")).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(mDevice.findObject(new UiSelector().text("Share file can not be found.")).exists());
    }
}
