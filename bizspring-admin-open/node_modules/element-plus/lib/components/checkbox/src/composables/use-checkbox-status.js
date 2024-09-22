'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var lodashUnified = require('lodash-unified');
require('../../../../hooks/index.js');
require('../../../../utils/index.js');
require('../../../../tokens/index.js');
var checkbox = require('../../../../tokens/checkbox.js');
var core = require('@vueuse/core');
var shared = require('@vue/shared');
var index = require('../../../../hooks/use-common-props/index.js');

const useCheckboxStatus = (props, slots, { model }) => {
  const checkboxGroup = vue.inject(checkbox.checkboxGroupContextKey, void 0);
  const isFocused = vue.ref(false);
  const isChecked = vue.computed(() => {
    const value = model.value;
    if (core.isBoolean(value)) {
      return value;
    } else if (shared.isArray(value)) {
      if (shared.isObject(props.label)) {
        return value.map(vue.toRaw).some((o) => lodashUnified.isEqual(o, props.label));
      } else {
        return value.map(vue.toRaw).includes(props.label);
      }
    } else if (value !== null && value !== void 0) {
      return value === props.trueLabel;
    } else {
      return !!value;
    }
  });
  const checkboxButtonSize = index.useSize(vue.computed(() => {
    var _a;
    return (_a = checkboxGroup == null ? void 0 : checkboxGroup.size) == null ? void 0 : _a.value;
  }), {
    prop: true
  });
  const checkboxSize = index.useSize(vue.computed(() => {
    var _a;
    return (_a = checkboxGroup == null ? void 0 : checkboxGroup.size) == null ? void 0 : _a.value;
  }));
  const hasOwnLabel = vue.computed(() => {
    return !!(slots.default || props.label);
  });
  return {
    checkboxButtonSize,
    isChecked,
    isFocused,
    checkboxSize,
    hasOwnLabel
  };
};

exports.useCheckboxStatus = useCheckboxStatus;
//# sourceMappingURL=use-checkbox-status.js.map
