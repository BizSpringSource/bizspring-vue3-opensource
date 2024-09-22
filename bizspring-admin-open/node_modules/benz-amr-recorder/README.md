# AMR 录音机 [![GitHub stars](https://img.shields.io/github/stars/BenzLeung/benz-amr-recorder.svg?style=social&label=Star&maxAge=2592000)](https://gitHub.com/BenzLeung/benz-amr-recorder/)

([README in English](./README-EN.md))

[![npm](https://img.shields.io/npm/v/benz-amr-recorder.svg)](https://www.npmjs.com/package/benz-amr-recorder)
[![npm](https://img.shields.io/npm/dt/benz-amr-recorder.svg)](https://www.npmjs.com/package/benz-amr-recorder)
[![size](https://img.shields.io/github/size/BenzLeung/benz-amr-recorder/BenzAMRRecorder.min.js)](https://gitHub.com/BenzLeung/benz-amr-recorder/BenzAMRRecorder.min.js)
[![gzip size](http://img.badgesize.io/https://npmcdn.com/benz-amr-recorder/BenzAMRRecorder.min.js?compression=gzip)](https://gitHub.com/BenzLeung/benz-amr-recorder/BenzAMRRecorder.min.js)

纯前端解码、播放、录音、编码 AMR 音频，无须服务器支持，基于 [amr.js](https://github.com/jpemartins/amr.js) 和 [RecorderJs](https://github.com/jergason/Recorderjs)。

注意：由于使用了 amr.js 做编码和解码，因此 js 文件（压缩后，未 gzip）接近 500 KB，使用前请考虑。

**2019-10-05 更新 (v1.1.0)：** 

增加浏览器支持检测功能，增加暂停播放、设置播放进度功能。

**2022-09-26 更新 (v1.1.4)：** 

1. 使用 `create-react-app` 脚手架测试了在 React 的使用情况，结论：没有发现问题。[代码点我](https://github.com/BenzLeung/test-amr-react)
2. 增加一个~~好像没有什么用的~~ `destroy()`，功能：释放amr数据和pcm数据、解绑所有事件、令对象作废。
3. 新功能：解绑事件，用法：给事件传入 `null`，例如：`amr.onPlay(null)` 。

**2022-09-27 更新 (v1.1.5)：**

更新 rollup 库以消除 github 的安全提醒。

## 特性

 - 方便的 API 实现解码、播放、录音、编码 AMR 文件。
 - 支持 url 和 blob （即`<input type="file">`）方式获取 AMR。
 - 支持将浏览器 `<audio>` 所支持的音频格式（例如 MP3 或 OGG 音频）转换成 AMR 音频。
 - 编码后的 AMR 文件可下载，无须服务器。

## Demo

[demo.html](https://benzleung.github.io/benz-amr-recorder/demo.html)

## 浏览器兼容性

最新的浏览器兼容性请参阅 [Can I Use](https://caniuse.com/#feat=stream) 。

 - 仅播放：[https://caniuse.com/#feat=audio-api](https://caniuse.com/#feat=audio-api)
 - 播放+录音：[https://caniuse.com/#feat=stream](https://caniuse.com/#feat=stream)

## 安装

方法一：引入 js 文件

```html
<script type="text/javascript" src="./BenzAMRRecorder.min.js"></script>
```

方法二：使用 npm

[![NPM](https://nodei.co/npm/benz-amr-recorder.png)](https://www.npmjs.com/package/benz-amr-recorder)

```
npm install benz-amr-recorder
```

```javascript
var BenzAMRRecorder = require('benz-amr-recorder');
```

## 用法

**注意：** 建议把 `initWithXXX()` 或 `play()` 方法绑定到一个用户事件中（例如 `click`、`touchstart`）。因为几乎所有移动设备（以及桌面版 Chrome 70+）都禁止页面自动播放音频。参考：

 - [https://webkit.org/blog/6784/new-video-policies-for-ios/](https://webkit.org/blog/6784/new-video-policies-for-ios/)
 - [https://developers.google.com/web/updates/2017/09/autoplay-policy-changes](https://developers.google.com/web/updates/2017/09/autoplay-policy-changes)

播放 AMR：

```javascript
var amr = new BenzAMRRecorder();
amr.initWithUrl('path/to/voice.amr').then(function() {
  amr.play();
});
amr.onEnded(function() {
  alert('播放完毕');
})
```

播放本地文件：

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

录制 AMR：

```javascript
var amrRec = new BenzAMRRecorder();
amrRec.initWithRecord().then(function() {
  amrRec.startRecord();
});
```

下载 AMR：

```javascript
window.location.href = window.URL.createObjectURL(amr.getBlob());
```

把 MP3 转换成 AMR （需要浏览器原生支持 MP3）：

```javascript
var amrFromMp3 = new BenzAMRRecorder();
amrFromMp3.initWithUrl('path/to/file.mp3').then(function() {
  // 下载 amr 文件
  window.location.href = window.URL.createObjectURL(amrFromMp3.getBlob());
})
```

## API

#### 初始化对象

```javascript
/**
 * 是否已经初始化
 * @return {boolean}
 */
amr.isInit();
```

```javascript
/**
 * 使用浮点数据初始化
 * @param {Float32Array} array
 * @return {Promise}
 */
amr.initWithArrayBuffer(array);
```

```javascript
/**
 * 使用 Blob 对象初始化（ <input type="file">）
 * @param {Blob} blob
 * @return {Promise}
 */
amr.initWithBlob(blob);
```

```javascript
/**
 * 使用 url 初始化
 * @param {string} url
 * @return {Promise}
 */
amr.initWithUrl(url);
```

```javascript
/**
 * 初始化录音
 * @return {Promise}
 */
amr.initWithRecord();
```

#### 事件

**注意：事件不会叠加，也就是说，新注册的事件将覆盖掉旧的事件。**

**2022-09-26 更新：** 现在支持解绑事件，只要传入 `null`，例如 `amr.onPlay(null)` 。

```javascript
/**
 * 播放
 * @param {Function | null} fn
 */
amr.onPlay(function() {
  console.log('开始播放');
});
```

```javascript
/**
 * 停止（包括播放结束）
 * @param {Function | null} fn
 */
amr.onStop(function() {
  console.log('停止播放');
});
```

```javascript
/**
 * 暂停
 * @param {Function | null} fn
 */
amr.onPause(function() {
  console.log('暂停');
});
```

```javascript
/**
 * （暂停状态中）继续播放
 * @param {Function | null} fn
 */
amr.onResume(function() {
  console.log('继续播放');
});
```

```javascript
/**
 * 播放结束
 * @param {Function | null} fn
 */
amr.onEnded(function() {
  console.log('播放结束');
});
```

```javascript
/**
 * 播放到结尾自动结束
 * @param {Function | null} fn
 */
amr.onAutoEnded(function() {
  console.log('播放自动结束');
});
```

```javascript
/**
 * 开始录音
 * @param {Function | null} fn
 */
amr.onStartRecord(function() {
  console.log('开始录音');
});
```

```javascript
/**
 * 结束录音
 * @param {Function | null} fn
 */
amr.onFinishRecord(function() {
  console.log('结束录音');
});
```

#### 播放控制

```javascript
/**
 * 播放（无视暂停状态）
 * @param {number?} startTime 可指定播放开始位置（秒，浮点数，可选）
 */
amr.play();
```

```javascript
/**
 * 停止
 */
amr.stop();
```

```javascript
/**
 * 暂停
 * @since 1.1.0
 */
amr.pause();
```

```javascript
/**
 * 从暂停状态中继续播放
 * @since 1.1.0
 */
amr.resume();
```

```javascript
/**
 * 整合 play() 和 resume()，若在暂停状态则继续，否则从头播放
 * @since 1.1.0
 */
amr.playOrResume();
```

```javascript
/**
 * 整合 resume() 和 pause()，切换暂停状态
 * @since 1.1.0
 */
amr.pauseOrResume();
```

```javascript
/**
 * 整合 play() 和 resume() 和 pause()
 * @since 1.1.0
 */
amr.playOrPauseOrResume();
```

```javascript
/**
 * 跳转到音频指定位置，不改变播放状态（若停止状态则等同于 `play(time)`） 
 * @since 1.1.0
 * @param {Number} time 指定位置（秒，浮点数）
 */
amr.setPosition(12.34);
```

```javascript
/**
 * 获取当前播放位置（秒） 
 * @since 1.1.0
 * @return {Number} 位置，秒，浮点数
 */
amr.getCurrentPosition();
```

```javascript
/**
 * 是否正在播放
 * @return {boolean}
 */
amr.isPlaying();
```

```javascript
/**
 * 是否暂停中
 * @since 1.1.0
 * @return {boolean}
 */
amr.isPaused();
```

#### 录音控制

```javascript
/**
 * 开始录音
 */
amr.startRecord();
```

```javascript
/**
 * 结束录音，并把录制的音频转换成 AMR
 * @return {Promise}
 */
amr.finishRecord();
```

```javascript
/**
 * 放弃录音
 */
amr.cancelRecord();
```

```javascript
/**
 * 是否正在录音
 * @return {boolean}
 */
amr.isRecording();
```

#### 其他

```javascript
/**
 * 获取音频的时间长度（单位：秒）
 * @return {Number}
 */
amr.getDuration();
```

```javascript
/**
 * 获取 AMR 文件的 Blob 对象（用于下载文件）
 * @return {Blob}
 */
amr.getBlob();
```

```javascript
/**
 * 释放amr文件数据，释放pcm音频数据，停止录音释放录音数据，取消监听所有事件，对象作废
 * @since 1.1.4
 */
amr.destroy();
```

```javascript
/**
 * 判断浏览器是否支持播放
 * 注意这是静态(static)方法
 * @since 1.1.0
 * @return {boolean}
 */
BenzAMRRecorder.isPlaySupported();
// 不是 amr.isPlaySupported();
```

```javascript
/**
 * 判断浏览器是否支持录音
 * 注意这是静态(static)方法
 * @since 1.1.0
 * @return {boolean}
 */
BenzAMRRecorder.isRecordSupported();
// 不是 amr.isRecordSupported();
```

# 尚未完成的特性

 - [x] ~~使用 Worker 编码解码 AMR。~~（v1.0.9）
 - [x] ~~暂停播放功能。~~（v1.1.0）
 - [ ] 暂停录音功能。
 - [x] ~~播放进度控制。~~（v1.1.0）
 - [x] ~~浏览器兼容性检查（[#9](https://github.com/BenzLeung/benz-amr-recorder/issues/9) [#11](https://github.com/BenzLeung/benz-amr-recorder/issues/11)）。~~（v1.1.0）

# 常见问题

## - 初始化之后如何更换音频？

请重新执行 `new BenzAMRRecorder()` 创建新的对象，然后用新的音频去初始化。

旧的对象只要没有变量引用就会被浏览器作为垃圾回收内存。

如果你的应用本身比较吃内存，或者希望更快地让浏览器回收内存，可以对旧的对象执行一下 `amr.destroy()` （版本 1.1.4），这样这个对象内部所存储的音频数据和文件数据都会被设置为 `null` ，然后这个对象就彻底不能用了。

## 为什么不让旧的对象内部实现更换音频？

1. 对象初始化时会决定该对象是用于录音或用于播放，重新初始化会让内部逻辑变得复杂。
2. 本项目的主要用途是语音聊天，而不是音乐播放，所以项目架构设计成了“每一条语音消息对应一个 `BenzAMRRecorder` 对象”，可节省网络开销，多次播放音频也不消耗网络流量。
3. 多个音频（对象）还可以同时混音播放。
4. ^~~偷懒~~^。

## - 关于跨域

这需要目标服务器（即存放amr文件的服务器）支持跨域，不是修改前端代码能解决的。

基本上如果服务器是CDN的话都能支持。如果不是CDN，那就请自行谷鸽百度一下如何让服务器支持跨域，或者找你们后端或运维同事帮忙。

## - 采样率？

本项目仅支持 amr-nb 格式，这种格式只有一种采样率 8000Hz。因为amr这种格式是专门设计成用于人声录音的，这个采样率足以把人类日常说话的字词句录清楚。对于人类，只有强悍的女高音歌手才能尖叫出接近8000Hz的声音。

另有一种 amr-wb 格式，采样率是 16000Hz，提升采样率可以让人声从“听得清楚”升级成“听得舒服”。人类在讲话的时候会伴随一些呼吸声气流声，这些声音有时候只能用上高采样率才能录进去。

目前本项目不支持 amr-wb。

## - 能支持 amr-wb 格式吗？

不打算支持，因为这会让包体积变大。本项目原本是为了配合微信做语音应用的，微信也没有用到 amr-wb。

已经[有网友fork支持amr-wb了](https://github.com/AcoWaves/benz-amr-recorder)，在此感谢AcoWaves。

## - Webpack 或 babel 打包出错

由于本人已经不参与音频相关的项目，目前暂时没时间Debug了。哪位大佬要是帮忙修复了，欢迎提PR。

**2022-09-26 更新：** 最近有点空，我用 `create-react-app` 创建了一个 React 初始项目，尝试把 `BenzAMRRecorder` 用于 React 。没有发现任何问题。

## - 本项目与Benz有什么关系？

Benz 是我本人从中学用到现在的英文名字，与某汽车厂商**完全无关**。

## - 似乎好久没有更新了？

由于本人已经不参与音频相关的项目，目前暂时没时间做更新了。而且我个人感觉本项目也没什么重要的东西需要更新了。（项目的基础 [amr.js](https://github.com/jpemartins/amr.js) 也是n年没有更新了。）欢迎大佬们提PR。

**2022-09-26 更新：** 做个小更新，主要试一试 Webpack 和 babel，当然我也没有手工地配置 Webpack 和 babel，就直接用脚手架 `create-react-app` 测试，然而用着很正常，没有发现问题。另外增加了事件取消绑定功能，以及增加一个~~好像没什么用的~~ `destroy()` 方法。

# 许可

MIT.
