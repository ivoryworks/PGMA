IF "%1"=="" (
    SET PREFIX=""
) ELSE (
    SET PREFIX=%1_
)
SET filename=%PREFIX%%date:~-10,4%%date:~-5,2%%date:~-2,2%_%time:~-11,2%%time:~-8,2%%time:~-5,2%.png

adb shell screencap -p /sdcard/%filename%
adb pull /sdcard/%filename%
adb shell rm /sdcard/%filename%
