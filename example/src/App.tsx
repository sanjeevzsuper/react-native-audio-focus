import React from 'react';
import { View, Text, Button } from 'react-native';
import AudioFocusModule from 'react-native-audio-focus';

const App = () => {
  React.useEffect(() => {
    const unsubscribe = AudioFocusModule.addAudioFocusListener((event) => {
      if (event === 'AUDIOFOCUS_GAIN') {
        console.log('Audio focus gained');
        // Resume playback or increase volume
      } else if (event === 'AUDIOFOCUS_LOSS') {
        console.log('Audio focus lost');
        // Pause playback or lower volume
      }
    });

    return () => {
      unsubscribe();
    };
  }, []);

  return (
    <View>
      <Text>Audio Focus Module Example</Text>
      <Button
        title="Request Audio Focus"
        onPress={() => AudioFocusModule.requestAudioFocus()}
      />
      <Button
        title="Abandon Audio Focus"
        onPress={() => AudioFocusModule.abandonAudioFocus()}
      />
    </View>
  );
};

export default App;
