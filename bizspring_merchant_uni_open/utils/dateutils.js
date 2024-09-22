var formatNumber = function (n) {
  n = n.toString();
  return n[1] ? n : '0' + n;
};
/**
 * 将时间戳(1570550400)格式转为 yyyy-MM-dd格式
 */


export function formatDate(datetime) {
  var date = getDate(datetime);
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  return [year, month, day].map(formatNumber).join('-');
}

module.exports = {
  formatDate
}