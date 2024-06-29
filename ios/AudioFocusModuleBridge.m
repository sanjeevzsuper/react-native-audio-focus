#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
@interface RCT_EXTERN_MODULE(AudioFocusModule, RCTEventEmitter)
RCT_EXTERN_METHOD(requestAudioFocus)
RCT_EXTERN_METHOD(abandonAudioFocus)
@end