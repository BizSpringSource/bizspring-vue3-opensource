(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["order/common/vendor"],{

/***/ 131:
/*!*******************************************************************************!*\
  !*** C:/IDEA_Space/bizspring-open/bizspring_shop_uni_open/api/order/order.js ***!
  \*******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _interopRequireDefault = __webpack_require__(/*! @babel/runtime/helpers/interopRequireDefault */ 4);
Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.calculate = calculate;
exports.cancel = cancel;
exports.checkout = checkout;
exports.create = create;
exports.list = list;
exports.payment = payment;
exports.receive = receive;
var _request = _interopRequireDefault(__webpack_require__(/*! @/utils/request.js */ 34));
var app = getApp();

/**
 * 列表
 */
function list(query) {
  return (0, _request.default)({
    url: '/order/member/order/list',
    method: 'get',
    params: query,
    headers: app.globalData.headers
  });
}

/**
 * 取消
 */
function cancel(params) {
  return (0, _request.default)({
    url: '/order/member/order/cancel',
    method: 'post',
    params: params,
    headers: app.globalData.headersJson
  });
}

/**
 * 收货
 */
function receive(params) {
  return (0, _request.default)({
    url: '/order/member/order/receive',
    method: 'post',
    params: params,
    headers: app.globalData.headersJson
  });
}

/**
 * 结算
 */
function checkout(query) {
  return (0, _request.default)({
    url: '/order/member/order/checkout',
    method: 'get',
    params: query,
    headers: app.globalData.headers
  });
}

/**
 * 计算
 */
function calculate(query) {
  return (0, _request.default)({
    url: '/order/member/order/calculate',
    method: 'get',
    params: query,
    headers: app.globalData.headers
  });
}

/**
 * 创建
 */
function create(params) {
  return (0, _request.default)({
    url: '/order/member/order/create',
    method: 'post',
    params: params,
    headers: app.globalData.headersJson
  });
}

/**
 * 支付
 */
function payment(query) {
  return (0, _request.default)({
    url: '/order/member/order/payment',
    method: 'get',
    params: query,
    headers: app.globalData.headers
  });
}
module.exports = {
  list: list,
  cancel: cancel,
  receive: receive,
  checkout: checkout,
  calculate: calculate,
  create: create,
  payment: payment
};

/***/ }),

/***/ 140:
/*!*********************************************************************************!*\
  !*** C:/IDEA_Space/bizspring-open/bizspring_shop_uni_open/api/order/payment.js ***!
  \*********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _interopRequireDefault = __webpack_require__(/*! @babel/runtime/helpers/interopRequireDefault */ 4);
Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.index = index;
exports.load = load;
exports.pay = pay;
exports.testPostPay = testPostPay;
var _request = _interopRequireDefault(__webpack_require__(/*! @/utils/request.js */ 34));
var app = getApp();

/**
 * 首页
 */
function index(data, params) {
  return (0, _request.default)({
    url: '/order/member/payment',
    method: 'post',
    data: data,
    params: params,
    headers: app.globalData.headersJson
  });
}

/**
 * 支付处理
 */
function pay(params) {
  return (0, _request.default)({
    url: '/order/member/payment/pay',
    method: 'get',
    params: params,
    headers: app.globalData.headers
  });
}

//支付前 生成支付记录
function load(data, params) {
  return (0, _request.default)({
    url: '/order/member/payment/load',
    method: 'post',
    data: data,
    params: params,
    headers: app.globalData.headersJson
  });
}
function testPostPay(params, paymentLogSn) {
  return (0, _request.default)({
    url: '/order/member/payment/test_post_pay_' + paymentLogSn,
    method: 'get',
    params: params,
    headers: app.globalData.headers
  });
}
module.exports = {
  index: index,
  pay: pay,
  load: load,
  testPostPay: testPostPay
};

/***/ })

}]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/order/common/vendor.js.map