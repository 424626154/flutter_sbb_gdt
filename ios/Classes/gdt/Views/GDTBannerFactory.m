//
//  GDTBannerFactory.m
//  Pods-Runner
//
//  Created by bb s on 11/30/19.
//

#import "GDTBannerFactory.h"
#import "GDTBannerView.h"

@implementation GDTBannerFactory
{
    NSObject<FlutterBinaryMessenger>* _messenger;
}
- (instancetype)initWithMessenger:(NSObject<FlutterBinaryMessenger>*)messenger
{
    self = [super init];
    if (self) {
        _messenger = messenger;
    }
    return self;
}

- (NSObject<FlutterPlatformView>*)createWithFrame:(CGRect)frame
                                   viewIdentifier:(int64_t)viewId
                                        arguments:(id _Nullable)args
{
    return [[GDTBannerContainer alloc] initWithFrame:frame viewIdentifier:viewId arguments:args messenger:_messenger];
}
- (NSObject<FlutterMessageCodec>*)createArgsCodec {
    return [FlutterStandardMessageCodec sharedInstance];
}

@end

@implementation GDTBannerContainer
{
    CGRect _frame;
    int64_t _viewId;
    NSDictionary *_args;
    NSObject<FlutterBinaryMessenger>* _messenger;
}

- (instancetype)initWithFrame:(CGRect)frame
               viewIdentifier:(int64_t)viewId
                    arguments:(id _Nullable)args
                    messenger:(NSObject<FlutterBinaryMessenger>*)messenger
{
    self = [super init];
    if (self) {
        _frame = frame;
        _viewId = viewId;
        _args = args;
        _messenger = messenger;
    }
    return self;
}

- (UIView*)view
{
    return [[GDTBannerView alloc] initWithFrame:_frame viewIdentifier:_viewId arguments:_args messenger:_messenger];
}

@end
