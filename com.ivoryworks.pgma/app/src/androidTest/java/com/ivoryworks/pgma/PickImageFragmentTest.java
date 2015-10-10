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
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class PickImageFragmentTest {
    private UiDevice mDevice;
    private int mLoopCnt;
    private String FILE_NOT_FOUND = "Share file can not be found.";
    private UiSelector mSelectorShare;
    private UiSelector mSelectorCamera;
    private UiSelector mSelectorGallery;
    private UiSelector mSelectorCamShutter;
    private UiSelector mSelectorCamDone;
    private UiSelector mSelectorCamReTake;
    private UiSelector mSelectorCamCancel;

    @Before
    public void setup() {
        // Initialize
        mLoopCnt = Integer.valueOf(System.getProperty("loop", "1"));
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mSelectorShare = new UiSelector().description("share");
        mSelectorCamera = new UiSelector().description("camera");
        mSelectorGallery = new UiSelector().description("gallery");
        mSelectorCamShutter = new UiSelector().resourceId("com.android.camera2:id/shutter_button");
        mSelectorCamDone = new UiSelector().resourceId("com.android.camera2:id/done_button");
        mSelectorCamReTake = new UiSelector().resourceId("com.android.camera2:id/retake_button");
        mSelectorCamCancel = new UiSelector().resourceId("com.android.camera2:id/cancel_button");

        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);

        // open Navigation drawer
        Tools.clickNavigationDrawerItem(mDevice, "Pick image");
    }

    @Test
    public void testButtonExists() {
        assertTrue(mDevice.findObject(mSelectorShare).exists());
        assertTrue(mDevice.findObject(mSelectorCamera).exists());
        assertTrue(mDevice.findObject(mSelectorGallery).exists());

        try {
            assertTrue(mDevice.findObject(mSelectorShare).isEnabled());
            assertTrue(mDevice.findObject(mSelectorCamera).isEnabled());
            assertTrue(mDevice.findObject(mSelectorGallery).isEnabled());
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShareFail() {
        try {
            mDevice.findObject(mSelectorShare).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(mDevice.findObject(new UiSelector().text(FILE_NOT_FOUND)).exists());
    }

    @Test
    public void testCamera() {
        try {
            mDevice.findObject(mSelectorCamera).clickAndWaitForNewWindow();
            mDevice.findObject(mSelectorCamShutter).click();
            mDevice.findObject(mSelectorCamDone).clickAndWaitForNewWindow();
            mDevice.findObject(mSelectorShare).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        assertFalse(mDevice.findObject(new UiSelector().text(FILE_NOT_FOUND)).exists());
        mDevice.pressBack();    // close share chooser
    }

    @Test
    public void testGallery() {
        try {
            mDevice.findObject(mSelectorGallery).clickAndWaitForNewWindow();
            UiSelector selectorImage = new UiSelector().className("android.widget.ImageView").instance(0);
            mDevice.findObject(selectorImage).clickAndWaitForNewWindow();
            mDevice.findObject(mSelectorShare).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        assertFalse(mDevice.findObject(new UiSelector().text(FILE_NOT_FOUND)).exists());
        mDevice.pressBack();    // close share chooser
    }

    @Test
    public void testShare() {
        try {
            mDevice.findObject(mSelectorCamera).clickAndWaitForNewWindow();
            mDevice.findObject(mSelectorCamShutter).click();
            mDevice.findObject(mSelectorCamDone).clickAndWaitForNewWindow();
            mDevice.findObject(mSelectorShare).click();
            mDevice.findObject(new UiSelector().text("PGMA")).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOrientation() {
        try {
            mDevice.setOrientationLeft();
            mDevice.setOrientationRight();
            mDevice.setOrientationNatural();
            SystemClock.sleep(2000);

            mDevice.findObject(mSelectorCamera).clickAndWaitForNewWindow();
            mDevice.findObject(mSelectorCamShutter).click();
            mDevice.findObject(mSelectorCamDone).clickAndWaitForNewWindow();
            SystemClock.sleep(2000);

            mDevice.setOrientationLeft();
            mDevice.setOrientationRight();
            mDevice.setOrientationNatural();
            SystemClock.sleep(2000);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
