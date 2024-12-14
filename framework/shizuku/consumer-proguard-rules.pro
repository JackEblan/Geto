-keepclassmembers class com.android.geto.framework.shizuku.UserService {
    public <init>(...);
}

# Need to keep AIDL files as it is
-keep class android.content.pm.IPackageManager { *; }
-keep class android.content.pm.IPackageManager$Default { *; }
-keep class android.content.pm.IPackageManager$Stub { *; }
-keep class android.content.pm.IPackageManager$Stub$Proxy { *; }