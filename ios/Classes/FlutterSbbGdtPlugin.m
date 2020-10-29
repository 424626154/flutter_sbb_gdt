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
    
    FlutterSbbGdtPlugin* instance = [FlutterSbbGdtPlugin sharedInstance];
       instance.registrar = registrar;
       
     FlutterMethodChannel* channel = [FlutterMethodChannel
         methodChannelWithName:@"flutter_sbb_gdt"
               binaryMessenger:[registrar messenger]];
   //  FlutterSbbGdtIosPlugin* instance = [[FlutterSbbGdtIosPlugin alloc] init];
     [registrar addMethodCallDelegate:instance channel:channel];
       
       // 注册banner视图
       GDTBannerFactory *GDTBannerView = [[GDTBannerFactory alloc] initWithMessenger:registrar.messenger];
       [registrar registerViewFactory:GDTBannerView withId:@"flutter_sbb_gdt/gdtview_banner"];
       
       // 注册原生模版广告
       GDTNativeExpressFactory *GDTNativeView = [[GDTNativeExpressFactory alloc] initWithMessenger:registrar.messenger];
       [registrar registerViewFactory:GDTNativeView withId:@"flutter_sbb_gdt/gdtview_native"];
       
       // 注册插件通道
       pluginChannel = [FlutterMethodChannel
                        methodChannelWithName:@"flutter_sbb_gdt/gdt_plugins"
                        binaryMessenger:[registrar messenger]];
       [registrar addMethodCallDelegate:instance channel:pluginChannel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([@"getPlatformVersion" isEqualToString:call.method]) {
        result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
        }else if([@"init" isEqual:call.method]) {
            [self GDTConfigInit:call result:result];
        }else if ([@"interstitial" isEqual:call.method]) {
        //      NSString *uuid = [GDTInterstitial createNew:call.arguments];
        //      result(@{@"channel_name":[GDTInterstitial getChannelName:uuid]});
            NSDictionary* arguments = call.arguments;
            NSString* posId = arguments[@"posId"];
            if(interstitials[posId]) {
                [interstitials[posId] close];
            }
            interstitials[posId] = [[GDTUnifiedInterstitial alloc] init:posId binaryMessenger:_registrar.messenger];
            result(@(YES));
        }else if ([@"splash" isEqual:call.method]) {
            if ([FlutterPluginCache sharedInstance].splash) {
                [FlutterPluginCache sharedInstance].splash = NULL;
            }
            [FlutterPluginCache sharedInstance].splash = [[GDTSplash alloc] initWithMessenger:_registrar];
            [[FlutterPluginCache sharedInstance].splash show:call.arguments result:result];
        }else if ([@"reward_video" isEqual:call.method]) {
            if ([FlutterPluginCache sharedInstance].rewardVideo) {
                [FlutterPluginCache sharedInstance].rewardVideo = NULL;
            }
            [FlutterPluginCache sharedInstance].rewardVideo = [[GDTRewardVideo alloc] initWithMessenger:_registrar];
            [[FlutterPluginCache sharedInstance].rewardVideo show:call.arguments result:result];
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
