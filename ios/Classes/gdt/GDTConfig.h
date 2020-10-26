//
//  GDTConfig.h
//  Pods-Runner
//
//  Created by bb s on 11/30/19.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
#import <GDTMobSDK/GDTSDKConfig.h>

NS_ASSUME_NONNULL_BEGIN

static FlutterMethodChannel *pluginChannel;

@interface GDTConfig : NSObject
@property (nonatomic, strong) NSString *appid;
+ (instancetype)sharedInstance;
+ (NSString *)createUUID;
- (void) initGDTConfig:(FlutterMethodCall*)call;
@end

NS_ASSUME_NONNULL_END
