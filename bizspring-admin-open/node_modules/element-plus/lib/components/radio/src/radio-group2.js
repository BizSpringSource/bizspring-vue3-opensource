'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../constants/index.js');
require('../../../tokens/index.js');
require('../../../hooks/index.js');
require('../../../utils/index.js');
var radioGroup = require('./radio-group.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var index = require('../../../hooks/use-namespace/index.js');
var index$1 = require('../../../hooks/use-id/index.js');
var index$2 = require('../../../hooks/use-form-item/index.js');
var event = require('../../../constants/event.js');
var radio = require('../../../tokens/radio.js');
var error = require('../../../utils/error.js');

const _hoisted_1 = ["id", "aria-label", "aria-labelledby"];
const __default__ = vue.defineComponent({
  name: "ElRadioGroup"
});
const _sfc_main = /* @__PURE__ */ vue.defineComponent({
  ...__default__,
  props: radioGroup.radioGroupProps,
  emits: radioGroup.radioGroupEmits,
  setup(__props, { emit }) {
    const props = __props;
    const ns = index.useNamespace("radio");
    const radioId = index$1.useId();
    const radioGroupRef = vue.ref();
    const { formItem } = index$2.useFormItem();
    const { inputId: groupId, isLabeledByFormItem } = index$2.useFormItemInputId(props, {
      formItemContext: formItem
    });
    const changeEvent = (value) => {
      emit(event.UPDATE_MODEL_EVENT, value);
      vue.nextTick(() => emit("change", value));
    };
    vue.onMounted(() => {
      const radios = radioGroupRef.value.querySelectorAll("[type=radio]");
      const firstLabel = radios[0];
      if (!Array.from(radios).some((radio) => radio.checked) && firstLabel) {
        firstLabel.tabIndex = 0;
      }
    });
    const name = vue.computed(() => {
      return props.name || radioId.value;
    });
    vue.provide(radio.radioGroupKey, vue.reactive({
      ...vue.toRefs(props),
      changeEvent,
      name
    }));
    vue.watch(() => props.modelValue, () => {
      if (props.validateEvent) {
        formItem == null ? void 0 : formItem.validate("change").catch((err) => error.debugWarn(err));
      }
    });
    return (_ctx, _cache) => {
      return vue.openBlock(), vue.createElementBlock("div", {
        id: vue.unref(groupId),
        ref_key: "radioGroupRef",
        ref: radioGroupRef,
        class: vue.normalizeClass(vue.unref(ns).b("group")),
        role: "radiogroup",
        "aria-label": !vue.unref(isLabeledByFormItem) ? _ctx.label || "radio-group" : void 0,
        "aria-labelledby": vue.unref(isLabeledByFormItem) ? vue.unref(formItem).labelId : void 0
      }, [
        vue.renderSlot(_ctx.$slots, "default")
      ], 10, _hoisted_1);
    };
  }
});
var RadioGroup = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/radio/src/radio-group.vue"]]);

exports["default"] = RadioGroup;
//# sourceMappingURL=radio-group2.js.map
