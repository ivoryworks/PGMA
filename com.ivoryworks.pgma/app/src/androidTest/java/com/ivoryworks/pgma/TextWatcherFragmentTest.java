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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TextWatcherFragmentTest {
    private UiDevice mDevice;

    @Before
    public void setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);

        // open Navigation drawer
        Tools.openNavigationDrawer(mDevice);
        UiScrollable scroller = new UiScrollable(new UiSelector().className(ListView.class.getName()));
        scroller.setAsVerticalList();   // スクロール方向指定
        try {
            UiObject target = scroller.getChildByText(new UiSelector().className(TextView.class.getName()), "TextWatcher");
            target.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testInputText() {
        UiSelector editText = new UiSelector().className(EditText.class.getName());
        try {
            mDevice.findObject(editText).setText("Test message.");
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SystemClock.sleep(5000);
    }
}
