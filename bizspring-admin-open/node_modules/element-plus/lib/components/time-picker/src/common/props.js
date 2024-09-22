'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../../../utils/index.js');
require('../../../../hooks/index.js');
var iconsVue = require('@element-plus/icons-vue');
var shared = require('../props/shared.js');
var runtime = require('../../../../utils/vue/props/runtime.js');
var index = require('../../../../hooks/use-common-props/index.js');

const timePickerDefaultProps = runtime.buildProps({
  id: {
    type: runtime.definePropType([Array, String])
  },
  name: {
    type: runtime.definePropType([Array, String]),
    default: ""
  },
  popperClass: {
    type: String,
    default: ""
  },
  format: String,
  valueFormat: String,
  type: {
    type: String,
    default: ""
  },
  clearable: {
    type: Boolean,
    default: true
  },
  clearIcon: {
    type: runtime.definePropType([String, Object]),
    default: iconsVue.CircleClose
  },
  editable: {
    type: Boolean,
    default: true
  },
  prefixIcon: {
    type: runtime.definePropType([String, Object]),
    default: ""
  },
  size: index.useSizeProp,
  readonly: {
    type: Boolean,
    default: false
  },
  disabled: {
    type: Boolean,
    default: false
  },
  placeholder: {
    type: String,
    default: ""
  },
  popperOptions: {
    type: runtime.definePropType(Object),
    default: () => ({})
  },
  modelValue: {
    type: runtime.definePropType([Date, Array, String, Number]),
    default: ""
  },
  rangeSeparator: {
    type: String,
    default: "-"
  },
  startPlaceholder: String,
  endPlaceholder: String,
  defaultValue: {
    type: runtime.definePropType([Date, Array])
  },
  defaultTime: {
    type: runtime.definePropType([Date, Array])
  },
  isRange: {
    type: Boolean,
    default: false
  },
  ...shared.disabledTimeListsProps,
  disabledDate: {
    type: Function
  },
  cellClassName: {
    type: Function
  },
  shortcuts: {
    type: Array,
    default: () => []
  },
  arrowControl: {
    type: Boolean,
    default: false
  },
  label: {
    type: String,
    default: void 0
  },
  tabindex: {
    type: runtime.definePropType([String, Number]),
    default: 0
  },
  validateEvent: {
    type: Boolean,
    default: true
  },
  unlinkPanels: Boolean
});

exports.timePickerDefaultProps = timePickerDefaultProps;
//# sourceMappingURL=props.js.map
