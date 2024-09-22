'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var core = require('@vueuse/core');
var index = require('../use-global-config/index.js');
var index$1 = require('../use-namespace/index.js');
var index$2 = require('../use-id/index.js');

let cachedContainer;
const usePopperContainerId = () => {
  const namespace = index.useGlobalConfig("namespace", index$1.defaultNamespace);
  const idInjection = index$2.useIdInjection();
  const id = vue.computed(() => {
    return `${namespace.value}-popper-container-${idInjection.prefix}`;
  });
  const selector = vue.computed(() => `#${id.value}`);
  return {
    id,
    selector
  };
};
const createContainer = (id) => {
  const container = document.createElement("div");
  container.id = id;
  document.body.appendChild(container);
  return container;
};
const usePopperContainer = () => {
  vue.onBeforeMount(() => {
    if (!core.isClient)
      return;
    const { id, selector } = usePopperContainerId();
    if (process.env.NODE_ENV === "test" || !cachedContainer && !document.body.querySelector(selector.value)) {
      cachedContainer = createContainer(id.value);
    }
  });
};

exports.usePopperContainer = usePopperContainer;
exports.usePopperContainerId = usePopperContainerId;
//# sourceMappingURL=index.js.map
