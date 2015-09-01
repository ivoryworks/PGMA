# uiautomator memo
## 複雑なスワイプ
複数の頂点を与えると順に辿る。

    Point[] segments = new Point[3];
    segments[0] = new Point(120, 425);
    segments[1] = new Point(120, 910);
    segments[2] = new Point(600, 910);
    mDevice.swipe(segments, SWIPE_STEPS);

## ロングプレスする
UiObjectのlongClick()で事足りない時。

    mDevice.swipe(x, y, x, y, 400);

## SeekBarを動かす
マイナス方向へ動かす。  
現在の位置から増減させる操作はできなさそう。

    UiSelector seekBar = new UiSelector().className(SeekBar.class.getName());
    int startX = mDevice.findObject(seekBar).getBounds().centerX();
    int startY = mDevice.findObject(seekBar).getBounds().centerY();
    int endY = startY;
    mDevice.swipe(startX, startY, startX-100, endY, 10);

## オブジェクトの中心座標を得る
    UiObject seekBar = mDevice.findObject( UiSelector().className(SeekBar.class.getName()));
    int x = seekBar.getBounds().centerX();
    int y = seekBar.getBounds().centerY();

## 5秒待つ

    mDevice.waitForIdle(5000);

※これ待てない（未解決）

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

## コマンドラインからのテスト実行
    adb shell am instrument -w com.ivoryworks.pgma.test/android.support.test.runner.AndroidJUnitRunner

android.util.AndroidException: INSTRUMENTATION_FAILED:となる場合、以下実行で出力されるリストを確認する事。存在しなければ実行できない。

    $ adb shell pm list instrumentation

## minSDKbバージョンが17以下のプロジェクトでuiautomatorを使いたい
テスト向けのAndroidManifest.xmlを、テストディレクトリ配下に置く。

    <?xml version="1.0" encoding="utf-8"?>
    <manifest
        xmlns:tools="http://schemas.android.com/tools"
        package="com.ivoryworks.pgma.test">

        <uses-sdk tools:overrideLibrary="android.support.test.uiautomator.v18"/>
    </manifest>
