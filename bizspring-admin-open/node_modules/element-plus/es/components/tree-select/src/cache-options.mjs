import { defineComponent, inject, watch } from 'vue';
import '../../select/index.mjs';
import { selectKey } from '../../select/src/token.mjs';

var CacheOptions = defineComponent({
  props: {
    data: {
      type: Array,
      default: () => []
    }
  },
  setup(props) {
    const select = inject(selectKey);
    watch(() => props.data, () => {
      props.data.forEach((item) => {
        if (!select.cachedOptions.has(item.value)) {
          select.cachedOptions.set(item.value, item);
        }
      });
      select.setSelected();
    }, { immediate: true, deep: true });
    return () => void 0;
  }
});

export { CacheOptions as default };
//# sourceMappingURL=cache-options.mjs.map
