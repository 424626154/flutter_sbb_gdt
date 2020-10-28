# flutter_sbb_gdt

flutter gdt

## Getting Started

This project is a starting point for a Flutter
[plug-in package](https://flutter.dev/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter, view our
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.


参考项目:
https://github.com/dolphinxx/adnet_qq
https://github.com/jlcool/flutter_gdt_plugin


初始化: FlutterGdtPlugin.init(Platform.isIOS?AppConfig.GDT_IOS_ID:AppConfig.GDT_ANDROID_ID);
开屏:FlutterGdtPlugin.showSplash(Platform.isIOS?AppConfig.GDT_IOS_SPLASG:AppConfig.GDT_ANDROID_SPLASH,true, 'assets/icon.png');
横屏:GDTNativeExpressView(posId: AdManager.getNativeExpressBannerSmallAdUnitId())
ios 编译时出现 Include of non-modular header inside framework module 'xxx':'xxx'
buldsetting 中设置 Allow Non-modular Includes In Framework Modules 为 YES


安卓
步骤2: 权限申请
优量汇 SDK 建议您在AndroidManifest.xml添加以下权限声明，若您的targetSDKVersion >= 23您还需要在运行时进行动态权限申请（可参考示例工程）

<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />    <!-- 可选，如果需要精确定位的话请加上此权限 -->
注意：SDK不强制校验上述权限（即:无上述权限sdk也可正常工作），但建议您申请上述权限。针对单媒体的用户，允许获取权限的，投放定向广告；不允许获取权限的用户，投放通投广告。媒体可以选择是否把上述权限提供给优量汇，并承担相应广告填充和eCPM单价下降损失的结果。

步骤3: 文件兼容
如果您打包 App 时的 targetSdkVersion >= 24
为了让 SDK 能够正常下载、安装 App 类广告，必须按照下面的步骤做兼容性处理。

依赖support库以支持FileProvider，且依赖库的大版本号必须大于等于24（尽量用更新版本的依赖库，例如：28.0.0），以保证支持tag : external-cache-path。
在 AndroidManifest.xml 中的 Application 标签中添加 provider 标签

    <provider
     android:name="com.qq.e.comm.GDTFileProvider"
     android:authorities="${applicationId}.gdt.fileprovider"
     android:exported="false"
     android:grantUriPermissions="true">
     <meta-data
         android:name="android.support.FILE_PROVIDER_PATHS"
         android:resource="@xml/gdt_file_path" />
 </provider>
需要注意的是 provider 的 authorities 值为 ${applicationId}.gdt.fileprovider，对于每一个开发者而言，这个值都是不同的，${applicationId} 在代码中和 Context.getPackageName() 值相等，是应用的唯一 id。

例如 Demo 示例工程中的 applicationId 为 "com.qq.e.union.demo"。
