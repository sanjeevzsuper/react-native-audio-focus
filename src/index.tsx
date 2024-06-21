import { NativeModules, NativeEventEmitter } from 'react-native';
import { AudioFocusEvent } from './types';

const { AudioFocusModule } = NativeModules;
const audioFocusEmitter = new NativeEventEmitter(AudioFocusModule);

const requestAudioFocus = () => {
  AudioFocusModule.requestAudioFocus();
};

const abandonAudioFocus = () => {
  AudioFocusModule.abandonAudioFocus();
};

const addAudioFocusListener = (listener: (event: string) => void) => {
  const gainSubscription = audioFocusEmitter.addListener(
    AudioFocusEvent.Gain,
    () => listener(AudioFocusEvent.Gain)
  );
  const lossSubscription = audioFocusEmitter.addListener(
    AudioFocusEvent.Loss,
    () => listener(AudioFocusEvent.Loss)
  );
  const transientSubscription = audioFocusEmitter.addListener(
    AudioFocusEvent.LossTransient,
    () => listener(AudioFocusEvent.LossTransient)
  );
  const canDuckSubscription = audioFocusEmitter.addListener(
    AudioFocusEvent.LossTransientDuck,
    () => listener(AudioFocusEvent.LossTransientDuck)
  );
  return () => {
    gainSubscription.remove();
    lossSubscription.remove();
    transientSubscription.remove();
    canDuckSubscription.remove();
  };
};

export default {
  requestAudioFocus,
  abandonAudioFocus,
  addAudioFocusListener,
  AudioFocusEvent,
};
