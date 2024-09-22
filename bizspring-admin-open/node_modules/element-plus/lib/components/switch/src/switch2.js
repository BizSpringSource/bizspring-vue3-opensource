'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var shared = require('@vue/shared');
require('../../../utils/index.js');
var index$4 = require('../../icon/index.js');
var iconsVue = require('@element-plus/icons-vue');
require('../../../constants/index.js');
require('../../../hooks/index.js');
var _switch = require('./switch.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var index = require('../../../hooks/use-form-item/index.js');
var index$1 = require('../../../hooks/use-common-props/index.js');
var index$2 = require('../../../hooks/use-namespace/index.js');
var index$3 = require('../../../hooks/use-deprecated/index.js');
var style = require('../../../utils/dom/style.js');
var event = require('../../../constants/event.js');
var error = require('../../../utils/error.js');
var core = require('@vueuse/core');

const _hoisted_1 = ["onClick"];
const _hoisted_2 = ["id", "aria-checked", "aria-disabled", "name", "true-value", "false-value", "disabled", "tabindex", "onKeydown"];
const _hoisted_3 = ["aria-hidden"];
const _hoisted_4 = ["aria-hidden"];
const _hoisted_5 = ["aria-hidden"];
const COMPONENT_NAME = "ElSwitch";
const __default__ = vue.defineComponent({
  name: COMPONENT_NAME
});
const _sfc_main = /* @__PURE__ */ vue.defineComponent({
  ...__default__,
  props: _switch.switchProps,
  emits: _switch.switchEmits,
  setup(__props, { expose, emit }) {
    const props = __props;
    const vm = vue.getCurrentInstance();
    const { formItem } = index.useFormItem();
    const switchSize = index$1.useSize();
    const ns = index$2.useNamespace("switch");
    index$3.useDeprecated({
      from: '"value"',
      replacement: '"model-value" or "v-model"',
      scope: COMPONENT_NAME,
      version: "2.3.0",
      ref: "https://element-plus.org/en-US/component/switch.html#attributes",
      type: "Attribute"
    }, vue.computed(() => {
      var _a;
      return !!((_a = vm.vnode.props) == null ? void 0 : _a.value);
    }));
    const { inputId } = index.useFormItemInputId(props, {
      formItemContext: formItem
    });
    const switchDisabled = index$1.useDisabled(vue.computed(() => props.loading));
    const isControlled = vue.ref(props.modelValue !== false);
    const input = vue.ref();
    const core$1 = vue.ref();
    const switchKls = vue.computed(() => [
      ns.b(),
      ns.m(switchSize.value),
      ns.is("disabled", switchDisabled.value),
      ns.is("checked", checked.value)
    ]);
    const coreStyle = vue.computed(() => ({
      width: style.addUnit(props.width)
    }));
    vue.watch(() => props.modelValue, () => {
      isControlled.value = true;
    });
    vue.watch(() => props.value, () => {
      isControlled.value = false;
    });
    const actualValue = vue.computed(() => {
      return isControlled.value ? props.modelValue : props.value;
    });
    const checked = vue.computed(() => actualValue.value === props.activeValue);
    if (![props.activeValue, props.inactiveValue].includes(actualValue.value)) {
      emit(event.UPDATE_MODEL_EVENT, props.inactiveValue);
      emit(event.CHANGE_EVENT, props.inactiveValue);
      emit(event.INPUT_EVENT, props.inactiveValue);
    }
    vue.watch(checked, (val) => {
      var _a;
      input.value.checked = val;
      if (props.validateEvent) {
        (_a = formItem == null ? void 0 : formItem.validate) == null ? void 0 : _a.call(formItem, "change").catch((err) => error.debugWarn(err));
      }
    });
    const handleChange = () => {
      const val = checked.value ? props.inactiveValue : props.activeValue;
      emit(event.UPDATE_MODEL_EVENT, val);
      emit(event.CHANGE_EVENT, val);
      emit(event.INPUT_EVENT, val);
      vue.nextTick(() => {
        input.value.checked = checked.value;
      });
    };
    const switchValue = () => {
      if (switchDisabled.value)
        return;
      const { beforeChange } = props;
      if (!beforeChange) {
        handleChange();
        return;
      }
      const shouldChange = beforeChange();
      const isPromiseOrBool = [
        shared.isPromise(shouldChange),
        core.isBoolean(shouldChange)
      ].includes(true);
      if (!isPromiseOrBool) {
        error.throwError(COMPONENT_NAME, "beforeChange must return type `Promise<boolean>` or `boolean`");
      }
      if (shared.isPromise(shouldChange)) {
        shouldChange.then((result) => {
          if (result) {
            handleChange();
          }
        }).catch((e) => {
          error.debugWarn(COMPONENT_NAME, `some error occurred: ${e}`);
        });
      } else if (shouldChange) {
        handleChange();
      }
    };
    const styles = vue.computed(() => {
      return ns.cssVarBlock({
        ...props.activeColor ? { "on-color": props.activeColor } : null,
        ...props.inactiveColor ? { "off-color": props.inactiveColor } : null,
        ...props.borderColor ? { "border-color": props.borderColor } : null
      });
    });
    const focus = () => {
      var _a, _b;
      (_b = (_a = input.value) == null ? void 0 : _a.focus) == null ? void 0 : _b.call(_a);
    };
    vue.onMounted(() => {
      input.value.checked = checked.value;
    });
    expose({
      focus,
      checked
    });
    return (_ctx, _cache) => {
      return vue.openBlock(), vue.createElementBlock("div", {
        class: vue.normalizeClass(vue.unref(switchKls)),
        style: vue.normalizeStyle(vue.unref(styles)),
        onClick: vue.withModifiers(switchValue, ["prevent"])
      }, [
        vue.createElementVNode("input", {
          id: vue.unref(inputId),
          ref_key: "input",
          ref: input,
          class: vue.normalizeClass(vue.unref(ns).e("input")),
          type: "checkbox",
          role: "switch",
          "aria-checked": vue.unref(checked),
          "aria-disabled": vue.unref(switchDisabled),
          name: _ctx.name,
          "true-value": _ctx.activeValue,
          "false-value": _ctx.inactiveValue,
          disabled: vue.unref(switchDisabled),
          tabindex: _ctx.tabindex,
          onChange: handleChange,
          onKeydown: vue.withKeys(switchValue, ["enter"])
        }, null, 42, _hoisted_2),
        !_ctx.inlinePrompt && (_ctx.inactiveIcon || _ctx.inactiveText) ? (vue.openBlock(), vue.createElementBlock("span", {
          key: 0,
          class: vue.normalizeClass([
            vue.unref(ns).e("label"),
            vue.unref(ns).em("label", "left"),
            vue.unref(ns).is("active", !vue.unref(checked))
          ])
        }, [
          _ctx.inactiveIcon ? (vue.openBlock(), vue.createBlock(vue.unref(index$4.ElIcon), { key: 0 }, {
            default: vue.withCtx(() => [
              (vue.openBlock(), vue.createBlock(vue.resolveDynamicComponent(_ctx.inactiveIcon)))
            ]),
            _: 1
          })) : vue.createCommentVNode("v-if", true),
          !_ctx.inactiveIcon && _ctx.inactiveText ? (vue.openBlock(), vue.createElementBlock("span", {
            key: 1,
            "aria-hidden": vue.unref(checked)
          }, vue.toDisplayString(_ctx.inactiveText), 9, _hoisted_3)) : vue.createCommentVNode("v-if", true)
        ], 2)) : vue.createCommentVNode("v-if", true),
        vue.createElementVNode("span", {
          ref_key: "core",
          ref: core$1,
          class: vue.normalizeClass(vue.unref(ns).e("core")),
          style: vue.normalizeStyle(vue.unref(coreStyle))
        }, [
          _ctx.inlinePrompt ? (vue.openBlock(), vue.createElementBlock("div", {
            key: 0,
            class: vue.normalizeClass(vue.unref(ns).e("inner"))
          }, [
            _ctx.activeIcon || _ctx.inactiveIcon ? (vue.openBlock(), vue.createBlock(vue.unref(index$4.ElIcon), {
              key: 0,
              class: vue.normalizeClass(vue.unref(ns).is("icon"))
            }, {
              default: vue.withCtx(() => [
                (vue.openBlock(), vue.createBlock(vue.resolveDynamicComponent(vue.unref(checked) ? _ctx.activeIcon : _ctx.inactiveIcon)))
              ]),
              _: 1
            }, 8, ["class"])) : _ctx.activeText || _ctx.inactiveText ? (vue.openBlock(), vue.createElementBlock("span", {
              key: 1,
              class: vue.normalizeClass(vue.unref(ns).is("text")),
              "aria-hidden": !vue.unref(checked)
            }, vue.toDisplayString(vue.unref(checked) ? _ctx.activeText : _ctx.inactiveText), 11, _hoisted_4)) : vue.createCommentVNode("v-if", true)
          ], 2)) : vue.createCommentVNode("v-if", true),
          vue.createElementVNode("div", {
            class: vue.normalizeClass(vue.unref(ns).e("action"))
          }, [
            _ctx.loading ? (vue.openBlock(), vue.createBlock(vue.unref(index$4.ElIcon), {
              key: 0,
              class: vue.normalizeClass(vue.unref(ns).is("loading"))
            }, {
              default: vue.withCtx(() => [
                vue.createVNode(vue.unref(iconsVue.Loading))
              ]),
              _: 1
            }, 8, ["class"])) : vue.createCommentVNode("v-if", true)
          ], 2)
        ], 6),
        !_ctx.inlinePrompt && (_ctx.activeIcon || _ctx.activeText) ? (vue.openBlock(), vue.createElementBlock("span", {
          key: 1,
          class: vue.normalizeClass([
            vue.unref(ns).e("label"),
            vue.unref(ns).em("label", "right"),
            vue.unref(ns).is("active", vue.unref(checked))
          ])
        }, [
          _ctx.activeIcon ? (vue.openBlock(), vue.createBlock(vue.unref(index$4.ElIcon), { key: 0 }, {
            default: vue.withCtx(() => [
              (vue.openBlock(), vue.createBlock(vue.resolveDynamicComponent(_ctx.activeIcon)))
            ]),
            _: 1
          })) : vue.createCommentVNode("v-if", true),
          !_ctx.activeIcon && _ctx.activeText ? (vue.openBlock(), vue.createElementBlock("span", {
            key: 1,
            "aria-hidden": !vue.unref(checked)
          }, vue.toDisplayString(_ctx.activeText), 9, _hoisted_5)) : vue.createCommentVNode("v-if", true)
        ], 2)) : vue.createCommentVNode("v-if", true)
      ], 14, _hoisted_1);
    };
  }
});
var Switch = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/switch/src/switch.vue"]]);

exports["default"] = Switch;
//# sourceMappingURL=switch2.js.map
