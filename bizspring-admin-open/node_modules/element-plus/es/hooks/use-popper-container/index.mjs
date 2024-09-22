import { computed, onBeforeMount } from 'vue';
import { isClient } from '@vueuse/core';
import { useGlobalConfig } from '../use-global-config/index.mjs';
import { defaultNamespace } from '../use-namespace/index.mjs';
import { useIdInjection } from '../use-id/index.mjs';

let cachedContainer;
const usePopperContainerId = () => {
  const namespace = useGlobalConfig("namespace", defaultNamespace);
  const idInjection = useIdInjection();
  const id = computed(() => {
    return `${namespace.value}-popper-container-${idInjection.prefix}`;
  });
  const selector = computed(() => `#${id.value}`);
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
  onBeforeMount(() => {
    if (!isClient)
      return;
    const { id, selector } = usePopperContainerId();
    if (process.env.NODE_ENV === "test" || !cachedContainer && !document.body.querySelector(selector.value)) {
      cachedContainer = createContainer(id.value);
    }
  });
};

export { usePopperContainer, usePopperContainerId };
//# sourceMappingURL=index.mjs.map
