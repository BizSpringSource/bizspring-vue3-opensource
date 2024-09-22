import iconList from "@/const/iconList";
export const tableOption = {
  dialogDrag: true,
  headerAlign: 'center',
  align: 'center',
  border: true,
  viewBtn: true,
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
  updateBtnText:'保存',
  cancelBtnText:'取 消',
  saveBtnText:'保存',
  column: [
    {
      label: '上级菜单',
      labelWidth:180,
      placeholder:'请选择上级菜单',
      prop: 'parentId',
      type: 'tree',
      hide: true,
      props: {
        label: 'name',
        value: 'id'
      },
      rules: [{
        required: true,
        message: '请选择上级菜单',
        trigger: 'change'
      }]
    },
    {
      label: '名称',
      labelWidth:180,
      placeholder:'请选择名称',
      prop: 'name',
      align: 'left',
      width: 200,
      rules: [{
        required: true,
        message: '名称不能为空',
        trigger: 'blur'
      }]
    },
    {
      label: '类型',
      labelWidth:180,
      placeholder:'请选择类型',
      prop: 'type',
      type: 'select',
      slot: true,
      dicData: [{
        label: '菜单',
        value: '0'
      },{
        label: '按钮',
        value: '1'
      }],
      rules: [{
        required: true,
        message: '请选择类型',
        trigger: 'blur'
      }]
    },
    {
      label: '权限标识',
      placeholder:'请选择权限标识',
      labelWidth:180,
      prop: 'permission'
    },
    {
      label: '排序',
      labelWidth:180,
      placeholder:'请选择排序',
      type: 'number',
      prop: 'sort',
      rules: [{
        required: true,
        message: '排序不能为空',
        trigger: 'blur'
      }]
    },
    {
      label:'图标',
      labelWidth:180,
      placeholder:'请选择图标',
      prop: 'icon',
      type: 'icon',
      iconList: iconList,
      slot: false,
      display: true
    },
    {
      label: '菜单路径',
      placeholder:'请选择菜单路径',
      labelWidth:180,
      prop: 'component',
      display: true
    },
    {
      label: '菜单编码',
      placeholder:'请选择菜单编码',
      labelWidth:180,
      prop: 'path',
      display: false
    },
    {
      label: '赋权角色',
      labelWidth:180,
      placeholder:'请选择赋权角色',
      prop: 'roleId',
      type: 'select',
      hide: true,
      sortable:true,
      dicUrl: '/upms/role/list',
      props: {
        label: 'roleName',
        value: 'id'
      },
      rules: [{
        required: true,
        message: '请选择赋权角色',
        trigger: 'change'
      }],
      editDisplay: false,
      viewDisplay: false
    },
    {
      label: '创建时间',
      labelWidth:180,
      placeholder:'请选择创建时间',
      prop: 'createTime',
      hide: true,
      editDisplay: false,
      addDisplay: false
    },
    {
      label: '修改时间',
      labelWidth:180,
      placeholder:'请选择修改时间',
      prop: 'updateTime',
      hide: true,
      editDisplay: false,
      addDisplay: false
    }
  ]
}
