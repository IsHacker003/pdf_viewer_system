# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.rajat.pdfviewer.HeaderData
-dontwarn com.rajat.pdfviewer.PdfRendererView$StatusCallBack
-dontwarn com.rajat.pdfviewer.PdfRendererView$ZoomListener
-dontwarn com.rajat.pdfviewer.PdfViewerActivity$Companion
-dontwarn com.rajat.pdfviewer.compose.PdfRendererComposeKt
-dontwarn com.rajat.pdfviewer.util.CacheStrategy
-dontwarn com.rajat.pdfviewer.util.PdfSource$LocalUri
-dontwarn com.rajat.pdfviewer.util.PdfSource$Remote
-dontwarn com.rajat.pdfviewer.util.PdfSource
-dontwarn com.rajat.pdfviewer.util.ToolbarTitleBehavior
-dontwarn com.rajat.pdfviewer.util.saveTo
