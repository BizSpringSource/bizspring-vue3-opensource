'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../tokens/index.js');
var popper = require('./popper.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var popper$1 = require('../../../tokens/popper.js');

const __default__ = vue.defineComponent({
  name: "ElPopperRoot",
  inheritAttrs: false
});
const _sfc_main = /* @__PURE__ */ vue.defineComponent({
  ...__default__,
  props: popper.popperProps,
  setup(__props, { expose }) {
    const props = __props;
    const triggerRef = vue.ref();
    const popperInstanceRef = vue.ref();
    const contentRef = vue.ref();
    const referenceRef = vue.ref();
    const role = vue.computed(() => props.role);
    const popperProvides = {
      triggerRef,
      popperInstanceRef,
      contentRef,
      referenceRef,
      role
    };
    expose(popperProvides);
    vue.provide(popper$1.POPPER_INJECTION_KEY, popperProvides);
    return (_ctx, _cache) => {
      return vue.renderSlot(_ctx.$slots, "default");
    };
  }
});
var Popper = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/popper/src/popper.vue"]]);

exports["default"] = Popper;
//# sourceMappingURL=popper2.js.map
