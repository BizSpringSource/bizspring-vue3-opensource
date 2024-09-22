'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../../constants/index.js');
require('../../../utils/index.js');
var runtime = require('../../../utils/vue/props/runtime.js');
var size = require('../../../constants/size.js');
var shared = require('@vue/shared');
var core = require('@vueuse/core');

const formProps = runtime.buildProps({
  model: Object,
  rules: {
    type: runtime.definePropType(Object)
  },
  labelPosition: {
    type: String,
    values: ["left", "right", "top"],
    default: "right"
  },
  requireAsteriskPosition: {
    type: String,
    values: ["left", "right"],
    default: "left"
  },
  labelWidth: {
    type: [String, Number],
    default: ""
  },
  labelSuffix: {
    type: String,
    default: ""
  },
  inline: Boolean,
  inlineMessage: Boolean,
  statusIcon: Boolean,
  showMessage: {
    type: Boolean,
    default: true
  },
  size: {
    type: String,
    values: size.componentSizes
  },
  disabled: Boolean,
  validateOnRuleChange: {
    type: Boolean,
    default: true
  },
  hideRequiredAsterisk: {
    type: Boolean,
    default: false
  },
  scrollToError: Boolean
});
const formEmits = {
  validate: (prop, isValid, message) => (shared.isArray(prop) || shared.isString(prop)) && core.isBoolean(isValid) && shared.isString(message)
};

exports.formEmits = formEmits;
exports.formProps = formProps;
//# sourceMappingURL=form.js.map
