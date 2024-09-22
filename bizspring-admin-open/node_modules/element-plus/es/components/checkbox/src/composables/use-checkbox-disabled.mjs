import { inject, computed } from 'vue';
import '../../../../hooks/index.mjs';
import '../../../../utils/index.mjs';
import '../../../../tokens/index.mjs';
import { checkboxGroupContextKey } from '../../../../tokens/checkbox.mjs';
import { isUndefined } from '../../../../utils/types.mjs';
import { useDisabled } from '../../../../hooks/use-common-props/index.mjs';

const useCheckboxDisabled = ({
  model,
  isChecked
}) => {
  const checkboxGroup = inject(checkboxGroupContextKey, void 0);
  const isLimitDisabled = computed(() => {
    var _a, _b;
    const max = (_a = checkboxGroup == null ? void 0 : checkboxGroup.max) == null ? void 0 : _a.value;
    const min = (_b = checkboxGroup == null ? void 0 : checkboxGroup.min) == null ? void 0 : _b.value;
    return !isUndefined(max) && model.value.length >= max && !isChecked.value || !isUndefined(min) && model.value.length <= min && isChecked.value;
  });
  const isDisabled = useDisabled(computed(() => (checkboxGroup == null ? void 0 : checkboxGroup.disabled.value) || isLimitDisabled.value));
  return {
    isDisabled,
    isLimitDisabled
  };
};

export { useCheckboxDisabled };
//# sourceMappingURL=use-checkbox-disabled.mjs.map
