'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var shared = require('@vue/shared');

const flattenOptions = (options) => {
  const flattened = [];
  options.forEach((option) => {
    if (shared.isArray(option.options)) {
      flattened.push({
        label: option.label,
        isTitle: true,
        type: "Group"
      });
      option.options.forEach((o) => {
        flattened.push(o);
      });
      flattened.push({
        type: "Group"
      });
    } else {
      flattened.push(option);
    }
  });
  return flattened;
};

exports.flattenOptions = flattenOptions;
//# sourceMappingURL=util.js.map
