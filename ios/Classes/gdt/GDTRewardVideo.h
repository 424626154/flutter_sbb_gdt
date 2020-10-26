//
//  GDTRewardVideo.h
//  Pods-Runner
//
//  Created by bb s on 1/28/20.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
#import "GDTConfig.h"
#import <GDTMobSDK/GDTRewardVideoAd.h>


NS_ASSUME_NONNULL_BEGIN

@interface GDTRewardVideo: NSObject<GDTRewardedVideoAdDelegate>
- (instancetype) initWithMessenger:(NSObject<FlutterPluginRegistrar>*)registrar;

- (void) show:(NSDictionary *)args result:(FlutterResult)result;

@end

NS_ASSUME_NONNULL_END
