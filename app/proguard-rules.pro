# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Hilt components
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep DataStore
-keep class androidx.datastore.** { *; }
