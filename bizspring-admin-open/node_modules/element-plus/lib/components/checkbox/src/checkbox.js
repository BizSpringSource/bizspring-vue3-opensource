'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../../constants/index.js');
require('../../../hooks/index.js');
require('../../../utils/index.js');
var index = require('../../../hooks/use-common-props/index.js');
var event = require('../../../constants/event.js');
var shared = require('@vue/shared');
var core = require('@vueuse/core');

const checkboxProps = {
  modelValue: {
    type: [Number, String, Boolean],
    default: void 0
  },
  label: {
    type: [String, Boolean, Number, Object]
  },
  indeterminate: Boolean,
  disabled: Boolean,
  checked: Boolean,
  name: {
    type: String,
    default: void 0
  },
  trueLabel: {
    type: [String, Number],
    default: void 0
  },
  falseLabel: {
    type: [String, Number],
    default: void 0
  },
  id: {
    type: String,
    default: void 0
  },
  controls: {
    type: String,
    default: void 0
  },
  border: Boolean,
  size: index.useSizeProp,
  tabindex: [String, Number],
  validateEvent: {
    type: Boolean,
    default: true
  }
};
const checkboxEmits = {
  [event.UPDATE_MODEL_EVENT]: (val) => shared.isString(val) || core.isNumber(val) || core.isBoolean(val),
  change: (val) => shared.isString(val) || core.isNumber(val) || core.isBoolean(val)
};

exports.checkboxEmits = checkboxEmits;
exports.checkboxProps = checkboxProps;
//# sourceMappingURL=checkbox.js.map
