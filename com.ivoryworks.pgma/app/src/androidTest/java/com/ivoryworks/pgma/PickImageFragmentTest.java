package com.ivoryworks.pgma;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class PickImageFragmentTest {
    private UiDevice mDevice;

    @Before
    public void startPickImageFragmentTest() {
        // Initialize
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start home screen
        mDevice.pressHome();
    }
}
