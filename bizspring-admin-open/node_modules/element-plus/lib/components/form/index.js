'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../utils/index.js');
var form$1 = require('./src/form2.js');
var formItem$1 = require('./src/form-item2.js');
var form = require('./src/form.js');
var formItem = require('./src/form-item.js');
require('./src/types.js');
var install = require('../../utils/vue/install.js');

const ElForm = install.withInstall(form$1["default"], {
  FormItem: formItem$1["default"]
});
const ElFormItem = install.withNoopInstall(formItem$1["default"]);

exports.formEmits = form.formEmits;
exports.formProps = form.formProps;
exports.formItemProps = formItem.formItemProps;
exports.formItemValidateStates = formItem.formItemValidateStates;
exports.ElForm = ElForm;
exports.ElFormItem = ElFormItem;
exports["default"] = ElForm;
//# sourceMappingURL=index.js.map
