//
//  GDTBannerView.h
//  Pods-Runner
//
//  Created by bb s on 11/30/19.
//

#import <UIKit/UIKit.h>
#import <Flutter/Flutter.h>
#import <GDTMobSDK/GDTUnifiedBannerView.h>

NS_ASSUME_NONNULL_BEGIN

@interface GDTBannerView : UIView<GDTUnifiedBannerViewDelegate>
- (instancetype)initWithFrame:(CGRect)frame
viewIdentifier:(int64_t)viewId
     arguments:(id _Nullable)args
     messenger:(NSObject<FlutterBinaryMessenger>*)messenger;
@end

NS_ASSUME_NONNULL_END
