import { ref, inject, computed, unref } from 'vue';
import '../../tokens/index.mjs';
import '../../utils/index.mjs';
import '../../constants/index.mjs';
import { useProp } from '../use-prop/index.mjs';
import { useGlobalConfig } from '../use-global-config/index.mjs';
import { buildProp } from '../../utils/vue/props/runtime.mjs';
import { componentSizes } from '../../constants/size.mjs';
import { formContextKey, formItemContextKey } from '../../tokens/form.mjs';

const useSizeProp = buildProp({
  type: String,
  values: componentSizes,
  required: false
});
const useSize = (fallback, ignore = {}) => {
  const emptyRef = ref(void 0);
  const size = ignore.prop ? emptyRef : useProp("size");
  const globalConfig = ignore.global ? emptyRef : useGlobalConfig("size");
  const form = ignore.form ? { size: void 0 } : inject(formContextKey, void 0);
  const formItem = ignore.formItem ? { size: void 0 } : inject(formItemContextKey, void 0);
  return computed(() => size.value || unref(fallback) || (formItem == null ? void 0 : formItem.size) || (form == null ? void 0 : form.size) || globalConfig.value || "");
};
const useDisabled = (fallback) => {
  const disabled = useProp("disabled");
  const form = inject(formContextKey, void 0);
  return computed(() => disabled.value || unref(fallback) || (form == null ? void 0 : form.disabled) || false);
};

export { useDisabled, useSize, useSizeProp };
//# sourceMappingURL=index.mjs.map
