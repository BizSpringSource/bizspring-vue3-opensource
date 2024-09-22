import {getList} from '@/api/upms/role'

const validateRoleName = (rule, value, callback) => {
  if (window.openType === 'edit'){
    callback()
  }else{
    getList({
      roleName: value
    }).then(response => {
      let data = response.data
      if (data.length > 0) {
        callback(new Error('名称已存在'))
      } else {
        callback()
      }
    })
  }
}

const validateRoleCode = (rule, value, callback) => {
  if (window.openType === 'edit'){
    callback()
  }else{
    getList({
      roleCode: value
    }).then(response => {
      let data = response.data
      if (data.length > 0) {
        callback(new Error('角色编码已存在'))
      } else {
        callback()
      }
    })
  }
}

export const tableOption = {
  dialogDrag:true,
  border: true,
  index: true,
  indexLabel: '序号',
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  viewBtn: true,
  excelBtn: true,
  printBtn: true,
  searchBtnText:'搜索',
  emptyBtnText:'重 置',
  addBtnText:'添加',
  viewBtnText:'查看',
  editBtnText:'编辑',
  delBtnText:'删除',
  addTitle:'添加',
  editTitle:'编辑',
  viewTitle:'查看',
  menuTitle:'操作',
  saveBtnText:'保存',
  updateBtnText:'保存',
  cancelBtnText:'取 消',
  menuType:'text',
  searchMenuSpan: 6,
  column: [{
    label: '名称',
    labelWidth:180,
    prop: 'roleName',
    span: 24,
    rules: [{
      required: true,
      message: '名称不能为空',
      trigger: 'blur'
    },
    {
      min: 3,
      max: 20,
      message: '长度在3至20个字符',
      trigger: 'blur'
    },
    {
      validator: validateRoleName,
      trigger: 'blur'
    }]
  }, {
    width: 120,
    labelWidth:180,
    label: '角色编码',
    prop: 'roleCode',
    span: 24,
    editDisabled: true,
    rules: [{
      required: true,
      message: '角色编码不能为空',
      trigger: 'blur'
    },
    {
      min: 3,
      max: 20,
      message: '长度在3至20个字符',
      trigger: 'blur'
    },
    {
      validator: validateRoleCode,
      trigger: 'blur'
    }
    ]
  }, {
    width: 150,
    labelWidth:180,
    label: '描述',
    prop: 'roleDesc',
    overHidden: true,
    span: 24,
    rules: [{
      required: true,
      message: '描述不能为空',
      trigger: 'blur'
    }]
  }, {
    width: 180,
    labelWidth:180,
    label: '数据权限类型',
    prop: 'dsType',
    type: 'select',
    span: 24,
    sortable:true,
    rules: [{
      required: true,
      message: '请选择数据权限类型',
      trigger: 'blur'
    }],
    dicData: [{
      label: '全部',
      value: 0
    }, {
      label: '自定义',
      value: 1
    }, {
      label: '本级及子级',
      value: 2
    }, {
      label: '本级',
      value: 3
    }]
  }, {
  //   label:'',
  //   prop: 'dsScope',
  //   formslot: true,
  //   showColumn:false,
  //   hide: true
  // }, {
    labelWidth:180,
    label: '创建时间',
    prop: 'createTime',
    type: 'datetime',
    format: 'YYYY-MM-DD',
    // valueFormat: 'yyyy-MM-dd HH:mm:ss',
    editDisplay: false,
    addDisplay: false,
    sortable:true,
    span: 24
  }]
}
