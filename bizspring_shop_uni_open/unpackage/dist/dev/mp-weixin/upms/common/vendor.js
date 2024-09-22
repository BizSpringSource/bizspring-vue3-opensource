(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["upms/common/vendor"],{

/***/ 196:
/*!********************************************************************************!*\
  !*** C:/IDEA_Space/bizspring-open/bizspring_shop_uni_open/api/upms/address.js ***!
  \********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _interopRequireDefault = __webpack_require__(/*! @babel/runtime/helpers/interopRequireDefault */ 4);
Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.add = add;
exports.del = del;
exports.edit = edit;
exports.list = list;
exports.save = save;
exports.update = update;
var _request = _interopRequireDefault(__webpack_require__(/*! @/utils/request.js */ 34));
var app = getApp();

/**
 * 列表
 */
function list(query) {
  return (0, _request.default)({
    url: '/upms/member/address/list',
    method: 'get',
    params: query,
    headers: app.globalData.headers
  });
}

/**
 * 添加
 */
function add(query) {
  return (0, _request.default)({
    url: '/upms/member/address/add',
    method: 'get',
    params: query,
    headers: app.globalData.headers
  });
}

/**
 * 保存
 */
function save(data) {
  return (0, _request.default)({
    url: '/upms/member/address/save',
    method: 'post',
    data: data,
    headers: app.globalData.headersJson
  });
}

/**
 * 编辑
 */
function edit(query) {
  return (0, _request.default)({
    url: '/upms/member/address/edit',
    method: 'get',
    params: query,
    headers: app.globalData.headers
  });
}

/**
 * 更新
 */
function update(data) {
  return (0, _request.default)({
    url: '/upms/member/address/update',
    method: 'post',
    data: data,
    headers: app.globalData.headersJson
  });
}

/**
 * 删除
 */
function del(params) {
  return (0, _request.default)({
    url: '/upms/member/address/delete',
    method: 'delete',
    params: params,
    headers: app.globalData.headers
  });
}
module.exports = {
  list: list,
  add: add,
  save: save,
  edit: edit,
  update: update,
  del: del
};

/***/ })

}]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/upms/common/vendor.js.map