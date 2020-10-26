//
//  FlutterPluginCache.m
//  Pods-Runner
//
//  Created by bb s on 11/30/19.
//

#import "FlutterPluginCache.h"

@implementation FlutterPluginCache

+ (instancetype) sharedInstance
{
    static FlutterPluginCache *instance = nil;
    if (!instance) {
        instance = [[FlutterPluginCache alloc] init];
        instance.interstitila = [[NSMutableDictionary alloc] init];
    }
    return instance;
}

@end
