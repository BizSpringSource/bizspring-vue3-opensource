'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../select/index.js');
var token = require('../../select/src/token.js');

var CacheOptions = vue.defineComponent({
  props: {
    data: {
      type: Array,
      default: () => []
    }
  },
  setup(props) {
    const select = vue.inject(token.selectKey);
    vue.watch(() => props.data, () => {
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

exports["default"] = CacheOptions;
//# sourceMappingURL=cache-options.js.map
