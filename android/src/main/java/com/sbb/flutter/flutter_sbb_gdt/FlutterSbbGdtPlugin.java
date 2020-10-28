package com.sbb.flutter.flutter_sbb_gdt;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import com.qq.e.ads.cfg.MultiProcessFlag;
import com.sbb.flutter.flutter_sbb_gdt.gdt.Interstitial;
import com.sbb.flutter.flutter_sbb_gdt.gdt.Splash;
import com.sbb.flutter.flutter_sbb_gdt.gdt.ractory.GDTBannerFactory;
import com.sbb.flutter.flutter_sbb_gdt.gdt.ractory.GDTNativeExpress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterSbbGdtPlugin */
public class FlutterSbbGdtPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  public static MethodChannel channel;
  public static String appid;
  public static Registrar registrar;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    FlutterSbbGdtPlugin.registrar = registrar;
    FlutterSbbGdtPlugin.channel = new MethodChannel(registrar.messenger(), "plugins.hetian.me/gdt_plugins");
    FlutterSbbGdtPlugin.channel.setMethodCallHandler(new FlutterSbbGdtPlugin());

    registrar.platformViewRegistry().registerViewFactory("plugins.hetian.me/gdtview_banner", new GDTBannerFactory(registrar));
    registrar.platformViewRegistry().registerViewFactory("plugins.hetian.me/gdtview_native", new GDTNativeExpress(registrar));
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_sbb_gdt");
    channel.setMethodCallHandler(this);
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
      Splash.getInstance(registrar).show((Map<String, Object>)call.arguments, result);
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
    List<String> lackedPermission = new ArrayList<String>();
    if (!(registrar.activity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
      lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
    }

    if (!(registrar.activity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
      lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    if (!(registrar.activity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
      lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    if (lackedPermission.size() > 0) {
      // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
      String[] requestPermissions = new String[lackedPermission.size()];

      lackedPermission.toArray(requestPermissions);
      registrar.activity().requestPermissions(requestPermissions, 1024);
      PluginRegistry.RequestPermissionsResultListener onRequestPermissionsResult = new PluginRegistry.RequestPermissionsResultListener() {
        @Override
        public boolean onRequestPermissionsResult(int requestCode, String[] strings, int[] grantResults) {
          if (!hasAllPermissionsGranted(grantResults)) {
            result.error("110", "权限申请失败", null);
            return false;
          }
          result.success("");
          return true;
        }
      };
      registrar.addRequestPermissionsResultListener(onRequestPermissionsResult);
    } else {
      result.success("");
    }
  }

  private boolean hasAllPermissionsGranted(int[] grantResults) {
    for (int grantResult : grantResults) {
      if (grantResult == PackageManager.PERMISSION_DENIED) {
        return false;
      }
    }
    return true;
  }

}
