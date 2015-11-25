package com.ivoryworks.pgma;

import android.os.RemoteException;
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

import static junit.framework.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ImageRotateFragmentTest {
    private UiDevice mDevice;

    @Before
    public void setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);

        // open Navigation drawer
        Tools.clickNavigationDrawerItem(mDevice, "Image rotate");
    }

    @Test
    public void testOrientation() {
        try {
            Tools.orientation(mDevice);
        } catch (RemoteException e) {
            assertFalse(true);
        }
    }
}
