# react-native-audio-focus

A React Native library to handle audio focus changes on both Android and iOS. This module allows React Native applications to respond to audio focus changes such as when another app starts playing audio or when a phone call is received. It helps in managing audio playback effectively by pausing or lowering the volume of the audio when necessary.

## Features

- Detects when audio focus is gained or lost.
- Handles audio interruptions and allows resuming audio playback.
- Simple API to request and abandon audio focus.
- Supports both Android and iOS platforms.
- Written in TypeScript for type safety.

## Events

```
    - AUDIOFOCUS_GAIN
    - AUDIOFOCUS_LOSS
    - AUDIOFOCUS_LOSS_TRANSIENT //android
    - AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK //android
```

## Installation

```sh
npm install react-native-audio-focus-module
```

or

```sh
yarn add react-native-audio-focus-module
```

## Linking

For React Native 0.60 and above, the library is automatically linked using autolinking. For older versions, you need to manually link the library:

```sh
react-native link react-native-audio-focus-module
```

## Android Setup

1. **Add the Native Module**: In your application's
   **android/app/src/main/java/com/yourappname/MainApplication.java**, register the AudioFocusPackage:

   ```java
   import com.yourprojectname.AudioFocusPackage; // Add this import

   public class MainApplication extends Application implements ReactApplication {

   @Override
   protected List<ReactPackage> getPackages() {
       @SuppressWarnings("UnnecessaryLocalVariable")
       List<ReactPackage> packages = new PackageList(this).getPackages();
       packages.add(new AudioFocusPackage()); // Add this line
       return packages;
   }

   // other methods...
   }

   ```

2. **Permissions**: Add the following permissions to your **main/AndroidManifest.xml**:
   ```xml
   <manifest>
    ...
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    ...
   </manifest>
   ```

## iOS Setup

1. **Swift Bridging Header**: Ensure your React Native project can use Swift. If you haven't already, create a bridging header:

   - Open your Xcode project.
   - Create a new Swift file (File > New > File > Swift File) and name it **Dummy.swift** (you can name it anything, actually).
   - Xcode will prompt you to create a bridging header. Accept the prompt.

2. **Add the Audio Focus Module**: Ensure that **AudioFocusModule.swift** is correctly added to your project.

3. **Modify AppDelegate**: Your **AppDelegate.m** should import the generated Swift header:

   ```objc
   #import "YourProjectName-Swift.h"
   ```

4. **Register the Module**: No need to explicitly register the module in **AppDelegate.m**, as it's done automatically.

## Usage

- Import the library into your React Native project:

```typescript
import AudioFocusModule from 'react-native-audio-focus-module';
```

- Request Audio Focus

```typescript
AudioFocusModule.requestAudioFocus();
```

- Abandoning Audio Focus

```typescript
AudioFocusModule.abandonAudioFocus();
```

- Listening to audio focus Changes

```typescript
const unsubscribe = AudioFocusModule.addAudioFocusListener((event) => {
  if (event === 'AUDIOFOCUS_GAIN') {
    console.log('Audio focus gained');
    // Resume playback or increase volume
  } else if (event === 'AUDIOFOCUS_LOSS') {
    console.log('Audio focus lost');
    // Pause playback or lower volume
  }
});

// To remove the listener when it's no longer needed
unsubscribe();
```

## Example

```typescript
import React, { useEffect } from 'react';
import { View, Text, Button } from 'react-native';
import AudioFocusModule from 'react-native-audio-focus-module';

const App = () => {
  useEffect(() => {
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
      <Button title="Request Audio Focus" onPress={() => AudioFocusModule.requestAudioFocus()} />
      <Button title="Abandon Audio Focus" onPress={() => AudioFocusModule.abandonAudioFocus()} />
    </View>
  );
};

export default App;

```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request if you have any improvements or suggestions.
See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

This library is licensed under the MIT License.

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
