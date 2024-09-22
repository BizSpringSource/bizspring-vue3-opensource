'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../utils/index.js');
require('../../../hooks/index.js');
var runtime = require('../../../utils/vue/props/runtime.js');
var index = require('../../../hooks/use-common-props/index.js');
var index$1 = require('../../../hooks/use-global-config/index.js');

const messageConfig = {};
const configProviderProps = runtime.buildProps({
  a11y: {
    type: Boolean,
    default: true
  },
  locale: {
    type: runtime.definePropType(Object)
  },
  size: index.useSizeProp,
  button: {
    type: runtime.definePropType(Object)
  },
  experimentalFeatures: {
    type: runtime.definePropType(Object)
  },
  keyboardNavigation: {
    type: Boolean,
    default: true
  },
  message: {
    type: runtime.definePropType(Object)
  },
  zIndex: Number,
  namespace: {
    type: String,
    default: "el"
  }
});
const ConfigProvider = vue.defineComponent({
  name: "ElConfigProvider",
  props: configProviderProps,
  setup(props, { slots }) {
    vue.watch(() => props.message, (val) => {
      Object.assign(messageConfig, val != null ? val : {});
    }, { immediate: true, deep: true });
    const config = index$1.provideGlobalConfig(props);
    return () => vue.renderSlot(slots, "default", { config: config == null ? void 0 : config.value });
  }
});

exports.configProviderProps = configProviderProps;
exports["default"] = ConfigProvider;
exports.messageConfig = messageConfig;
//# sourceMappingURL=config-provider.js.map
