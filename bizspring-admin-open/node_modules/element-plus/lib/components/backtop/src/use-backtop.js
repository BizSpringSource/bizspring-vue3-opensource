'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var core = require('@vueuse/core');
require('../../../utils/index.js');
var animation = require('../../../utils/animation.js');
var error = require('../../../utils/error.js');

const useBackTop = (props, emit, componentName) => {
  const el = vue.shallowRef();
  const container = vue.shallowRef();
  const visible = vue.ref(false);
  const scrollToTop = () => {
    if (!el.value)
      return;
    const beginTime = Date.now();
    const beginValue = el.value.scrollTop;
    const frameFunc = () => {
      if (!el.value)
        return;
      const progress = (Date.now() - beginTime) / 500;
      if (progress < 1) {
        el.value.scrollTop = beginValue * (1 - animation.easeInOutCubic(progress));
        requestAnimationFrame(frameFunc);
      } else {
        el.value.scrollTop = 0;
      }
    };
    requestAnimationFrame(frameFunc);
  };
  const handleScroll = () => {
    if (el.value)
      visible.value = el.value.scrollTop >= props.visibilityHeight;
  };
  const handleClick = (event) => {
    scrollToTop();
    emit("click", event);
  };
  const handleScrollThrottled = core.useThrottleFn(handleScroll, 300, true);
  core.useEventListener(container, "scroll", handleScrollThrottled);
  vue.onMounted(() => {
    var _a;
    container.value = document;
    el.value = document.documentElement;
    if (props.target) {
      el.value = (_a = document.querySelector(props.target)) != null ? _a : void 0;
      if (!el.value) {
        error.throwError(componentName, `target does not exist: ${props.target}`);
      }
      container.value = el.value;
    }
  });
  return {
    visible,
    handleClick
  };
};

exports.useBackTop = useBackTop;
//# sourceMappingURL=use-backtop.js.map
