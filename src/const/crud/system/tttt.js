/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
export const tableOption = {
    dialogDrag: true,
    border: true,
    indexLabel: '序号',
    stripe: true,
    menuAlign: 'center',
    align: 'center',
    menuType: 'text',
    searchShow: false,
    excelBtn: true,
    printBtn: true,
    viewBtn: true,
    searchMenuSpan: 6,
    column: [
            {
            label: 'id',
            prop: 'id',
            sortable: true,
            rules: [
                    {
                        required: true,
                        message: '请输入',
                        trigger: 'blur'
                    },
                                    {
                        max: 255,
                        message: '长度在不能超过255个字符'
                    },
                ]
        },
            {
            label: 'test',
            prop: 'test',
            sortable: true,
            rules: [
                                    {
                        max: 255,
                        message: '长度在不能超过255个字符'
                    },
                ]
        },
            ]
}
