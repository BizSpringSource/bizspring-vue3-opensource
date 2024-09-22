'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../../hooks/index.js');
require('../../../../utils/index.js');
require('../../../../tokens/index.js');
var checkbox = require('../../../../tokens/checkbox.js');
var types = require('../../../../utils/types.js');
var index = require('../../../../hooks/use-common-props/index.js');

const useCheckboxDisabled = ({
  model,
  isChecked
}) => {
  const checkboxGroup = vue.inject(checkbox.checkboxGroupContextKey, void 0);
  const isLimitDisabled = vue.computed(() => {
    var _a, _b;
    const max = (_a = checkboxGroup == null ? void 0 : checkboxGroup.max) == null ? void 0 : _a.value;
    const min = (_b = checkboxGroup == null ? void 0 : checkboxGroup.min) == null ? void 0 : _b.value;
    return !types.isUndefined(max) && model.value.length >= max && !isChecked.value || !types.isUndefined(min) && model.value.length <= min && isChecked.value;
  });
  const isDisabled = index.useDisabled(vue.computed(() => (checkboxGroup == null ? void 0 : checkboxGroup.disabled.value) || isLimitDisabled.value));
  return {
    isDisabled,
    isLimitDisabled
  };
};

exports.useCheckboxDisabled = useCheckboxDisabled;
//# sourceMappingURL=use-checkbox-disabled.js.map
