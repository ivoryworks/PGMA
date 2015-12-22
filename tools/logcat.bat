@ECHO OFF
IF "%1"=="" (
    SET PREFIX=""
) ELSE (
    SET PREFIX=%1_
)
SET logfilename=%PREFIX%%date:~-10,4%%date:~-5,2%%date:~-2,2%_%time:~-11,2%%time:~-8,2%%time:~-5,2%.log
adb logcat -c
adb logcat -v time > %logfilename%
