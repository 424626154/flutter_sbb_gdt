//
//  FlutterPluginCache.h
//  Pods-Runner
//
//  Created by bb s on 11/30/19.
//

#import <Foundation/Foundation.h>
#import "GDTInterstitial.h"
#import "GDTSplash.h"
#import "GDTRewardVideo.h"

NS_ASSUME_NONNULL_BEGIN

@interface FlutterPluginCache : NSObject
@property (nonatomic, strong) NSMutableDictionary<NSString *, GDTInterstitial *>* interstitila;
@property (nonatomic, strong, nullable) GDTSplash * splash;

@property (nonatomic, strong, nullable) GDTRewardVideo * rewardVideo;

+ (instancetype) sharedInstance;
@end

NS_ASSUME_NONNULL_END
