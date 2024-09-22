import '../../utils/index.mjs';
import Popper from './src/popper2.mjs';
export { default as ElPopperArrow } from './src/arrow2.mjs';
export { default as ElPopperTrigger } from './src/trigger2.mjs';
export { default as ElPopperContent } from './src/content2.mjs';
export { Effect, popperProps, roleTypes, usePopperProps } from './src/popper.mjs';
export { popperTriggerProps, usePopperTriggerProps } from './src/trigger.mjs';
export { popperContentEmits, popperContentProps, popperCoreConfigProps, usePopperContentEmits, usePopperContentProps, usePopperCoreConfigProps } from './src/content.mjs';
export { popperArrowProps, usePopperArrowProps } from './src/arrow.mjs';
import { withInstall } from '../../utils/vue/install.mjs';

const ElPopper = withInstall(Popper);

export { ElPopper, ElPopper as default };
//# sourceMappingURL=index.mjs.map
