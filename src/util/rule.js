/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
export const rule = {
  /**
     * 校验 请输入中文、英文、数字包括下划线
     * 名称校验
     */
  validatorNameCn(rule, value, callback) {
    const acount = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/
    if (value && (!(acount).test(value))) {
      callback(new Error('请输入中文、英文、数字包括下划线'))
    } else {
      callback()
    }
  },
  /**
     * 校验 请输入英文、数字包括下划线
     * 名称校验
     */
  validatorName(rule, value, callback) {
    const acount = /^[A-Z_]+$/
    if (value && (!(acount).test(value))) {
      callback(new Error('请输入大写英文、下划线'))
    } else {
      callback()
    }
  },
  /**
     * 校验 请输入中文、英文、数字包括下划线
     * 名称校验
     */
  validatorKey(rule, value, callback) {
    const acount = /^[A-Z_]+$/
    if (value && (!(acount).test(value))) {
      callback(new Error('请输入大写英文、下划线'))
    } else {
      callback()
    }
  },

  /**
     * 校验首尾空白字符的正则表达式
     *
     */
  checkSpace(rule, value, callback) {
    const longrg = /[^\s]+$/
    if (!longrg.test(value)) {
      callback(new Error('请输入非空格信息'))
    } else {
      callback()
    }
  }
}
