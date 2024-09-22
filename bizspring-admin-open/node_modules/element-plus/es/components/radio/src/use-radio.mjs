import { ref, inject, computed } from 'vue';
import '../../../constants/index.mjs';
import '../../../tokens/index.mjs';
import '../../../hooks/index.mjs';
import { radioGroupKey } from '../../../tokens/radio.mjs';
import { UPDATE_MODEL_EVENT } from '../../../constants/event.mjs';
import { useSize, useDisabled } from '../../../hooks/use-common-props/index.mjs';

const useRadio = (props, emit) => {
  const radioRef = ref();
  const radioGroup = inject(radioGroupKey, void 0);
  const isGroup = computed(() => !!radioGroup);
  const modelValue = computed({
    get() {
      return isGroup.value ? radioGroup.modelValue : props.modelValue;
    },
    set(val) {
      if (isGroup.value) {
        radioGroup.changeEvent(val);
      } else {
        emit && emit(UPDATE_MODEL_EVENT, val);
      }
      radioRef.value.checked = props.modelValue === props.label;
    }
  });
  const size = useSize(computed(() => radioGroup == null ? void 0 : radioGroup.size));
  const disabled = useDisabled(computed(() => radioGroup == null ? void 0 : radioGroup.disabled));
  const focus = ref(false);
  const tabIndex = computed(() => {
    return disabled.value || isGroup.value && modelValue.value !== props.label ? -1 : 0;
  });
  return {
    radioRef,
    isGroup,
    radioGroup,
    focus,
    size,
    disabled,
    tabIndex,
    modelValue
  };
};

export { useRadio };
//# sourceMappingURL=use-radio.mjs.map
