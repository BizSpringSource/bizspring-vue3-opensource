var recorderWorker = require('./recorderWorker');
var recorderWorkerStr = recorderWorker.toString()
    .replace(/^\s*function.*?\(\)\s*{/, '')
    .replace(/}\s*$/, '');

// var WORKER_PATH = './recorderWorker.js';

var Recorder = function(source, cfg){
  var config = cfg || {};
  var bufferLen = config.bufferLen || 4096;
  this.context = source.context;
  this.node = (this.context.createScriptProcessor ||
               this.context.createJavaScriptNode).call(this.context,
                                                       bufferLen, 2, 2);
  var worker = new Worker((window.URL || window.webkitURL).createObjectURL(new Blob([recorderWorkerStr], {type:"text/javascript"})));
  worker.onmessage = function(e){
    if (e.data.type === 'blob') {
        currCallbackWithBlob(e.data.data);
    } else {
        currCallbackWithBuffer(e.data.data);
    }
  };

  worker.postMessage({
    command: 'init',
    config: {
      sampleRate: this.context.sampleRate
    }
  });
  var recording = false,
    currCallbackWithBuffer,
    currCallbackWithBlob;

  this.node.onaudioprocess = function(e){
    if (!recording) return;
    worker.postMessage({
      command: 'record',
      buffer: [
        e.inputBuffer.getChannelData(0),
        e.inputBuffer.getChannelData(1)
      ]
    });
  };

  this.configure = function(cfg){
    for (var prop in cfg){
      if (cfg.hasOwnProperty(prop)){
        config[prop] = cfg[prop];
      }
    }
  };

  this.record = function(){
    recording = true;
  };

  this.stop = function(){
    recording = false;
  };

  this.clear = function(){
    worker.postMessage({ command: 'clear' });
  };

  this.getBuffer = function(cb) {
    currCallbackWithBuffer = cb || config.callback;
    worker.postMessage({ command: 'getBuffer' })
  };

  this.exportWAV = function(cb, type){
    currCallbackWithBlob = cb || config.callback;
    type = type || config.type || 'audio/wav';
    if (!currCallbackWithBlob) throw new Error('Callback not set');
    worker.postMessage({
      command: 'exportWAV',
      type: type
    });
  };

  this.release = function() {
    this.stop();
    this.clear();
    this.configure = this.record = this.stop = this.clear = this.getBuffer = this.exportWAV = function () {};
    source.disconnect(this.node);
    this.node.onaudioprocess = null;
    this.node.disconnect();
    worker.terminate();
  };

  source.connect(this.node);
  this.node.connect(this.context.destination);    //this should not be necessary
};

Recorder.forceDownload = function(blob, filename){
  var url = (window.URL || window.webkitURL).createObjectURL(blob);
  var link = window.document.createElement('a');
  link.href = url;
  link.download = filename || 'output.wav';
  var click = document.createEvent("Event");
  click.initEvent("click", true, true);
  link.dispatchEvent(click);
};

module.exports = Recorder;
