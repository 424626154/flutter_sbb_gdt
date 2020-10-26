//
//  GDTConfig.m
//  Pods-Runner
//
//  Created by bb s on 11/30/19.
//

#import "GDTConfig.h"

@implementation GDTConfig

+ (instancetype) sharedInstance
{
    static GDTConfig *instance = nil;
    if (!instance) {
        instance = [[GDTConfig alloc] init];
    }
    return instance;
}

// 初始化广点通配置
- (void) initGDTConfig:(FlutterMethodCall *)call
{
    self.appid = call.arguments[@"appid"];
    [GDTSDKConfig enableGPS:YES];
}

+ (NSString *)createUUID
{
    CFUUIDRef uuidObject = CFUUIDCreate(kCFAllocatorDefault);
    NSString *uuidStr = (NSString  *)CFBridgingRelease(CFUUIDCreateString(kCFAllocatorDefault, uuidObject));
    CFRelease(uuidObject);
    return uuidStr;
}

@end
