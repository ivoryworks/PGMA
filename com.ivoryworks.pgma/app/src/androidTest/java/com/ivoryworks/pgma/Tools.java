package com.ivoryworks.pgma;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.ListView;
import android.widget.TextView;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class Tools {
    public static final int LAUNCH_TIMEOUT = 5000;
    public static final int SWIPE_STEP = 30;
    public static final String PGMA_PACKAGE = "com.ivoryworks.pgma.debug";

    public static void startPGMA(UiDevice device) {
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PGMA_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(PGMA_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    public static void goToHome(UiDevice device) {
        // Start home screen
        device.pressHome();
        final String launcherPackage = Tools.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), Tools.LAUNCH_TIMEOUT);
    }

    public static void openNavigationDrawer(UiDevice device) {
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        device.swipe(0, height / 2, (int) (width * .75), height / 2, SWIPE_STEP);
    }

    public static void clickNavigationDrawerItem(UiDevice device, String name) {
        Tools.openNavigationDrawer(device);
        UiScrollable scroller = new UiScrollable(new UiSelector().className(ListView.class.getName()));
        scroller.setAsVerticalList();   // スクロール方向指定
        try {
            UiObject target = scroller.getChildByText(new UiSelector().className(TextView.class.getName()), name);
            target.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public static void closeNavigationDrawer(UiDevice device) {
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        device.swipe((int) (width * .75), height / 2, 0, height / 2, SWIPE_STEP);
    }

    public static String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    public static void openNotification(UiDevice device) {
        int displayWidth = device.getDisplayWidth();
        int displayHeight = device.getDisplayHeight();
        device.swipe(displayWidth/2, 0, displayWidth/2, displayHeight/2, SWIPE_STEP);
    }

    public static void orientation(UiDevice device) throws RemoteException {
        device.setOrientationLeft();
        device.setOrientationRight();
        device.setOrientationNatural();
    }
}
