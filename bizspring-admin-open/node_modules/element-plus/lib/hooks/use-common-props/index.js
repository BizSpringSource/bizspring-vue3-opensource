'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../tokens/index.js');
require('../../utils/index.js');
require('../../constants/index.js');
var index = require('../use-prop/index.js');
var index$1 = require('../use-global-config/index.js');
var runtime = require('../../utils/vue/props/runtime.js');
var size = require('../../constants/size.js');
var form = require('../../tokens/form.js');

const useSizeProp = runtime.buildProp({
  type: String,
  values: size.componentSizes,
  required: false
});
const useSize = (fallback, ignore = {}) => {
  const emptyRef = vue.ref(void 0);
  const size = ignore.prop ? emptyRef : index.useProp("size");
  const globalConfig = ignore.global ? emptyRef : index$1.useGlobalConfig("size");
  const form$1 = ignore.form ? { size: void 0 } : vue.inject(form.formContextKey, void 0);
  const formItem = ignore.formItem ? { size: void 0 } : vue.inject(form.formItemContextKey, void 0);
  return vue.computed(() => size.value || vue.unref(fallback) || (formItem == null ? void 0 : formItem.size) || (form$1 == null ? void 0 : form$1.size) || globalConfig.value || "");
};
const useDisabled = (fallback) => {
  const disabled = index.useProp("disabled");
  const form$1 = vue.inject(form.formContextKey, void 0);
  return vue.computed(() => disabled.value || vue.unref(fallback) || (form$1 == null ? void 0 : form$1.disabled) || false);
};

exports.useDisabled = useDisabled;
exports.useSize = useSize;
exports.useSizeProp = useSizeProp;
//# sourceMappingURL=index.js.map
