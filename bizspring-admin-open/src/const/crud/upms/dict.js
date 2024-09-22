export const tableOption = {
    dialogDrag: true,
    border: true,
    index: true,
    indexLabel: '序号',
    stripe: true,
    menuAlign: 'center',
    align: 'center',
    showColumnBtn: false,
    viewBtn: true,
    searchSize: 'default',
    menuType: 'text',
    searchMenuSpan: 6,
    searchBtnText:'搜索',
    emptyBtnText:'重 置',
    addBtnText:'添加',
    saveBtnText:'保存',
    viewBtnText:'查看',
    editBtnText:'编辑',
    delBtnText:'删除',
    addTitle:'添加',
    editTitle:'编辑',
    viewTitle:'查看',
    menuTitle:'操作',
    updateBtnText:'保存',
    cancelBtnText:'取 消',
    submitText:'确 定',
    emptyText:'取 消',
    column: [{
        label: '类型',
        placeholder:'请选择类型',
        prop: 'type',
        labelWidth:150,
        search: true,
        rules: [{
            required: true,
            message: '请选择类型',
            trigger: 'blur'
        }]
    }, {
        label: '描述',
        labelWidth:150,
        placeholder:'请选择描述',
        prop: 'description',
        rules: [{
            required: true,
            message: '请选择描述',
            trigger: 'blur'
        }]
    },
]
}


export const tableOption2 = {
    dialogDrag: true,
    border: true,
    stripe: true,
    menuAlign: 'center',
    addTitle: '添加',
    editTitle: '编辑',
    addBtnText: '添加',
    editBtnText: '编辑',
    saveBtnText: '保存',
    cancelBtnText: '取 消',
    updateBtnText: '保存',
    delBtnText: '删除',
    updateText:'保存',
    cancelText:'取 消',
    align: 'center',
    refreshBtn: true,
    showColumnBtn: false,
    searchSize: 'small',
    column: [
        {
            label: '数据值',
            placeholder:'请选择数据值',
            prop: 'value',
            labelWidth:150,
            rules: [{
                required: true,
                message: '请选择数据值',
                trigger: 'blur'
            }]
        }, {
            label: '标签名',
            prop: 'label',
            placeholder:'请选择标签名',
            labelWidth:150,
            rules: [{
                required: true,
                message: '请选择标签名',
                trigger: 'blur'
            }]
        }, {
            label: '排序',
            placeholder:'请选择排序',
            prop: 'sort',
            labelWidth:150,
            type: 'number',
            rules: [{
                required: true,
                message: '请选择排序',
                trigger: 'blur'
            }]
        }, {
            label: '描述',
            placeholder:'请选择描述',
            prop: 'description',
            labelWidth:150,
            rules: [{
                required: true,
                message: '描述',
                trigger: 'blur'
            }]
        }]
}
