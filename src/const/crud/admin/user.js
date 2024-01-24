
/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
export const validateUsername = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请输入用户名'))
    }
    const flag = new RegExp(/^([a-z\d]+?)$/).test(value)
    if (!flag) {
        callback(new Error('用户名支持小写英文、数字'))
    }

    if (window.boxType === 'edit') {
        return callback()
    }
}

// 设置密码校验规则
export const checkPassword = (rule, value, callback) => {
    if (window.boxType === 'edit') {
        return callback()
    }
    if (!value) {
        callback(new Error('请输入密码'))
    } else if (value.length <= 6) {
        callback(new Error('请输入6位以上密码'))
    } else {
        callback()
    }
}

// 设置手机号的验证规则
export const checkPhone = (rule, value, callback) => {
    if (!value) {
        callback(new Error('请输入手机号'))
    } else {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        if (!reg.test(value)) {
            return callback(new Error('请输入正确的电话'))
        }
    }

    if (window.boxType === 'edit') {
        return callback()
    }
}

