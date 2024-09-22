'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../utils/index.js');
var index = require('../use-timeout/index.js');
var runtime = require('../../utils/vue/props/runtime.js');

const useDelayedToggleProps = runtime.buildProps({
  showAfter: {
    type: Number,
    default: 0
  },
  hideAfter: {
    type: Number,
    default: 200
  }
});
const useDelayedToggle = ({
  showAfter,
  hideAfter,
  open,
  close
}) => {
  const { registerTimeout } = index.useTimeout();
  const onOpen = (event) => {
    registerTimeout(() => {
      open(event);
    }, vue.unref(showAfter));
  };
  const onClose = (event) => {
    registerTimeout(() => {
      close(event);
    }, vue.unref(hideAfter));
  };
  return {
    onOpen,
    onClose
  };
};

exports.useDelayedToggle = useDelayedToggle;
exports.useDelayedToggleProps = useDelayedToggleProps;
//# sourceMappingURL=index.js.map
