# AMR Recorder

[![npm](https://img.shields.io/npm/v/benz-amr-recorder.svg)](https://www.npmjs.com/package/benz-amr-recorder)
[![npm](https://img.shields.io/npm/dt/benz-amr-recorder.svg)](https://www.npmjs.com/package/benz-amr-recorder)
[![size](https://img.shields.io/github/size/BenzLeung/benz-amr-recorder/BenzAMRRecorder.min.js)](https://gitHub.com/BenzLeung/benz-amr-recorder/BenzAMRRecorder.min.js)
[![gzip size](http://img.badgesize.io/https://npmcdn.com/benz-amr-recorder/BenzAMRRecorder.min.js?compression=gzip)](https://gitHub.com/BenzLeung/benz-amr-recorder/BenzAMRRecorder.min.js)

Play, record, reformat AMR audio, in pure Javascript, without any server.

This project is based on [amr.js](https://github.com/jpemartins/amr.js) and [RecorderJs](https://github.com/jergason/Recorderjs).

NOTE: Since amr.js is used for encoding and decoding, the js file is close to 500 KB (minified, no gzipped), please consider before use.

## Feature

 - Simple API for playing and recording AMR audio.
 - Supported url or blob (e.g. `<input type="file">`) to initialize AMR.
 - Supported reformat audio which browser is supported (such as MP3 or OGG) to AMR audio.
 - AMR that is encoded could be downloaded, without any server.
 
## Demo

[demo-en.html](https://benzleung.github.io/benz-amr-recorder/demo-en.html)

## Browser Compatibility

For the latest browser compatibility, please refer to [Can I Use](https://caniuse.com/#feat=audio-api).

  - Play only: [https://caniuse.com/#feat=audio-api](https://caniuse.com/#feat=audio-api)
  - Play + Record: [https://caniuse.com/#feat=stream](https://caniuse.com/#feat=stream)

## Setup

Load the JS file directly:

```html
<script type="text/javascript" src="./BenzAMRRecorder.min.js"></script>
```

OR use npm:

[![NPM](https://nodei.co/npm/benz-amr-recorder.png)](https://www.npmjs.com/package/benz-amr-recorder)

```
npm install benz-amr-recorder
```

```javascript
var BenzAMRRecorder = require('benz-amr-recorder');
```

## Usage

**Note:** It is recommended to bind the `initWithXXX()` or `play()` methods to a user event (eg `click`, `touchstart`). Because almost all mobile devices (and the desktop version of Chrome 70+) prohibit JavaScript from playing audio automatically. reference:

 - [https://webkit.org/blog/6784/new-video-policies-for-ios/](https://webkit.org/blog/6784/new-video-policies-for-ios/)
 - [https://developers.google.com/web/updates/2017/09/autoplay-policy-changes](https://developers.google.com/web/updates/2017/09/autoplay-policy-changes)

Play an AMR:

```javascript
var amr = new BenzAMRRecorder();
amr.initWithUrl('path/to/voice.amr').then(function() {
  amr.play();
});
amr.onEnded(function() {
  alert('play ended');
})
```

Play a local file:

```html
<input type="file" id="amr-file" accept=".amr">
```

```javascript
var amr = new BenzAMRRecorder();
var amrFileObj = document.getElementById('amr-file');
amrFileObj.onchange = function() {
  amr.initWithBlob(this.files[0]).then(function() {
    amr.play();
  });
}
```

Record AMR:

```javascript
var amrRec = new BenzAMRRecorder();
amrRec.initWithRecord().then(function() {
  amrRec.startRecord();
});
```

Download AMR:

```javascript
window.location.href = window.URL.createObjectURL(amr.getBlob());
```

Reformat MP3 to AMR (Need browser support MP3 format):

```javascript
var amrFromMp3 = new BenzAMRRecorder();
amrFromMp3.initWithUrl('path/to/file.mp3').then(function() {
  // Download the AMR file
  window.location.href = window.URL.createObjectURL(amrFromMp3.getBlob());
})
```

## API

#### Initialize

```javascript
/**
 * If AMR was initialized
 * @return {boolean}
 */
amr.isInit();
```

```javascript
/**
 * Init with Float32Array
 * @param {Float32Array} array
 * @return {Promise}
 */
amr.initWithArrayBuffer(array);
```

```javascript
/**
 * Init with Blob object ( <input type="file"> )
 * @param {Blob} blob
 * @return {Promise}
 */
amr.initWithBlob(blob);
```

```javascript
/**
 * Init with URL
 * @param {string} url
 * @return {Promise}
 */
amr.initWithUrl(url);
```

```javascript
/**
 * Initialize record
 * @return {Promise}
 */
amr.initWithRecord();
```

#### Event listeners

**Notice: They will NOT add the event listener. They simply cover the old listener only.**


```javascript
/**
 * On play
 * @param {Function | null} fn
 */
amr.onPlay(function() {
  console.log('play');
});
```

```javascript
/**
 * On stop (Include onEnded)
 * @param {Function | null} fn
 */
amr.onStop(function() {
  console.log('stop playing');
});
```

```javascript
/**
 * On pause
 * @param {Function | null} fn
 */
amr.onPause(function() {
  console.log('pause');
});
```

```javascript
/**
 * On resume (form the paused state)
 * @param {Function | null} fn
 */
amr.onResume(function() {
  console.log('resume');
});
```

```javascript
/**
 * on play ended
 * @param {Function | null} fn
 */
amr.onEnded(function() {
  console.log('play ended');
});
```

```javascript
/**
 * on play to end and automatically ended
 * @param {Function | null} fn
 */
amr.onAutoEnded(function() {
  console.log('play automatically ended');
});
```

```javascript
/**
 * on start record
 * @param {Function | null} fn
 */
amr.onStartRecord(function() {
  console.log('start record');
});
```

```javascript
/**
 * on finish record
 * @param {Function | null} fn
 */
amr.onFinishRecord(function() {
  console.log('finish record');
});
```

#### Playing controls

```javascript
/**
 * play (ignore the paused state)
 * @param {number?} startTime - specify the start position (in seconds, float number, optional)
 */
amr.play();
```

```javascript
/**
 * stop
 */
amr.stop();
```

```javascript
/**
 * pause
 * @since 1.1.0
 */
amr.pause();
```

```javascript
/**
 * resume from the paused state
 * @since 1.1.0
 */
amr.resume();
```

```javascript
/**
 * Integrate `play()` and `resume()`, if it is paused, continue, otherwise play from the beginning
 * @since 1.1.0
 */
amr.playOrResume();
```

```javascript
/**
 * Integrate `resume()` and `pause()` to toggle the pause state
 * @since 1.1.0
 */
amr.pauseOrResume();
```

```javascript
/**
 * Integrate play() , resume() , and pause()
 * @since 1.1.0
 */
amr.playOrPauseOrResume();
```

```javascript
/**
 * Jump to the specified position of the audio, it will not change the playback status (if it is stopped, it is equivalent to `play(time)`)
 * @since 1.1.0
 * @param {Number} time the specified position（in seconds, float number）
 */
amr.setPosition(12.34);
```

```javascript
/**
 * Get the current playback position (in seconds) 
 * @since 1.1.0
 * @return {Number} position, in seconds, float number
 */
amr.getCurrentPosition();
```

```javascript
/**
 * If AMR was playing
 * @return {boolean}
 */
amr.isPlaying();
```

```javascript
/**
 * If audio was paused
 * @since 1.1.0
 * @return {boolean}
 */
amr.isPaused();
```

#### Recording controls

```javascript
/**
 * Start record
 */
amr.startRecord();
```

```javascript
/**
 * Finish record, and then reformat to AMR
 * @return {Promise}
 */
amr.finishRecord();
```

```javascript
/**
 * Cancel record
 */
amr.cancelRecord();
```

```javascript
/**
 * If it was recording
 * @return {boolean}
 */
amr.isRecording();
```

#### Other APIs

```javascript
/**
 * Get duration of the AMR (by second)
 * @return {Number}
 */
amr.getDuration();
```

```javascript
/**
 * Get the Blob object of the AMR file (Use for download)
 * @return {Blob}
 */
amr.getBlob();
```

```javascript
/**
 * Release AMR data and PCM data, stop recording, remove all event listeners
 * @since 1.1.4
 */
amr.destroy();
```

```javascript
/**
 * Determine if the browser supports playback
 * Note that this is a static method
 * @since 1.1.0
 * @return {boolean}
 */
BenzAMRRecorder.isPlaySupported();
// NOT `amr.isPlaySupported();`
```

```javascript
/**
 * Determine if the browser supports recording
 * Note that this is a static method
 * @since 1.1.0
 * @return {boolean}
 */
BenzAMRRecorder.isRecordSupported();
// NOT `amr.isRecordSupported();`
```

# Todo list

 - [x] ~~Encode & decode with WebWorker.~~（v1.0.9）
 - [x] ~~Pause playback.~~（v1.1.0）
 - [ ] Pause recording.
 - [x] ~~Playing progress and jump to a position.~~（v1.1.0）
 - [x] ~~Browser compatibility check API ([#9](https://github.com/BenzLeung/benz-amr-recorder/issues/9) [#11](https://github.com/BenzLeung/benz-amr-recorder/issues/11)).~~（v1.1.0）

# License

MIT.
