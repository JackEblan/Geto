# Keep the package name
-keeppackagenames class com.android.geto.domain.model.** { *; }

# Keep the enum classes and their members (values/names)
-keep enum com.android.geto.domain.model.** {
    *;
}