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
    // isExist({username: value}).then(response => {
    //     const result = response.data.data
    //     if (result) {
    //         return callback(new Error('用户名已经存在'))
    //     } else {
    //         return callback()
    //     }
    // })
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
        callback(new Error('请输入手机号码'))
    } else {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        if (!reg.test(value)) {
            return callback(new Error('请输入正确的电话'))
        }
    }

    if (window.boxType === 'edit') {
        return callback()
    }

    // isExist({phone: value}).then(response => {
    //     const result = response.data.data
    //     if (result) {
    //         return callback(new Error('手机号已经存在'))
    //     } else {
    //         return callback()
    //     }
    // })
}

