#import "EaseKefuPlugin.h"
#import <ease_kefu/ease_kefu-Swift.h>

@implementation EaseKefuPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftEaseKefuPlugin registerWithRegistrar:registrar];
}
@end
