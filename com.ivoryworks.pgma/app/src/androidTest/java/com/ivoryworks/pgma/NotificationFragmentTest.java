package com.ivoryworks.pgma;

import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class NotificationFragmentTest {
    private UiDevice mDevice;
    private UiSelector mRemoveBtn;

    @Before
    public void setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mRemoveBtn = new UiSelector().resourceId("com.ivoryworks.pgma.debug:id/button_remove");

        Tools.goToHome(mDevice);
        Tools.startPGMA(mDevice);

        // open Navigation drawer
        Tools.clickNavigationDrawerItem(mDevice, "Notification");

    }

    @Test
    public void testIconNotification() {
        UiSelector selector = new UiSelector().resourceId("com.ivoryworks.pgma.debug:id/button_simple");
        try {
            mDevice.findObject(selector).click();
//            mDevice.openNotification();
            Tools.openNotification(mDevice);
            UiSelector icon = new UiSelector().className(ImageView.class.getName()).resourceId("android:id/icon");
            Assert.assertTrue(mDevice.findObject(icon).exists());

            mDevice.pressBack();    // close Notification
            mDevice.findObject(mRemoveBtn).click(); // Notification消去
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testTextNotification() {
        UiSelector selector = new UiSelector().resourceId("com.ivoryworks.pgma.debug:id/button_text");
        try {
            mDevice.findObject(selector).click();
            Tools.openNotification(mDevice);
            // Icon
            UiSelector icon = new UiSelector().className(ImageView.class.getName()).resourceId("android:id/icon");
            Assert.assertTrue(mDevice.findObject(icon).exists());
            // Title
            UiSelector title = new UiSelector().className(TextView.class.getName()).resourceId("android:id/title");
            Assert.assertTrue(mDevice.findObject(title).exists());
            Assert.assertTrue(mDevice.findObject(title).getText().equals("Notification title"));
            // Text
            UiSelector text = new UiSelector().className(TextView.class.getName()).resourceId("android:id/text2");
            Assert.assertTrue(mDevice.findObject(text).exists());
            Assert.assertTrue(mDevice.findObject(text).getText().equals("Text message"));
            // Sub Text
            UiSelector subText = new UiSelector().className(TextView.class.getName()).resourceId("android:id/text");
            Assert.assertTrue(mDevice.findObject(subText).exists());
            Assert.assertTrue(mDevice.findObject(subText).getText().equals("sub text"));
            // Sub Text
            UiSelector info = new UiSelector().className(TextView.class.getName()).resourceId("android:id/info");
            Assert.assertTrue(mDevice.findObject(info).exists());
            Assert.assertTrue(mDevice.findObject(info).getText().equals("Info message"));

            mDevice.pressBack();    // close Notification
            mDevice.findObject(mRemoveBtn).click(); // Notification消去
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
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
