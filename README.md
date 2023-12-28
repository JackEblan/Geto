![Geto](docs/images/geto-splash.jpg "Geto")

Geto
==================
[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" alt="Get it on F-Droid" height="80">](https://f-droid.org/en/packages/com.android.geto/)

The architecture and modularization by this project is inspired from [Now in Android](https://github.com/android/nowinandroid)

The only reason I created this app is to turn off that damn Developer Options when using a banking
app. The only annoying thing about it is you have to go to the Settings app. When you turn off that
switch button, your Developer Options configurations will be reset to default. The good thing is
that when you modify your settings through its Shared Preferences, you won't lose all your settings
once the Developer Options is modified. So basically, you have to grant this app with **android.permission.WRITE_SECURE_SETTINGS** in order for it to modify your Settings values.

## Screenshots

![Screenshot showing User App List screen, User App Settings and Add Setting Dialog](docs/images/screenshots.jpg "Screenshot showing For You screen, Interests screen and Topic detail screen")

# Installation

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

# Geto Settings

Here are some settings you can experiment with. Simply choose an app from the list, click the **Add
Settings** button, and proceed to fill in the provided options. Refer to the examples below for
guidance. If you have unique settings that work well on different devices, please reach out to me so
that I can incorporate them into this list. Your contributions are appreciated!

To apply your settings to a specific app, click **Launch App**. Remember to reset your preferences
after exiting the app by clicking the **Refresh** button.

1. **Hide developer options detection in any apps:**
    - Type **Global**
    - Settings label **Hide Developer Options**
    - Settings key **development_settings_enabled**
    - Settings value on launch **0**
    - Settings value on revert **1**

# License

**Geto** is licensed under the GNU General Public License v3.0. See the [license](LICENSE) for more
information.
