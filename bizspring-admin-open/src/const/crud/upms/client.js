export const tableOption = {
  dialogDrag:true,
  border: true,
  index: true,
  indexWidth:150,
  indexLabel: '序号',
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  viewBtn: true,
  excelBtn: true,
  printBtn: true,
  menuType:'text',
  searchMenuSpan: 6,
  searchBtnText:'搜索',
  emptyBtnText:'重 置',
  updateBtnText:'保存',
  cancelBtnText:'取 消',
  saveBtnText:'保存',
  addBtnText:'添加',
  viewBtnText:'查看',
  editBtnText:'编辑',
  delBtnText:'删除',
  addTitle:'添加',
  editTitle:'编辑',
  viewTitle:'查看',
  menuTitle:'操作',
  column: [{
    width: 150,
    labelWidth:180,
    label: '终端ID',
    placeholder:'请输入终端ID',
    prop: 'id',
    align: 'center',
    sortable: true,
    editDisabled: true,
    rules: [{
      required: true,
      message: '请输入终端ID',
      trigger: 'blur'
    }]
  }, {
    label: '终端密钥',
    placeholder:'请输入终端密钥',
    labelWidth:180,
    prop: 'clientSecret',
    align: 'center',
    sortable: true,
    overHidden: true,
    width: 120,
    rules: [{
      required: true,
      message: '请选择终端密钥',
      trigger: 'blur'
    }]
  }, {
    label: '作用域',
    placeholder:'请选择作用域',
    labelWidth:180,
    prop: 'scope',
    align: 'center',
    rules: [{
      required: true,
      message: '请选择作用域',
      trigger: 'blur'
    }]
  }, {
    label: '授权类型',
    placeholder:'请选择授权类型',
    labelWidth:180,
    prop: 'authorizedGrantTypes',
    align: 'center',
    type: 'checkbox',
    dicUrl: '/upms/dict/type/authorized_grant_types',
    overHidden: true,
    rules: [{
      required: true,
      message: '请选择授权类型',
      trigger: 'blur'
    }]
  }, {
    label: '回调地址',
    placeholder:'请选择回调地址',
    labelWidth:180,
    prop: 'webServerRedirectUri',
    align: 'center',
    hide: true
  }, {
    label: '权限',
    placeholder:'请选择权限',
    labelWidth:180,
    prop: 'authorities',
    align: 'center',
    hide: true
  }, {
    label: '自动放行',
    labelWidth:180,
    placeholder:'请选择自动放行',
    prop: 'autoapprove',
    align: 'center',
    type: 'radio',
    dicUrl: '/upms/dict/type/true_false',
    hide: true,
    rules: [{
      required: true,
      message: '请选择自动放行',
      trigger: 'blur'
    }]
  }, {
    label: '令牌时效(s)',
    labelWidth:180,
    placeholder:'请选择令牌时效',
    prop: 'accessTokenValidity',
    type: 'number',
    align: 'center',
  }, {
    label: '刷新时效(s)',
    labelWidth:180,
    placeholder:'请选择刷新时效',
    prop: 'refreshTokenValidity',
    type: 'number',
    align: 'center',
  }, {
    label: '扩展信息',
    labelWidth:180,
    placeholder:'请选择扩展信息',
    prop: 'additionalInformation',
    align: 'center',
    hide: true
  }, {
    label: '资源ID',
    placeholder:'请选择资源ID',
    labelWidth:180,
    prop: 'resourceIds',
    align: 'center',
    hide: true
  }]
}
