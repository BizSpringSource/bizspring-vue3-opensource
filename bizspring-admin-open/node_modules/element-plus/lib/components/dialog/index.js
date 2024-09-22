'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../utils/index.js');
var dialog$1 = require('./src/dialog2.js');
var useDialog = require('./src/use-dialog.js');
var dialog = require('./src/dialog.js');
var install = require('../../utils/vue/install.js');

const ElDialog = install.withInstall(dialog$1["default"]);

exports.useDialog = useDialog.useDialog;
exports.dialogEmits = dialog.dialogEmits;
exports.dialogProps = dialog.dialogProps;
exports.ElDialog = ElDialog;
exports["default"] = ElDialog;
//# sourceMappingURL=index.js.map
