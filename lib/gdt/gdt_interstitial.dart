import 'package:flutter/services.dart';
import 'flutterGdtPlugin.dart';

typedef UnifiedInterstitialAdEventCallback = Function(UnifiedInterstitialAdEvent event, dynamic arguments);
enum UnifiedInterstitialAdEvent {
  onNoAd,
  onAdReceived,
  onAdExposure,
  onAdClosed,
  onAdClicked,
  onAdLeftApplication,
  onAdOpened,
}

class GDTInterstitial{
  final String posId;

  final UnifiedInterstitialAdEventCallback adEventCallback;

  MethodChannel _methodChannel;

  GDTInterstitial(this.posId, {this.adEventCallback}) {
    var channelName = 'flutter_sbb_gdt/gdt_plugins/interstitial/$posId';
    print('$channelName');
    this._methodChannel = MethodChannel(channelName);
    this._methodChannel.setMethodCallHandler(_handleMethodCall);
    FlutterGdtPlugin.createInterstitialAd(posId: posId);
  }

  Future<void> _handleMethodCall(MethodCall call) async {
    if(adEventCallback != null) {
      UnifiedInterstitialAdEvent event;
      switch (call.method) {
        case 'onNoAd':
          event = UnifiedInterstitialAdEvent.onNoAd;
          break;
        case 'onAdReceived':
          event = UnifiedInterstitialAdEvent.onAdReceived;
          break;
        case 'onAdExposure':
          event = UnifiedInterstitialAdEvent.onAdExposure;
          break;
        case 'onAdClosed':
          event = UnifiedInterstitialAdEvent.onAdClosed;
          break;
        case 'onAdClicked':
          event = UnifiedInterstitialAdEvent.onAdClicked;
          break;
        case 'onAdLeftApplication':
          event = UnifiedInterstitialAdEvent.onAdLeftApplication;
          break;
        case 'onAdOpened':
          event = UnifiedInterstitialAdEvent.onAdOpened;
          break;
      }
      adEventCallback(event, call.arguments);
    }
  }

  Future<void> loadAd() async {
    await _methodChannel.invokeMethod('load');
  }

  Future<void> closeAd() async {
    await _methodChannel.invokeMethod('close');
  }

  Future<void> showAd() async {
    await _methodChannel.invokeMethod('show');
  }

  Future<void> showAdAsPopup() async {
    await _methodChannel.invokeMethod('popup');
  }

}