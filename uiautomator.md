# uiautomator memo
## 画面キャプチャ

    String fileName = "UiAutomator_" + System.currentTimeMillis() + ".png";
     File filePath = new File("/mnt/sdcard/Pictures/" + fileName);
     mDevice.takeScreenshot(filePath);

## ViewPagerをフリックする

    int displayWidth = mDevice.getDisplayWidth();
    int displayHeight = mDevice.getDisplayHeight();
    // Left to Right
    mDevice.swipe((int)(displayWidth * .25), displayHeight / 2,
                        displayWidth, displayHeight / 2, SWIPE_STEPS);
    // Right to Left
    mDevice.swipe((int)(displayWidth * .75), displayHeight / 2,
                        0, displayHeight / 2, SWIPE_STEPS);


## 端末の向きを変える

    mDevice.setOrientationRight();
    mDevice.setOrientationLeft();
    mDevice.setOrientationNatural();    // 自然な状態へ

## ナンバーピッカーまわす
変数stepにより上に回すか下に回すかコントロールする。

    UiObject picker = mDevice.findObject(new UiSelector().className(NumberPicker.class.getName()));
    mDevice.swipe(picker.getBounds().centerX(), picker.getBounds().centerY(),
            picker.getBounds().centerX(), picker.getBounds().centerY() + step, 30);

## 複雑なスワイプ
複数の頂点を与えると順に辿る。

    Point[] segments = new Point[3];
    segments[0] = new Point(120, 425);
    segments[1] = new Point(120, 910);
    segments[2] = new Point(600, 910);
    mDevice.swipe(segments, 30);

## ロングプレスする
UiObjectのlongClick()で事足りない時。

    mDevice.swipe(x, y, x, y, 400);

## SeekBarを動かす
マイナス方向へ動かす。  
現在の位置から増減させる操作は難しそう。

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

    SystemClock.sleep(5000);

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

## minSDKbバージョンが17以下のプロジェクトでuiautomatorを使う
テスト向けのAndroidManifest.xmlを、テストディレクトリ配下に置く。

    <?xml version="1.0" encoding="utf-8"?>
    <manifest
        xmlns:tools="http://schemas.android.com/tools"
        package="com.example.android.test">

        <uses-sdk tools:overrideLibrary="android.support.test.uiautomator.v18"/>
    </manifest>

## 主要クラス
### UiDevice
　テストを実行するデバイスそのもの。「ホーム」や「バック」キーの押下や、端末の向きを変更するメソッドが用意されている。

### UiSelector
　操作する対象のUIコンポーネントを特定するための走査条件を表す。

### UiObject
　操作するUIコンポーネントそのもの。
