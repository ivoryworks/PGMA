package com.ivoryworks.pgma;

import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CanvasFragmentTest {
    private UiDevice mDevice;

    @Before
    public void setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);

        // open Navigation drawer
        Tools.clickNavigationDrawerItem(mDevice, "Canvas");
    }

    @Test
    public void testOrientation() {
        try {
            mDevice.setOrientationLeft();
            mDevice.setOrientationRight();
            mDevice.setOrientationNatural();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SystemClock.sleep(5000);
    }
}
