export const tableOption = {
  dialogDrag:true,
  border: true,
  index: true,
  indexLabel: '序号',
  stripe: true,
  menuAlign: 'center',
  menuWidth: 150,
  align: 'center',
  refreshBtn: true,
  showColumnBtn: false,
  searchSize: 'default',
  addBtn: false,
  editBtn: false,
  viewBtn: true,
  excelBtn: true,
  printBtn: true,
  menuType:'text',
  searchMenuSpan: 6,
  searchBtnText:'搜索',
  emptyBtnText:'重 置',
  viewBtnText:'查看',
  delBtnText:'删除',
  viewTitle:'查看',
  menuTitle:'操作',
  props: {
    label: 'label',
    value: 'value'
  },
  column: [{
    label: '类型',
    labelWidth:150,
    searchLabelWidth:150,
    placeholder:'请选择请求方式',
    prop: 'type',
    type: 'select',
    sortable:true,
    dicUrl: '/upms/dict/type/log_type',
    searchSpan:8,
    search: true
  }, {
    label: '标题',
    labelWidth:150,
    prop: 'title'
  }, {
    label: 'IP地址',
    labelWidth:150,
    prop: 'remoteAddr'
  }, {
    label: '资源路径',
    labelWidth:150,
    prop: 'requestUri'
  }, {
    label: '请求方式',
    labelWidth:150,
    placeholder:'请选择请求方式',
    searchLabelWidth:150,
    prop: 'method',
    type: 'select',
    sortable:true,
    dicUrl: '/upms/dict/type/request_method',
    search: true,
    searchSpan:8,
  }, {
    label: '操作人',
    labelWidth:150,
    searchLabelWidth:150,
    sortable:true,
    prop: 'createBy',
    search: true,
    searchSpan:8,
    placeholder:'请选择操作人',
  }, {
    label: '客户端',
    labelWidth:150,
    sortable:true,
    prop: 'serviceId',
    hide: true
  }, {
    width: 80,
    labelWidth:150,
    label: '请求时间',
    prop: 'time'
  }, {
    width: 150,
    labelWidth:150,
    label: '创建时间',
    prop: 'createTime',
    type: 'datetime',
    sortable:true,
    format: 'YYYY-MM-DD',
    // valueFormat: 'yyyy-MM-dd HH:mm:ss'
  }, {
    labelWidth:150,
    label: '异常信息',
    prop: 'exception'
  }]
}
