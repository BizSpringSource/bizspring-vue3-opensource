'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var index = require('../use-global-config/index.js');

const zIndex = vue.ref(0);
const useZIndex = () => {
  const initialZIndex = index.useGlobalConfig("zIndex", 2e3);
  const currentZIndex = vue.computed(() => initialZIndex.value + zIndex.value);
  const nextZIndex = () => {
    zIndex.value++;
    return currentZIndex.value;
  };
  return {
    initialZIndex,
    currentZIndex,
    nextZIndex
  };
};

exports.useZIndex = useZIndex;
//# sourceMappingURL=index.js.map
