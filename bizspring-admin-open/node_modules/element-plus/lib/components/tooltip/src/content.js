'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../../utils/index.js');
require('../../popper/index.js');
require('../../../hooks/index.js');
var index = require('../../../hooks/use-namespace/index.js');
var runtime = require('../../../utils/vue/props/runtime.js');
var index$1 = require('../../../hooks/use-delayed-toggle/index.js');
var content = require('../../popper/src/content.js');

const ns = index.useNamespace("tooltip");
const useTooltipContentProps = runtime.buildProps({
  ...index$1.useDelayedToggleProps,
  ...content.popperContentProps,
  appendTo: {
    type: runtime.definePropType([String, Object])
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
    type: runtime.definePropType(Boolean),
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

exports.useTooltipContentProps = useTooltipContentProps;
//# sourceMappingURL=content.js.map
