package com.ivoryworks.pgma;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import junit.framework.Assert;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo = null;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMainActivity() {
        // MainActivityの起動
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.waitForActivity(MainActivity.class);

        // RobotiumPunchballへの遷移
        solo.clickOnActionBarHomeButton();  // NavigationDrawerを開く
        ListView listView = (ListView) solo.getView(R.id.navigation_drawer);
        View SwitchOrganizations = listView.getChildAt(4);  // Todo:magic number!!!
        solo.clickOnView(SwitchOrganizations);  // RobotiumPunchballメニューをクリック

        // Toast表示ボタンが無効化されている事を確認する
        Button btnToast = (Button) solo.getView(R.id.btnToast);
        Assert.assertFalse(btnToast.isEnabled());

        // EditTextに入力した文字列をToast表示する
        String toastMessage = "Toast test";
        solo.enterText((EditText) solo.getView(R.id.editToastText), toastMessage);
        solo.clickOnView(solo.getView(R.id.btnToast));  // Toastを表示するボタンをクリック
        Assert.assertTrue(solo.searchText(toastMessage));   // 入力した文字列がToast表示されるか確認
    }
}