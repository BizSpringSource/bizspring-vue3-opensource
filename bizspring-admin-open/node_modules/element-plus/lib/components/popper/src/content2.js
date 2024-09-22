'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var shared = require('@vue/shared');
var lodashUnified = require('lodash-unified');
var core = require('@popperjs/core');
require('../../focus-trap/index.js');
require('../../../hooks/index.js');
require('../../../tokens/index.js');
require('../../../utils/index.js');
var content = require('./content.js');
var utils = require('./utils.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var popper = require('../../../tokens/popper.js');
var form = require('../../../tokens/form.js');
var index = require('../../../hooks/use-z-index/index.js');
var index$1 = require('../../../hooks/use-namespace/index.js');
var types = require('../../../utils/types.js');
var focusTrap = require('../../focus-trap/src/focus-trap.js');

const __default__ = vue.defineComponent({
  name: "ElPopperContent"
});
const _sfc_main = /* @__PURE__ */ vue.defineComponent({
  ...__default__,
  props: content.popperContentProps,
  emits: content.popperContentEmits,
  setup(__props, { expose, emit }) {
    const props = __props;
    const { popperInstanceRef, contentRef, triggerRef, role } = vue.inject(popper.POPPER_INJECTION_KEY, void 0);
    const formItemContext = vue.inject(form.formItemContextKey, void 0);
    const { nextZIndex } = index.useZIndex();
    const ns = index$1.useNamespace("popper");
    const popperContentRef = vue.ref();
    const focusStartRef = vue.ref("first");
    const arrowRef = vue.ref();
    const arrowOffset = vue.ref();
    vue.provide(popper.POPPER_CONTENT_INJECTION_KEY, {
      arrowRef,
      arrowOffset
    });
    if (formItemContext && (formItemContext.addInputId || formItemContext.removeInputId)) {
      vue.provide(form.formItemContextKey, {
        ...formItemContext,
        addInputId: shared.NOOP,
        removeInputId: shared.NOOP
      });
    }
    const contentZIndex = vue.ref(props.zIndex || nextZIndex());
    const trapped = vue.ref(false);
    let triggerTargetAriaStopWatch = void 0;
    const computedReference = vue.computed(() => utils.unwrapMeasurableEl(props.referenceEl) || vue.unref(triggerRef));
    const contentStyle = vue.computed(() => [{ zIndex: vue.unref(contentZIndex) }, props.popperStyle]);
    const contentClass = vue.computed(() => [
      ns.b(),
      ns.is("pure", props.pure),
      ns.is(props.effect),
      props.popperClass
    ]);
    const ariaModal = vue.computed(() => {
      return role && role.value === "dialog" ? "false" : void 0;
    });
    const createPopperInstance = ({
      referenceEl,
      popperContentEl,
      arrowEl
    }) => {
      const options = utils.buildPopperOptions(props, {
        arrowEl,
        arrowOffset: vue.unref(arrowOffset)
      });
      return core.createPopper(referenceEl, popperContentEl, options);
    };
    const updatePopper = (shouldUpdateZIndex = true) => {
      var _a;
      (_a = vue.unref(popperInstanceRef)) == null ? void 0 : _a.update();
      shouldUpdateZIndex && (contentZIndex.value = props.zIndex || nextZIndex());
    };
    const togglePopperAlive = () => {
      var _a, _b;
      const monitorable = { name: "eventListeners", enabled: props.visible };
      (_b = (_a = vue.unref(popperInstanceRef)) == null ? void 0 : _a.setOptions) == null ? void 0 : _b.call(_a, (options) => ({
        ...options,
        modifiers: [...options.modifiers || [], monitorable]
      }));
      updatePopper(false);
      if (props.visible && props.focusOnShow) {
        trapped.value = true;
      } else if (props.visible === false) {
        trapped.value = false;
      }
    };
    const onFocusAfterTrapped = () => {
      emit("focus");
    };
    const onFocusAfterReleased = (event) => {
      var _a;
      if (((_a = event.detail) == null ? void 0 : _a.focusReason) !== "pointer") {
        focusStartRef.value = "first";
        emit("blur");
      }
    };
    const onFocusInTrap = (event) => {
      if (props.visible && !trapped.value) {
        if (event.target) {
          focusStartRef.value = event.target;
        }
        trapped.value = true;
      }
    };
    const onFocusoutPrevented = (event) => {
      if (!props.trapping) {
        if (event.detail.focusReason === "pointer") {
          event.preventDefault();
        }
        trapped.value = false;
      }
    };
    const onReleaseRequested = () => {
      trapped.value = false;
      emit("close");
    };
    vue.onMounted(() => {
      let updateHandle;
      vue.watch(computedReference, (referenceEl) => {
        var _a;
        updateHandle == null ? void 0 : updateHandle();
        const popperInstance = vue.unref(popperInstanceRef);
        (_a = popperInstance == null ? void 0 : popperInstance.destroy) == null ? void 0 : _a.call(popperInstance);
        if (referenceEl) {
          const popperContentEl = vue.unref(popperContentRef);
          contentRef.value = popperContentEl;
          popperInstanceRef.value = createPopperInstance({
            referenceEl,
            popperContentEl,
            arrowEl: vue.unref(arrowRef)
          });
          updateHandle = vue.watch(() => referenceEl.getBoundingClientRect(), () => updatePopper(), {
            immediate: true
          });
        } else {
          popperInstanceRef.value = void 0;
        }
      }, {
        immediate: true
      });
      vue.watch(() => props.triggerTargetEl, (triggerTargetEl, prevTriggerTargetEl) => {
        triggerTargetAriaStopWatch == null ? void 0 : triggerTargetAriaStopWatch();
        triggerTargetAriaStopWatch = void 0;
        const el = vue.unref(triggerTargetEl || popperContentRef.value);
        const prevEl = vue.unref(prevTriggerTargetEl || popperContentRef.value);
        if (types.isElement(el)) {
          triggerTargetAriaStopWatch = vue.watch([role, () => props.ariaLabel, ariaModal, () => props.id], (watches) => {
            ;
            ["role", "aria-label", "aria-modal", "id"].forEach((key, idx) => {
              lodashUnified.isNil(watches[idx]) ? el.removeAttribute(key) : el.setAttribute(key, watches[idx]);
            });
          }, { immediate: true });
        }
        if (prevEl !== el && types.isElement(prevEl)) {
          ;
          ["role", "aria-label", "aria-modal", "id"].forEach((key) => {
            prevEl.removeAttribute(key);
          });
        }
      }, { immediate: true });
      vue.watch(() => props.visible, togglePopperAlive, { immediate: true });
      vue.watch(() => utils.buildPopperOptions(props, {
        arrowEl: vue.unref(arrowRef),
        arrowOffset: vue.unref(arrowOffset)
      }), (option) => {
        var _a;
        return (_a = popperInstanceRef.value) == null ? void 0 : _a.setOptions(option);
      });
    });
    vue.onBeforeUnmount(() => {
      triggerTargetAriaStopWatch == null ? void 0 : triggerTargetAriaStopWatch();
      triggerTargetAriaStopWatch = void 0;
    });
    expose({
      popperContentRef,
      popperInstanceRef,
      updatePopper,
      contentStyle
    });
    return (_ctx, _cache) => {
      return vue.openBlock(), vue.createElementBlock("div", {
        ref_key: "popperContentRef",
        ref: popperContentRef,
        style: vue.normalizeStyle(vue.unref(contentStyle)),
        class: vue.normalizeClass(vue.unref(contentClass)),
        tabindex: "-1",
        onMouseenter: _cache[0] || (_cache[0] = (e) => _ctx.$emit("mouseenter", e)),
        onMouseleave: _cache[1] || (_cache[1] = (e) => _ctx.$emit("mouseleave", e))
      }, [
        vue.createVNode(vue.unref(focusTrap["default"]), {
          trapped: trapped.value,
          "trap-on-focus-in": true,
          "focus-trap-el": popperContentRef.value,
          "focus-start-el": focusStartRef.value,
          onFocusAfterTrapped,
          onFocusAfterReleased,
          onFocusin: onFocusInTrap,
          onFocusoutPrevented,
          onReleaseRequested
        }, {
          default: vue.withCtx(() => [
            vue.renderSlot(_ctx.$slots, "default")
          ]),
          _: 3
        }, 8, ["trapped", "focus-trap-el", "focus-start-el"])
      ], 38);
    };
  }
});
var ElPopperContent = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/popper/src/content.vue"]]);

exports["default"] = ElPopperContent;
//# sourceMappingURL=content2.js.map
