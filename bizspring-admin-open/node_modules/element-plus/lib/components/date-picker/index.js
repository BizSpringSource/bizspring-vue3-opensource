'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var datePicker = require('./src/date-picker.js');

const _DatePicker = datePicker["default"];
_DatePicker.install = (app) => {
  app.component(_DatePicker.name, _DatePicker);
};
const ElDatePicker = _DatePicker;

exports.ElDatePicker = ElDatePicker;
exports["default"] = _DatePicker;
//# sourceMappingURL=index.js.map
