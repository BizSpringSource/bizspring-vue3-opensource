import { isClient } from '@vueuse/core';

const isFirefox = () => isClient && /firefox/i.test(window.navigator.userAgent);

export { isFirefox };
//# sourceMappingURL=browser.mjs.map
