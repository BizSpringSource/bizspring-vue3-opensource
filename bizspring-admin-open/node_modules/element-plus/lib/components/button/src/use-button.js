'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../hooks/index.js');
require('../../../tokens/index.js');
var index = require('../../../hooks/use-deprecated/index.js');
var button = require('../../../tokens/button.js');
var index$1 = require('../../../hooks/use-global-config/index.js');
var index$2 = require('../../../hooks/use-form-item/index.js');
var index$3 = require('../../../hooks/use-common-props/index.js');

const useButton = (props, emit) => {
  index.useDeprecated({
    from: "type.text",
    replacement: "link",
    version: "3.0.0",
    scope: "props",
    ref: "https://element-plus.org/en-US/component/button.html#button-attributes"
  }, vue.computed(() => props.type === "text"));
  const buttonGroupContext = vue.inject(button.buttonGroupContextKey, void 0);
  const globalConfig = index$1.useGlobalConfig("button");
  const { form } = index$2.useFormItem();
  const _size = index$3.useSize(vue.computed(() => buttonGroupContext == null ? void 0 : buttonGroupContext.size));
  const _disabled = index$3.useDisabled();
  const _ref = vue.ref();
  const slots = vue.useSlots();
  const _type = vue.computed(() => props.type || (buttonGroupContext == null ? void 0 : buttonGroupContext.type) || "");
  const autoInsertSpace = vue.computed(() => {
    var _a, _b, _c;
    return (_c = (_b = props.autoInsertSpace) != null ? _b : (_a = globalConfig.value) == null ? void 0 : _a.autoInsertSpace) != null ? _c : false;
  });
  const shouldAddSpace = vue.computed(() => {
    var _a;
    const defaultSlot = (_a = slots.default) == null ? void 0 : _a.call(slots);
    if (autoInsertSpace.value && (defaultSlot == null ? void 0 : defaultSlot.length) === 1) {
      const slot = defaultSlot[0];
      if ((slot == null ? void 0 : slot.type) === vue.Text) {
        const text = slot.children;
        return /^\p{Unified_Ideograph}{2}$/u.test(text.trim());
      }
    }
    return false;
  });
  const handleClick = (evt) => {
    if (props.nativeType === "reset") {
      form == null ? void 0 : form.resetFields();
    }
    emit("click", evt);
  };
  return {
    _disabled,
    _size,
    _type,
    _ref,
    shouldAddSpace,
    handleClick
  };
};

exports.useButton = useButton;
//# sourceMappingURL=use-button.js.map
