//
//  GDTInterstitial.h
//  Pods-Runner
//
//  Created by bb s on 11/30/19.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
#import <GDTMobSDK/GDTUnifiedInterstitialAd.h>
#import "GDTConfig.h"

NS_ASSUME_NONNULL_BEGIN

@interface GDTInterstitial : NSObject<GDTUnifiedInterstitialAdDelegate>

@property (strong, nonatomic) GDTUnifiedInterstitialAd *interstitial;
@property (strong, nonatomic) FlutterMethodChannel *methodChannel;
+ (NSString *)createNew:(NSDictionary *)args;
+ (NSString *)getChannelName:(NSString *)uuid;

@end

NS_ASSUME_NONNULL_END
