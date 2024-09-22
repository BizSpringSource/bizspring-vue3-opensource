'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../utils/index.js');
var tooltip$1 = require('./src/tooltip2.js');
var tooltip = require('./src/tooltip.js');
var trigger = require('./src/trigger.js');
var content = require('./src/content.js');
var install = require('../../utils/vue/install.js');

const ElTooltip = install.withInstall(tooltip$1["default"]);

exports.tooltipEmits = tooltip.tooltipEmits;
exports.useTooltipModelToggle = tooltip.useTooltipModelToggle;
exports.useTooltipModelToggleEmits = tooltip.useTooltipModelToggleEmits;
exports.useTooltipModelToggleProps = tooltip.useTooltipModelToggleProps;
exports.useTooltipProps = tooltip.useTooltipProps;
exports.useTooltipTriggerProps = trigger.useTooltipTriggerProps;
exports.useTooltipContentProps = content.useTooltipContentProps;
exports.ElTooltip = ElTooltip;
exports["default"] = ElTooltip;
//# sourceMappingURL=index.js.map
