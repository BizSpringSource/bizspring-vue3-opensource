import { isRef, watch, onScopeDispose } from 'vue';
import { computed } from '@vue/reactivity';
import { isClient } from '@vueuse/core';
import '../../utils/index.mjs';
import { useNamespace } from '../use-namespace/index.mjs';
import { throwError } from '../../utils/error.mjs';
import { hasClass, removeClass, getStyle, addClass } from '../../utils/dom/style.mjs';
import { getScrollBarWidth } from '../../utils/dom/scroll.mjs';

const useLockscreen = (trigger) => {
  if (!isRef(trigger)) {
    throwError("[useLockscreen]", "You need to pass a ref param to this function");
  }
  const ns = useNamespace("popup");
  const hiddenCls = computed(() => ns.bm("parent", "hidden"));
  if (!isClient || hasClass(document.body, hiddenCls.value)) {
    return;
  }
  let scrollBarWidth = 0;
  let withoutHiddenClass = false;
  let bodyWidth = "0";
  const cleanup = () => {
    setTimeout(() => {
      removeClass(document.body, hiddenCls.value);
      if (withoutHiddenClass) {
        document.body.style.width = bodyWidth;
      }
    }, 200);
  };
  watch(trigger, (val) => {
    if (!val) {
      cleanup();
      return;
    }
    withoutHiddenClass = !hasClass(document.body, hiddenCls.value);
    if (withoutHiddenClass) {
      bodyWidth = document.body.style.width;
    }
    scrollBarWidth = getScrollBarWidth(ns.namespace.value);
    const bodyHasOverflow = document.documentElement.clientHeight < document.body.scrollHeight;
    const bodyOverflowY = getStyle(document.body, "overflowY");
    if (scrollBarWidth > 0 && (bodyHasOverflow || bodyOverflowY === "scroll") && withoutHiddenClass) {
      document.body.style.width = `calc(100% - ${scrollBarWidth}px)`;
    }
    addClass(document.body, hiddenCls.value);
  });
  onScopeDispose(() => cleanup());
};

export { useLockscreen };
//# sourceMappingURL=index.mjs.map
