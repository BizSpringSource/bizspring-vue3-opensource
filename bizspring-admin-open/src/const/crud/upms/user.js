import {getByUserName, getCount} from '@/api/upms/user'

const validateUserName = (rule, value, callback) => {
    if (window.openType != 'add'){
        callback()
    }else{
        getByUserName(value).then(response => {
            let user = response.data.data;
            if (user != null && null != user.id) {
                callback(new Error('用户名已存在'))
            } else {
                callback()
            }
        })
    }
}

const validateEmail = (rule, value, callback) => {
  if (window.openType != 'add'){
    callback()
  }else{
    getCount({
      email: value
    }).then(response => {
      let data = response.data.data
      if (data > 0) {
        callback(new Error('邮箱已存在'))
      } else {
        callback()
      }
    })
  }
}

const validatePhone = (rule, value, callback) => {
  if (window.openType != 'add'){
    callback()
  }else{
    getCount({
      mobile: value
    }).then(response => {
      let data = response.data.data
          if (data > 0) {
        callback(new Error('手机号码已存在'))
      } else {
        callback()
      }
    })
  }
}

export const tableOption = {
    dialogDrag: true,
    border: true,
    index: true,
    indexLabel: '序号',
    stripe: true,
    menuAlign: 'center',
    align: 'center',
    viewBtn: true,
    searchBtnText:'搜索',
    emptyBtnText:'重 置',
    addBtnText:'添加',
    viewBtnText:'查看',
    editBtnText:'编辑',
    delBtnText:'删除',
    saveBtnText:'保存',
    updateBtnText:'保存',
    cancelBtnText:'取 消',
    addTitle:'添加',
    editTitle:'编辑',
    viewTitle:'查看',
    menuTitle:'操作',
    excelBtn: true,
    printBtn: true,
    searchMenuSpan: 6,
    column: [{
        fixed: true,
        label: 'id',
        labelWidth:'150',
        prop: 'id',
        span: 24,
        hide: true,
        editDisabled: true,
        addDisplay: false
    }, {
        fixed: true,
        label: '用户名',
        placeholder:'请选择用户名',
        labelWidth:'150',
        prop: 'username',
        editDisabled: true,
        search: true,
        searchLabelWidth:'120',
        searchSpan:8,
        span: 24,
        rules: [{
            required: true,
            message: '请选择用户名'
        },
        {
            min: 4,
            max: 20,
            message: '长度在3至20个字符',
            trigger: 'blur'
        },{
                validator: validateUserName,
                trigger: 'blur'
        }]
    }, {
        label: '密码',
        placeholder:'请输入密码',
        labelWidth:'150',
        prop: 'password',
        type: 'password',
        value: '',
        hide: true,
        viewDisplay: false,
        editDisplay: false,
        span: 24,
        rules: [{
            required: true,
            min: 6,
            max: 20,
            message: '长度在6至20个字符',
            trigger: 'blur'
        }]
    }, {
        label: '机构',
        placeholder:'请选择机构',
        prop: 'organId',
        labelWidth:'150',
        span: 24,
        sortable: true,
        rules: [{
            required: true,
            message: '请选择机构',
            trigger: 'change'
        }],
        type: 'tree',
        search: true,
        searchLabelWidth:'120',
        searchSpan:8,
        props: {
            label: 'name',
            value: 'id'
        },
        defaultExpandAll: true,
        dicUrl: '/upms/organ/tree'
    }, {
        label: '角色',
        labelWidth:'150',
        prop: 'roleIds',
        overHidden: true,
        dataType: 'array',
        span: 24,
        type: 'checkbox',
        rules: [{
            required: true,
            message: '请选择角色',
            trigger: 'blur'
        }],
        props: {
            label: 'roleName',
            value: 'id'
        },
        dicUrl: '/upms/role/list'
    }, {
        label: '是否启用',
        prop: 'isEnabled',
        type: 'radio',
        slot: true,
        sortable: true,
        labelWidth:'150',
        span: 24,
        rules: [{
            required: true,
            message: '请选择是否启用',
            trigger: 'blur'
        }],
        dicData: [{
            label: '是',
            value: '1'
        },{
            label: '否',
            value: '0'
        }]
    }, {
        label: '创建时间',
        labelWidth:'150',
        placeholder:'请选择创建时间',
        prop: 'createdTime',
        type: 'datetime',
        addDisplay: false,
        editDisabled: true,
        sortable: true,
        span: 24,
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'yyyy-MM-dd HH:mm:ss'

    }, {
      label: '邮箱',
      prop: 'email',
      placeholder:'请选择邮箱',
      labelWidth:'150',
      span: 24,
      editDisabled: true,
      rules: [{
        required: true,
        message: '请选择邮箱'
      },{
        validator: validateEmail,
        trigger: 'blur'
      }]
    }, {
      label: '手机号码',
      placeholder:'请选择手机号码',
      prop: 'mobile',
      value: '',
      span: 24,
      labelWidth:'150',
      editDisabled: true,
      rules: [{
        required: true,
        message: '请选择手机号码'
      },{
        min: 11,
        max: 11,
        message: '长度在11个字符',
        trigger: 'blur'
      },{
        validator: validatePhone,
        trigger: 'blur'
      }]
    }]
}
