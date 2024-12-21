-keepclassmembers class com.android.geto.framework.shizuku.UserService {
    public <init>(...);
}

# Need to keep PackageManager AIDL files as it is
-keep class android.content.pm.IPackageManager { *; }
-keep class android.content.pm.IPackageManager$Default { *; }
-keep class android.content.pm.IPackageManager$Stub { *; }
-keep class android.content.pm.IPackageManager$Stub$Proxy { *; }

# Need to keep ActivityManager AIDL files as it is
-keep class android.app.IActivityManager { *; }
-keep class android.app.IActivityManager$Default { *; }
-keep class android.app.IActivityManager$Stub { *; }
-keep class android.app.IActivityManager$Stub$Proxy { *; }