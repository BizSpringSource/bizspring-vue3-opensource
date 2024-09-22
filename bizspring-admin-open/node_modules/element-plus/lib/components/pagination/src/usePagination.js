'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../tokens/index.js');
var pagination = require('../../../tokens/pagination.js');

const usePagination = () => vue.inject(pagination.elPaginationKey, {});

exports.usePagination = usePagination;
//# sourceMappingURL=usePagination.js.map
