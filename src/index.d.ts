declare module 'react-native-audio-focus' {
  type AudioFocusEvent =
    | 'AUDIOFOCUS_LOSS'
    | 'AUDIOFOCUS_GAIN'
    | 'AUDIOFOCUS_LOSS_TRANSIENT'
    | 'AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK';

  interface AudioFocusModule {
    requestAudioFocus(): void;
    abandonAudioFocus(): void;
    addAudioFocusListener(
      listener: (event: AudioFocusEvent) => void
    ): () => void;
  }

  const AudioFocusModule: AudioFocusModule;
  export default AudioFocusModule;
}
