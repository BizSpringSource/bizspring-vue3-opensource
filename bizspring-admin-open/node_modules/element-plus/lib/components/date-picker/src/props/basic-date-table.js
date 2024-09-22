'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../../../utils/index.js');
var shared = require('./shared.js');
var runtime = require('../../../../utils/vue/props/runtime.js');

const basicDateTableProps = runtime.buildProps({
  ...shared.datePickerSharedProps,
  cellClassName: {
    type: runtime.definePropType(Function)
  },
  showWeekNumber: Boolean,
  selectionMode: shared.selectionModeWithDefault("date")
});

exports.basicDateTableProps = basicDateTableProps;
//# sourceMappingURL=basic-date-table.js.map
