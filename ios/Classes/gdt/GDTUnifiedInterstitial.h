//
//  GDTUnifiedInterstitial.h
//  Pods-Runner
//
//  Created by bb s on 3/26/20.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
#import <GDTMobSDK/GDTUnifiedInterstitialAd.h>
#import "GDTConfig.h"

NS_ASSUME_NONNULL_BEGIN

@interface GDTUnifiedInterstitial : NSObject<GDTUnifiedInterstitialAdDelegate>

@property (strong, nonatomic) GDTUnifiedInterstitialAd *ad;

- (instancetype)init:(NSString *)posId binaryMessenger:(NSObject<FlutterBinaryMessenger>*)messenger;
- (void)show;
- (void)close;

@end

NS_ASSUME_NONNULL_END
