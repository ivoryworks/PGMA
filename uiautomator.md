# uiautomator memo
## CheckBox�̏�Ԃ�m��

    UiSelector chkBox = new UiSelector().className(CheckedBox.class.getName()).instance(0);
    if (mDevice.findObject(chkBox).isChecked()) {
        ...
    }

## minSDKb�o�[�W������17�ȉ��̃v���W�F�N�g��uiautomator���g������
�e�X�g������AndroidManifest.xml���A�e�X�g�f�B���N�g���z���ɒu���B

    <?xml version="1.0" encoding="utf-8"?>
    <manifest
        xmlns:tools="http://schemas.android.com/tools"
        package="com.ivoryworks.pgma.test">

        <uses-sdk tools:overrideLibrary="android.support.test.uiautomator.v18"/>
    </manifest>
