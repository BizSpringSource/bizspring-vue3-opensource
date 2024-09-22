'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../tokens/index.js');
var index = require('../use-id/index.js');
var form = require('../../tokens/form.js');

const useFormItem = () => {
  const form$1 = vue.inject(form.formContextKey, void 0);
  const formItem = vue.inject(form.formItemContextKey, void 0);
  return {
    form: form$1,
    formItem
  };
};
const useFormItemInputId = (props, {
  formItemContext,
  disableIdGeneration,
  disableIdManagement
}) => {
  if (!disableIdGeneration) {
    disableIdGeneration = vue.ref(false);
  }
  if (!disableIdManagement) {
    disableIdManagement = vue.ref(false);
  }
  const inputId = vue.ref();
  let idUnwatch = void 0;
  const isLabeledByFormItem = vue.computed(() => {
    var _a;
    return !!(!props.label && formItemContext && formItemContext.inputIds && ((_a = formItemContext.inputIds) == null ? void 0 : _a.length) <= 1);
  });
  vue.onMounted(() => {
    idUnwatch = vue.watch([vue.toRef(props, "id"), disableIdGeneration], ([id, disableIdGeneration2]) => {
      const newId = id != null ? id : !disableIdGeneration2 ? index.useId().value : void 0;
      if (newId !== inputId.value) {
        if (formItemContext == null ? void 0 : formItemContext.removeInputId) {
          inputId.value && formItemContext.removeInputId(inputId.value);
          if (!(disableIdManagement == null ? void 0 : disableIdManagement.value) && !disableIdGeneration2 && newId) {
            formItemContext.addInputId(newId);
          }
        }
        inputId.value = newId;
      }
    }, { immediate: true });
  });
  vue.onUnmounted(() => {
    idUnwatch && idUnwatch();
    if (formItemContext == null ? void 0 : formItemContext.removeInputId) {
      inputId.value && formItemContext.removeInputId(inputId.value);
    }
  });
  return {
    isLabeledByFormItem,
    inputId
  };
};

exports.useFormItem = useFormItem;
exports.useFormItemInputId = useFormItemInputId;
//# sourceMappingURL=index.js.map
