'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../utils/index.js');
var pagination = require('./src/pagination.js');
var install = require('../../utils/vue/install.js');

const ElPagination = install.withInstall(pagination["default"]);

exports.paginationEmits = pagination.paginationEmits;
exports.paginationProps = pagination.paginationProps;
exports.ElPagination = ElPagination;
exports["default"] = ElPagination;
//# sourceMappingURL=index.js.map
