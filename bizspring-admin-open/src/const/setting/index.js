const dicData = [{
  label: '关闭',
  value: 'false'
},{
  label: '开启',
  value: 'true'
}]
export const list = [{
  key: 'showTag',
  commit: 'SET_SHOWTAG'
}, {
  key: 'showTheme',
  commit: 'SET_SHOWTHEME'
}, {
  key: 'showColor',
  commit: 'SET_SHOWCOLOR'
}, {
  key: 'showLock',
  commit: 'SET_SHOWLOCK'
}, {
  key: 'showDebug',
  commit: 'SET_SHOWDEBUG'
}, {
  key: 'showFullScren',
  commit: 'SET_SHOWFULLSCREN'
}, {
  key: 'showCollapse',
  commit: 'SET_SHOWCOLLAPSE'
}, {
  key: 'showSearch',
  commit: 'SET_SHOWSEARCH'
}, {
  key: 'showMenu',
  commit: 'SET_SHOWMENU'
}]
export const option = (safe) => {
  const _safe = safe
  return {
    submitBtn: false,
    emptyBtn:false,
    'labelPosition':'right',
    'labelWidth':'50',
    column: [{
      label: '标签',
      prop: 'showTag',
      type: 'switch',
      span: 24,
      dicData: dicData,
      click: ({ column }) => {
        _safe.set(column.prop)
      }
    }, {
      label: '日志',
      prop: 'showDebug',
      type: 'switch',
      span: 24,
      dicData: dicData,
      click: ({ column }) => {
        _safe.set(column.prop)
      }
    },
    {
      label: '主题色',
      prop: 'showColor',
      type: 'switch',
      span: 24,
      dicData: dicData,
      click: ({ column }) => {
        _safe.set(column.prop)
      }
    }, {
      label: '全屏',
      prop: 'showFullScren',
      type: 'switch',
      span: 24,
      dicData: dicData,
      click: ({ column }) => {
        _safe.set(column.prop)
      }
    }, {
      label: '锁屏',
      prop: 'showLock',
      type: 'switch',
      span: 24,
      dicData: dicData,
      click: ({ column }) => {
        _safe.set(column.prop)
      }
    }, {
      label: '搜索',
      prop: 'showSearch',
      type: 'switch',
      span: 24,
      dicData: dicData,
      click: ({ column }) => {
        _safe.set(column.prop)
      }
    }, {
      label: '缩放',
      prop: 'showCollapse',
      type: 'switch',
      span: 24,
      dicData: dicData,
      click: ({ column }) => {
        _safe.set(column.prop)
      }
    }]
  }
}
