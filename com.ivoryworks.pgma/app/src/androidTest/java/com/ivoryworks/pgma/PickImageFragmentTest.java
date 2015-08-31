package com.ivoryworks.pgma;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class PickImageFragmentTest {
    private UiDevice mDevice;

    @Before
    public void setup() {
        // Initialize
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);
    }

    @Test
    public void testShareFail() {
        // open Navigation drawer
        mDevice.findObject(By.desc("Navigate up")).click();
    }
}
