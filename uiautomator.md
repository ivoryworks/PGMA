# uiautomator memo
## スクロールして探す
    UiScrollable scroller = new UiScrollable(new UiSelector().className(ListView.class.getName()));
    scroller.setAsVerticalList();   // スクロール方向指定
    UiObject target = scroller.getChildByText(new UiSelector().className(TextView.class.getName()), 探索文字列);
    mDevice.findObject(target).click();

## CheckBoxの状態を知る

    UiSelector chkBox = new UiSelector().className(CheckedBox.class.getName()).instance(0);
    if (mDevice.findObject(chkBox).isChecked()) {
        ...
    }

## minSDKbバージョンが17以下のプロジェクトでuiautomatorを使いたい
テスト向けのAndroidManifest.xmlを、テストディレクトリ配下に置く。

    <?xml version="1.0" encoding="utf-8"?>
    <manifest
        xmlns:tools="http://schemas.android.com/tools"
        package="com.ivoryworks.pgma.test">

        <uses-sdk tools:overrideLibrary="android.support.test.uiautomator.v18"/>
    </manifest>
