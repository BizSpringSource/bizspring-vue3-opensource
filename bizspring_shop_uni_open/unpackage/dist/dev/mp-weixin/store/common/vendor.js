(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["store/common/vendor"],{

/***/ 176:
/*!*******************************************************************************!*\
  !*** C:/IDEA_Space/bizspring-open/bizspring_shop_uni_open/api/store/store.js ***!
  \*******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _interopRequireDefault = __webpack_require__(/*! @babel/runtime/helpers/interopRequireDefault */ 4);
Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.index = index;
var _request = _interopRequireDefault(__webpack_require__(/*! @/utils/request.js */ 34));
var app = getApp();

/**
 * 首页
 */
function index(storeId) {
  return (0, _request.default)({
    url: '/store/member/store/index?id=' + storeId,
    method: 'get',
    headers: app.globalData.headers
  });
}
module.exports = {
  index: index
};

/***/ })

}]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/store/common/vendor.js.map