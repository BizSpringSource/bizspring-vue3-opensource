export default {
  //常见 httpstatus 和 英文 msg
  '200':'请求成功',//'request successful', //'请求成功',
  '422':'请求参数错误',//'Request parameter error', //'请求参数错误',
  '000':'操作太频繁，请勿重复操作',//'The request is too frequent', //'操作太频繁，请勿重复操作',
  '400':'请求失败',
  '401':'登录过期',//'Login overdue', //'登录过期',
  '403':'当前操作没有权限',//'The current operation does not have permissions', //'当前操作没有权限',
  '404':'资源不存在',//'Request does not exist', //'资源不存在',
  '417':'未绑定登录账号，请使用密码登录后绑定',//Expectation Failed,//'If no login account is bound, use the password to log in and bind it', //'未绑定登录账号，请使用密码登录后绑定',
  '426':'用户名不存在或密码错误',//Upgrade Required,//'The username does not exist or the password is incorrect', //'用户名不存在或密码错误',
  '428':'验证码错误,请重新输入',//Precondition Required,//'Verification code error, please enter again', //'验证码错误,请重新输入',
  '429':'请求过频繁,请勿重复操作',//Too Many Requests,//'The request is too frequent, do not repeat the operation', //'请求过频繁,请勿重复操作',
  '500':'操作遇到错误，请反馈管理员',//Internal Server Error,//'An error occurs, please report it to the administrator', //'操作遇到错误，请反馈管理员',
  '503':'服务不可用，请确保相应服务已启动成功',//Service Unavailable,//'The service is unavailable. Ensure that the service has been started successfully', //'服务不可用，请确保相应服务已启动成功',
  'default':'系统未知错误,请反馈给管理员',//'System unknown error, please report to the administrator' //'系统未知错误,请反馈给管理员'
  'fail':'System failure. Please contact service provider',
  default:'系统未知错误,请反馈给管理员',//'System unknown error, please report to the administrator' //'系统未知错误,请反馈给管理员'
  fail:'System failure. Please contact service provider',
}
