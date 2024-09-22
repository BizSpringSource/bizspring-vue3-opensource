var app = getApp();

var MD5Encode = require("./MD5Encode.js");
/**
 * 对字符串判空
 */


function isStringEmpty(data) {
  if (null == data || "" == data) {
    return true;
  }

  return false;
}
/**
 * 封装网络请求
 */


function sentHttpRequestToServer(uri, data, method, successCallback, failCallback, completeCallback) {
  uni.request({
    url: app.globalData.d.hostUrl + uri,
    data: data,
    method: method,
    header: app.globalData.header,
    success: successCallback,
    fail: failCallback,
    complete: completeCallback
  });
}
/**
 * 将map对象转换为json字符串
 */


function mapToJson(map) {
  if (null == map) {
    return null;
  }

  var jsonString = "{";

  for (var key in map) {
    jsonString = jsonString + key + ":" + map[key] + ",";
  }

  if ("," == jsonString.charAt(jsonString.length - 1)) {
    jsonString = jsonString.substring(0, jsonString.length - 1);
  }

  jsonString += "}";
  return jsonString;
}
/**
 * 弹窗提示成功
 */


function toastSuccess() {
  uni.showToast({
    title: '成功',
    icon: 'success',
    duration: 2000
  });
}
/**
 * 调用微信支付
 */


function doWechatPay(prepayId, successCallback, failCallback, completeCallback) {
  var nonceString = getRandomString();
  var currentTimeStamp = getCurrentTimeStamp();
  var packageName = "prepay_id=" + prepayId;
  var dataMap = {
    timeStamp: currentTimeStamp,
    nonceStr: nonceString,
    package: packageName,
    signType: "MD5",
    paySign: getWechatPaySign(nonceString, packageName, currentTimeStamp),
    success: successCallback,
    fail: failCallback,
    complete: completeCallback
  };
  uni.requestPayment(dataMap);
}
/**
 * 获取微信支付签名字符串
 */


function getWechatPaySign(nonceStr, packageName, timeStamp) {
  var beforMD5 = "appid=" + app.globalData.d.appId + "&nonceStr=" + nonceStr + "&package=" + packageName + "&signType=MD5" + "&timeStamp=" + timeStamp + "&key=" + app.globalData.d.appKey;
  return doMD5Encode(beforMD5).toUpperCase();
}
/**
 * 获取当前时间戳
 */


function getCurrentTimeStamp() {
  var timestamp = Date.parse(new Date());
  return timestamp + "";
}
/**
 * 获取随机字符串，32位以下
 */


function getRandomString() {
  return Math.random().toString(36).substring(3, 8);
}
/**
 * MD5加密
 */


function doMD5Encode(toEncode) {
  return MD5Encode.hexMD5(toEncode);
}

module.exports = {
  isStringEmpty: isStringEmpty,
  sentHttpRequestToServer: sentHttpRequestToServer,
  mapToJson: mapToJson,
  toastSuccess: toastSuccess,
  doWechatPay: doWechatPay
};