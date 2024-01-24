/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
export default {
    version: "v1.0", // 控制首页显示的版本号
    logo: "BizSpring",   // 侧边栏搜索的时候顶部展示的文案
    key: "BizSpring", //配置主键,目前用于存储
    title: "BizSpring 在线商城",
    indexTitle: "BizSpring 在线商城",
    validateCode: true,//是否开启验证码校验
    register: true, //是否开启注册
    formLoginClient: 'BizSpring:BizSpring',// 用户名密码登录的 client 信息
    smsLoginClient: 'app:app',// 验证码登录的 client 信息
    basicAuth:"Basic YWRtaW46YWRtaW4=",
    remainingTime: 1800000, // token 剩余多少毫秒执行刷新
    whiteList: ["/login", "/404", "/401", "/lock"], // 配置无权限可以访问的页面
    whiteTagList: ["/login", "/404", "/401", "/lock"], // 配置不添加tags页面 （'/advanced-router/mutative-detail/*'——*为通配符）
    lockPage: "/lock",
    tokenTime: 6000,
    statusWhiteList: [428],
    // 配置首页不可关闭
    isFirstPage: false,
    setting: {
        sidebar: 'vertical',
        tag: true,
        debug: true,
        collapse: true,
        search: true,
        lock: true,
        fullscren: true,
        theme: true,
        menu: true
    },
    fistPage: {
        label: "首页",
        value: "/wel/index",
        params: {},
        query: {},
        meta: {
            i18n: 'dashboard'
        },
        close: false
    },
    // 配置菜单的属性
    menu: {
        iconDefault: 'iconfont icon-caidan',
        label: 'label',
        path: 'path',
        icon: 'icon',
        children: 'children',
        query: 'query',
        href: 'href',
        meta: 'meta'
    }
}
