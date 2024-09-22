import { getCurrentInstance, computed } from 'vue';

const useProp = (name) => {
  const vm = getCurrentInstance();
  return computed(() => {
    var _a, _b;
    return (_b = ((_a = vm.proxy) == null ? void 0 : _a.$props)[name]) != null ? _b : void 0;
  });
};

export { useProp };
//# sourceMappingURL=index.mjs.map
