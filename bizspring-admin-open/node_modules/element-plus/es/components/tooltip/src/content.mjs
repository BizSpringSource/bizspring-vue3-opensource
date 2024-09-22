import '../../../utils/index.mjs';
import '../../popper/index.mjs';
import '../../../hooks/index.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';
import { buildProps, definePropType } from '../../../utils/vue/props/runtime.mjs';
import { useDelayedToggleProps } from '../../../hooks/use-delayed-toggle/index.mjs';
import { popperContentProps } from '../../popper/src/content.mjs';

const ns = useNamespace("tooltip");
const useTooltipContentProps = buildProps({
  ...useDelayedToggleProps,
  ...popperContentProps,
  appendTo: {
    type: definePropType([String, Object])
  },
  content: {
    type: String,
    default: ""
  },
  rawContent: {
    type: Boolean,
    default: false
  },
  persistent: Boolean,
  ariaLabel: String,
  visible: {
    type: definePropType(Boolean),
    default: null
  },
  transition: {
    type: String,
    default: `${ns.namespace.value}-fade-in-linear`
  },
  teleported: {
    type: Boolean,
    default: true
  },
  disabled: {
    type: Boolean
  }
});

export { useTooltipContentProps };
//# sourceMappingURL=content.mjs.map
