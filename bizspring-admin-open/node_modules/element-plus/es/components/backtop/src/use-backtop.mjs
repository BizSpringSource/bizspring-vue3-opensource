import { shallowRef, ref, onMounted } from 'vue';
import { useThrottleFn, useEventListener } from '@vueuse/core';
import '../../../utils/index.mjs';
import { easeInOutCubic } from '../../../utils/animation.mjs';
import { throwError } from '../../../utils/error.mjs';

const useBackTop = (props, emit, componentName) => {
  const el = shallowRef();
  const container = shallowRef();
  const visible = ref(false);
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
        el.value.scrollTop = beginValue * (1 - easeInOutCubic(progress));
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
  const handleScrollThrottled = useThrottleFn(handleScroll, 300, true);
  useEventListener(container, "scroll", handleScrollThrottled);
  onMounted(() => {
    var _a;
    container.value = document;
    el.value = document.documentElement;
    if (props.target) {
      el.value = (_a = document.querySelector(props.target)) != null ? _a : void 0;
      if (!el.value) {
        throwError(componentName, `target does not exist: ${props.target}`);
      }
      container.value = el.value;
    }
  });
  return {
    visible,
    handleClick
  };
};

export { useBackTop };
//# sourceMappingURL=use-backtop.mjs.map
