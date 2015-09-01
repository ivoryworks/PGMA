# uiautomator memo
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
