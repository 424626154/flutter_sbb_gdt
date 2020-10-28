//
//  GDTRewardVideo.m
//  Pods-Runner
//
//  Created by bb s on 1/28/20.
//

#import "GDTRewardVideo.h"

@implementation GDTRewardVideo{
    NSObject<FlutterBinaryMessenger>*_messenger;
    NSObject<FlutterPluginRegistrar>*_registrar;
    FlutterMethodChannel *_methodChannel;
    GDTRewardVideoAd *_rewardVideoAd;
}


- (instancetype) initWithMessenger:(NSObject<FlutterPluginRegistrar>*)registrar{
    self = [super init];
    if (self) {
        _messenger = registrar.messenger;
        _registrar = registrar;
    }
    return self;
}

- (void) show:(NSDictionary *)args result:(FlutterResult)result
{
    NSLog(@"GDTRewardVideo show");
    NSString *appId = args[@"appId"];
    NSString *posId = args[@"posId"];
    _rewardVideoAd= [[GDTRewardVideoAd alloc] initWithAppId:appId placementId:posId];
    _rewardVideoAd.delegate = self;
//    _rewardVideoAd.videoMuted = NO; // 设置激励视频是否静音
    [_rewardVideoAd loadAd];
    NSString *uuid = [GDTConfig createUUID];
    NSString *channelName = [NSString stringWithFormat:@"flutter_sbb_gdt/gdt_plugins/reward_video/%@", uuid];
    _methodChannel = [FlutterMethodChannel methodChannelWithName:channelName binaryMessenger:_messenger];
    result(@{@"channel_name":channelName});
}

- (void)gdt_rewardVideoAdDidLoad:(GDTRewardVideoAd *)rewardedVideoAd{
    NSLog(@"gdt_rewardVideoAdDidLoad");
}

- (void)gdt_rewardVideoAdVideoDidLoad:(GDTRewardVideoAd *)rewardedVideoAd{
    NSLog(@"gdt_rewardVideoAdVideoDidLoad");
    UIWindow *curVc = [[UIApplication sharedApplication] keyWindow].rootViewController;
    [_rewardVideoAd showAdFromRootViewController:curVc];
}

- (void)gdt_rewardVideoAdWillVisible:(GDTRewardVideoAd *)rewardedVideoAd{
    NSLog(@"GDTRewardVideoAd");
}

- (void)gdt_rewardVideoAdDidExposed:(GDTRewardVideoAd *)rewardedVideoAd{
    NSLog(@"gdt_rewardVideoAdDidExposed");
}

- (void)gdt_rewardVideoAdDidClose:(GDTRewardVideoAd *)rewardedVideoAd{
     NSLog(@"gdt_rewardVideoAdDidClose");
}

- (void)gdt_rewardVideoAdDidClicked:(GDTRewardVideoAd *)rewardedVideoAd{
    NSLog(@"gdt_rewardVideoAdDidClicked");
}

- (void)gdt_rewardVideoAd:(GDTRewardVideoAd *)rewardedVideoAd didFailWithError:(NSError *)error{
    NSLog(@"gdt_rewardVideoAd");
}

- (void)gdt_rewardVideoAdDidRewardEffective:(GDTRewardVideoAd *)rewardedVideoAd{
    NSLog(@"gdt_rewardVideoAdDidRewardEffective");
    [_methodChannel invokeMethod:@"onReward" arguments:@""];
}

- (void)gdt_rewardVideoAdDidPlayFinish:(GDTRewardVideoAd *)rewardedVideoAd{
     NSLog(@"gdt_rewardVideoAdDidPlayFinish");
}

@end
