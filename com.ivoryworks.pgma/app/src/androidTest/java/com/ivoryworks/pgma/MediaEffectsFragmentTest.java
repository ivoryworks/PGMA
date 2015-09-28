package com.ivoryworks.pgma;

import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MediaEffectsFragmentTest {
    private UiDevice mDevice;

    @Before
    public void setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);

        // open Navigation drawer
        Tools.openNavigationDrawer(mDevice);
        clickListTextAndWait("Media Effects");
    }

    @Test
    public void testOrientation() {
        try {
            SystemClock.sleep(2000);
            mDevice.setOrientationLeft();
            SystemClock.sleep(2000);
            mDevice.setOrientationRight();
            SystemClock.sleep(2000);
            mDevice.setOrientationNatural();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SystemClock.sleep(5000);
    }

    private boolean clickListTextAndWait(String text) {
        try {
            findTextInListView(text).clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            return false;
        }
        return true;
    }

    private UiObject findTextInListView(String itemName) throws UiObjectNotFoundException {
        UiScrollable scroller = new UiScrollable(new UiSelector().className(ListView.class.getName()));
        // スクロール方向指定
        scroller.setAsVerticalList();
        return scroller.getChildByText(new UiSelector().className(TextView.class.getName()), itemName);
    }
}
