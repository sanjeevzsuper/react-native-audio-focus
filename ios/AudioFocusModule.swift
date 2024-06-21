import AVFoundation
import React

@objc(AudioFocusModule)
class AudioFocusModule: RCTEventEmitter {

    @objc
    func requestAudioFocus() {
        NotificationCenter.default.addObserver(self, selector: #selector(handleAudioInterruption), name: AVAudioSession.interruptionNotification, object: nil)
        do {
            try AVAudioSession.sharedInstance().setCategory(.playback, mode: .default, options: [])
            try AVAudioSession.sharedInstance().setActive(true)
        } catch {
            print("Failed to set audio session category.")
        }
    }

    @objc
    func abandonAudioFocus() {
        NotificationCenter.default.removeObserver(self, name: AVAudioSession.interruptionNotification, object: nil)
        do {
            try AVAudioSession.sharedInstance().setActive(false)
        } catch {
            print("Failed to deactivate audio session.")
        }
    }

    @objc
    func handleAudioInterruption(notification: Notification) {
        guard let userInfo = notification.userInfo,
              let typeValue = userInfo[AVAudioSessionInterruptionTypeKey] as? UInt,
              let type = AVAudioSession.InterruptionType(rawValue: typeValue) else {
            return
        }

        if (type == .began) {
            sendEvent(withName: "AUDIOFOCUS_LOSS", body: nil)
        } else if (type == .ended) {
            sendEvent(withName: "AUDIOFOCUS_GAIN", body: nil)
        }
    }

    override func supportedEvents() -> [String]! {
        return ["AUDIOFOCUS_LOSS", "AUDIOFOCUS_GAIN"]
    }
}
