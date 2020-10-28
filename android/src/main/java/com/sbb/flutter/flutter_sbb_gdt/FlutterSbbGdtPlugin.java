package com.sbb.flutter.flutter_sbb_gdt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.qq.e.ads.cfg.MultiProcessFlag;
import com.sbb.flutter.flutter_sbb_gdt.gdt.Interstitial;
import com.sbb.flutter.flutter_sbb_gdt.gdt.Splash;
import com.sbb.flutter.flutter_sbb_gdt.gdt.ractory.GDTBannerFactory;
import com.sbb.flutter.flutter_sbb_gdt.gdt.ractory.GDTNativeExpress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterSbbGdtPlugin */
public class FlutterSbbGdtPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.RequestPermissionsResultListener {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private static final String TAG = FlutterSbbGdtPlugin.class.getSimpleName();
  private static FlutterSbbGdtPlugin instance;
  private Activity activity;
  public static MethodChannel channel;
  public static String appid;
  private FlutterPluginBinding flutterPluginBinding;
//  public static Registrar registrar;
  public FlutterAssets flutterAssets;

  private List<String> lackedPermission;
  private int requestReadPhoneState;
  private int requestAccessFineLocation;
  private int _requestCode;

  public static FlutterSbbGdtPlugin getInstance(){
    return instance;
  }
  public static Activity getActivity() {
    return instance.activity;
  }

  public FlutterSbbGdtPlugin() {
    instance = this;
  }

  /** Plugin registration. */
//  public static void registerWith(Registrar registrar) {
//    FlutterSbbGdtPlugin.registrar = registrar;
//    FlutterSbbGdtPlugin.channel = new MethodChannel(registrar.messenger(), "plugins.hetian.me/gdt_plugins");
//    FlutterSbbGdtPlugin.channel.setMethodCallHandler(new FlutterSbbGdtPlugin());
//
//    registrar.platformViewRegistry().registerViewFactory("plugins.hetian.me/gdtview_banner", new GDTBannerFactory(registrar));
//    registrar.platformViewRegistry().registerViewFactory("plugins.hetian.me/gdtview_native", new GDTNativeExpress(registrar));
//  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_sbb_gdt");
    channel.setMethodCallHandler(this);

    flutterPluginBinding.getPlatformViewRegistry().registerViewFactory("flutter_sbb_gdt/gdtview_banner", new GDTBannerFactory(flutterPluginBinding.getBinaryMessenger()));
    flutterPluginBinding.getPlatformViewRegistry().registerViewFactory("flutter_sbb_gdt/gdtview_native", new GDTNativeExpress(flutterPluginBinding.getBinaryMessenger()));
    this.flutterPluginBinding = flutterPluginBinding;
    this.flutterAssets = flutterPluginBinding.getFlutterAssets();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }else if(call.method.equals("requestPermission")){
      if (Build.VERSION.SDK_INT >= 23) {
        checkAndRequestPermission(call, result);
      } else {
        result.success("");
      }
    }else if(call.method.equals("init")){
      init(call, result);
    }else if(call.method.equals("init")){
      String uuid = Interstitial.CreateInterstitial((Map<String, Object>)call.arguments);
      HashMap<String, String> rets = new HashMap<>();
      rets.put("channel_name", Interstitial.GetChannelName(uuid));
      result.success(rets);
    }else if(call.method.equals("init")){
      Splash.getInstance(flutterPluginBinding.getApplicationContext(),flutterPluginBinding.getBinaryMessenger()).show((Map<String, Object>)call.arguments, result);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private void init(MethodCall call, Result result) {
    if (call.hasArgument("appid")) {
      MultiProcessFlag.setMultiProcess(true);
      FlutterSbbGdtPlugin.appid = (String)call.argument("appid");
      result.success("");
      return ;
    }
    result.error("100", "请设置appid", "");
  }

  /**
   * ----------非常重要----------
   * <p>
   * Android6.0以上的权限适配简单示例：
   * <p>
   * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
   * <p>
   * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
   * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
   */
  @TargetApi(Build.VERSION_CODES.M)
  private void checkAndRequestPermission(MethodCall call, final Result result) {
    lackedPermission = new ArrayList<>();
    Activity activity = FlutterSbbGdtPlugin.getActivity();
    if (requestReadPhoneState > 0 && !(activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
      lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
    }

//        if (!(activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
//            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }

    if (requestAccessFineLocation > 0 && !(activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
      lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    if (lackedPermission.size() > 0) {
      // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
      String[] requestPermissions = new String[lackedPermission.size()];
      lackedPermission.toArray(requestPermissions);
//      registrar.addRequestPermissionsResultListener(this);
      _requestCode = Integer.parseInt(new SimpleDateFormat("MMddHHmmss", Locale.CHINA).format(new Date()));
      activity.requestPermissions(requestPermissions, _requestCode);
      Log.d(TAG, "requesting permissions...");
    }
  }

  private boolean isPermissionsFine(int[] grantResults) {
    for (int i = 0;i < grantResults.length;i++) {
      int grantResult = grantResults[i];
      // 有必须权限未授予
      if (grantResult == PackageManager.PERMISSION_DENIED && ((lackedPermission.get(i).equals(Manifest.permission.ACCESS_FINE_LOCATION) && requestAccessFineLocation == 2) || (lackedPermission.get(i).equals(Manifest.permission.READ_PHONE_STATE) && requestReadPhoneState == 2))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
    binding.addRequestPermissionsResultListener(this);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    this.activity = null;
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
    binding.addRequestPermissionsResultListener(this);
  }

  @Override
  public void onDetachedFromActivity() {
    this.activity = null;
  }

  @Override
  public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    Log.d(TAG, "onRequestPermissionsResult " + requestCode);
    Activity activity = FlutterSbbGdtPlugin.getActivity();
    if (requestCode == _requestCode) {
      if(!isPermissionsFine(grantResults)) {
        Log.d(TAG, "permissions rejected");
        // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
        Toast.makeText(activity, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
        activity.finish();
      }
      return true;
    }
    return false;
  }
}
