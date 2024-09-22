import { ref, computed } from 'vue';
import { useGlobalConfig } from '../use-global-config/index.mjs';

const zIndex = ref(0);
const useZIndex = () => {
  const initialZIndex = useGlobalConfig("zIndex", 2e3);
  const currentZIndex = computed(() => initialZIndex.value + zIndex.value);
  const nextZIndex = () => {
    zIndex.value++;
    return currentZIndex.value;
  };
  return {
    initialZIndex,
    currentZIndex,
    nextZIndex
  };
};

export { useZIndex };
//# sourceMappingURL=index.mjs.map
