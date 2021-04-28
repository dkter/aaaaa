.\gradlew installDebug
if ($?) {
    adb shell am start -n io.github.dkter.aaaaa/io.github.dkter.aaaaa.MainActivity
    Start-Sleep -Seconds 0.2
    adb shell 'logcat --pid=$(pidof -s io.github.dkter.aaaaa)'
}