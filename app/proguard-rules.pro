# Xposed / LSPosed 模块核心入口与组件
-keep class com.hmodule.MainHook { *; }
-keep class com.hmodule.MainActivity { *; }
-keep class com.hmodule.ModuleApplication { *; }
-keep class com.hmodule.config.ConfigurationProvider { *; }

# libxposed 运行时
-keep class io.github.libxposed.** { *; }
-dontwarn io.github.libxposed.**

# R8 优化与访问权限放宽
-allowaccessmodification
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses

# 保留行号以便排查崩溃堆栈
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# 优化 Kotlin 冗余空安全检查调用
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void checkNotNullParameter(java.lang.Object, java.lang.String);
    public static void checkNotNull(java.lang.Object, java.lang.String);
    public static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
}
