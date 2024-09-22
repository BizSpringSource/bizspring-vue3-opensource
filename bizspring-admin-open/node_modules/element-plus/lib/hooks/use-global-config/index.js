'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../tokens/index.js');
require('../../utils/index.js');
var configProvider = require('../../tokens/config-provider.js');
var error = require('../../utils/error.js');
var objects = require('../../utils/objects.js');

const globalConfig = vue.ref();
function useGlobalConfig(key, defaultValue = void 0) {
  const config = vue.getCurrentInstance() ? vue.inject(configProvider.configProviderContextKey, globalConfig) : globalConfig;
  if (key) {
    return vue.computed(() => {
      var _a, _b;
      return (_b = (_a = config.value) == null ? void 0 : _a[key]) != null ? _b : defaultValue;
    });
  } else {
    return config;
  }
}
const provideGlobalConfig = (config, app, global = false) => {
  var _a;
  const inSetup = !!vue.getCurrentInstance();
  const oldConfig = inSetup ? useGlobalConfig() : void 0;
  const provideFn = (_a = app == null ? void 0 : app.provide) != null ? _a : inSetup ? vue.provide : void 0;
  if (!provideFn) {
    error.debugWarn("provideGlobalConfig", "provideGlobalConfig() can only be used inside setup().");
    return;
  }
  const context = vue.computed(() => {
    const cfg = vue.unref(config);
    if (!(oldConfig == null ? void 0 : oldConfig.value))
      return cfg;
    return mergeConfig(oldConfig.value, cfg);
  });
  provideFn(configProvider.configProviderContextKey, context);
  if (global || !globalConfig.value) {
    globalConfig.value = context.value;
  }
  return context;
};
const mergeConfig = (a, b) => {
  var _a;
  const keys = [.../* @__PURE__ */ new Set([...objects.keysOf(a), ...objects.keysOf(b)])];
  const obj = {};
  for (const key of keys) {
    obj[key] = (_a = b[key]) != null ? _a : a[key];
  }
  return obj;
};

exports.provideGlobalConfig = provideGlobalConfig;
exports.useGlobalConfig = useGlobalConfig;
//# sourceMappingURL=index.js.map
