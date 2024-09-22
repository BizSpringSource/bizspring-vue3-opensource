import DatePicker from './src/date-picker.mjs';

const _DatePicker = DatePicker;
_DatePicker.install = (app) => {
  app.component(_DatePicker.name, _DatePicker);
};
const ElDatePicker = _DatePicker;

export { ElDatePicker, _DatePicker as default };
//# sourceMappingURL=index.mjs.map
