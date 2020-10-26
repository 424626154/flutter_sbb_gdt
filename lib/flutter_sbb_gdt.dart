
import 'dart:async';

import 'package:flutter/services.dart';

class FlutterSbbGdt {
  static const MethodChannel _channel =
      const MethodChannel('flutter_sbb_gdt');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
