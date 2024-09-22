'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@vueuse/core');

const isFirefox = () => core.isClient && /firefox/i.test(window.navigator.userAgent);

exports.isFirefox = isFirefox;
//# sourceMappingURL=browser.js.map
