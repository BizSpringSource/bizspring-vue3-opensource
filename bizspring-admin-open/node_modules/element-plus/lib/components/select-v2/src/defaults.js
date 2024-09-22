'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@popperjs/core');
require('../../../utils/index.js');
require('../../tooltip/index.js');
var iconsVue = require('@element-plus/icons-vue');
var content = require('../../tooltip/src/content.js');
var validator = require('../../../utils/vue/validator.js');
var runtime = require('../../../utils/vue/props/runtime.js');

const SelectProps = {
  allowCreate: Boolean,
  autocomplete: {
    type: String,
    default: "none"
  },
  automaticDropdown: Boolean,
  clearable: Boolean,
  clearIcon: {
    type: [String, Object],
    default: iconsVue.CircleClose
  },
  effect: {
    type: String,
    default: "light"
  },
  collapseTags: Boolean,
  collapseTagsTooltip: {
    type: Boolean,
    default: false
  },
  defaultFirstOption: Boolean,
  disabled: Boolean,
  estimatedOptionHeight: {
    type: Number,
    default: void 0
  },
  filterable: Boolean,
  filterMethod: Function,
  height: {
    type: Number,
    default: 170
  },
  itemHeight: {
    type: Number,
    default: 34
  },
  id: String,
  loading: Boolean,
  loadingText: String,
  label: String,
  modelValue: [Array, String, Number, Boolean, Object],
  multiple: Boolean,
  multipleLimit: {
    type: Number,
    default: 0
  },
  name: String,
  noDataText: String,
  noMatchText: String,
  remoteMethod: Function,
  reserveKeyword: {
    type: Boolean,
    default: true
  },
  options: {
    type: Array,
    required: true
  },
  placeholder: {
    type: String
  },
  teleported: content.useTooltipContentProps.teleported,
  persistent: {
    type: Boolean,
    default: true
  },
  popperClass: {
    type: String,
    default: ""
  },
  popperOptions: {
    type: Object,
    default: () => ({})
  },
  remote: Boolean,
  size: {
    type: String,
    validator: validator.isValidComponentSize
  },
  valueKey: {
    type: String,
    default: "value"
  },
  scrollbarAlwaysOn: {
    type: Boolean,
    default: false
  },
  validateEvent: {
    type: Boolean,
    default: true
  },
  placement: {
    type: runtime.definePropType(String),
    values: core.placements,
    default: "bottom-start"
  }
};
const OptionProps = {
  data: Array,
  disabled: Boolean,
  hovering: Boolean,
  item: Object,
  index: Number,
  style: Object,
  selected: Boolean,
  created: Boolean
};

exports.OptionProps = OptionProps;
exports.SelectProps = SelectProps;
//# sourceMappingURL=defaults.js.map
