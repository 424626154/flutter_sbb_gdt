#import <Flutter/Flutter.h>

@interface FlutterSbbGdtPlugin : NSObject<FlutterPlugin>

@property (nonatomic, strong) NSObject<FlutterPluginRegistrar> * _Nonnull registrar;

+ (instancetype ) sharedInstance;

+(void)removeInterstitial:(NSString*)posId;

@end
