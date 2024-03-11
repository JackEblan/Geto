<div align = "center">

<img width="" src="app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp" alt="Geto" align="center">

# Geto

### Apply custom settings to your apps

![GitHub Release](https://img.shields.io/github/v/release/JackEblan/Geto?style=for-the-badge)
![GitHub License](https://img.shields.io/github/license/JackEblan/Geto?style=for-the-badge)
![F-Droid Version](https://img.shields.io/f-droid/v/com.android.geto?style=for-the-badge)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/JackEblan/Geto/total?style=for-the-badge)

[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" alt="Get it on F-Droid" height="80">](https://f-droid.org/en/packages/com.android.geto/)

</div>

<div align ="left">

About The Project
==================

The architecture and modularization of this project are inspired
by [Now in Android](https://github.com/android/nowinandroid)

The only reason I created this app is to turn off that damn Developer Options when using a banking
app. The only annoying thing about it is you have to go to the Settings app. When you turn off that
switch button, your Developer Options configurations will be reset to default. The good thing is
that when you modify your settings through its Shared Preferences, you won't lose all your settings
once the Developer Options is modified. So basically, you have to grant this app
with `android.permission.WRITE_SECURE_SETTINGS` in order for it to modify your Settings values.

I want your feedback about the upcoming features in the [Discussions](https://github.com/JackEblan/Geto/discussions)

# Screenshots

<pre>
<img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/1.jpg" width="120" height="280" />  <img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/2.jpg" width="120" height="280" />  <img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/3.jpg" width="120" height="280" /> <img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/4.jpg" width="120" height="280" /> <img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/5.jpg" width="120" height="280" /> <img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/6.jpg" width="120" height="280" /> <img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/7.jpg" width="120" height="280" /> <img src="https://github.com/JackEblan/Geto/blob/master/fastlane/metadata/android/en-US/images/phoneScreenshots/8.jpg" width="120" height="280" />
</pre>

# Installation

Download the Geto APK
from [Releases](https://github.com/JackEblan/Geto/releases). You
can also download the app-debug APK
from [GitHub Actions](https://github.com/JackEblan/Geto/actions).

This guide is only for Android 11+ without using computer.
For Android 10 and below, [This Youtube tutorial](https://www.youtube.com/watch?v=k4k297qItY4) can
help you.

1. **Install Shizuku and aShell:**
    - Download and
      install [Shizuku](https://play.google.com/store/apps/details?id=moe.shizuku.privileged.api&hl=en&gl=US)
      and [aShell](https://play.google.com/store/apps/details?id=in.sunilpaulmathew.ashell&hl=en&gl=US&pli=1)
      from the Google Play Store.

2. **Set Up Shizuku:**
    - Refer to the Shizuku documentation or follow the
      guidelines [here](https://shizuku.rikka.app/guide/setup/) to properly configure Shizuku.

3. **Open aShell:**
    - Launch aShell on your device. If the app does not show any errors then Shizuku service is
      working properly.

4. **Grant Necessary Permissions:**
    - In the aShell command box, enter the following command and press Enter:
      ```bash
      pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS
      ```
    - Ensure that no errors are reported during the execution of the command.

5. **Start Using Geto:**
    - If the permission is granted successfully, you can now start using Geto without any issues.

# License

**Geto** is licensed under the GNU General Public License v3.0. See the [license](LICENSE) for more
information.
</div>
