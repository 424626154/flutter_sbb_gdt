#import "FlutterSbbGdtPlugin.h"

#import "GDTConfig.h"

#import "GDTBannerFactory.h"
#import "GDTNativeExpressFactory.h"

#import "GDTInterstitial.h"
#import "GDTSplash.h"
//#import "GDTRewardVideo.h"
#import "GDTUnifiedInterstitial.h"
#import "FlutterPluginCache.h"

static NSMutableDictionary<NSString*, GDTUnifiedInterstitial*> *interstitials = nil;

@implementation FlutterSbbGdtPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_sbb_gdt"
            binaryMessenger:[registrar messenger]];
  FlutterSbbGdtPlugin* instance = [[FlutterSbbGdtPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else {
    result(FlutterMethodNotImplemented);
  }
}

+ (instancetype) sharedInstance
{
    static FlutterSbbGdtPlugin *instance = nil;
    if (!instance) {
        instance = [[FlutterSbbGdtPlugin alloc] init];
    }
    return instance;
}


+ (void)removeInterstitial:(NSString*)posId {
    [interstitials removeObjectForKey:posId];
}

-(void) GDTConfigInit:(FlutterMethodCall*)call result:(FlutterResult)result
{
    [GDTConfig.sharedInstance initGDTConfig:call];
    result(@"success");
}

@end
