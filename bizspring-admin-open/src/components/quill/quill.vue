<template>
  <div class="quill-box">
    <div class="quill-editor">
      <div class="editor" ref="editor"></div>
    </div>
  </div>
</template>

<script>
// 引入原始组件
import * as Quill from 'quill'
// 引入核心样式和主题样式
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
// 引入自定义blot
import blotSelect from './blot'
blotSelect(Quill)

export default {
  name: 'quill-xiumi-135',
  props: {
    options: Object,
    // 用于双向绑定
    content: String,
    value: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      visible: false,
      visible2: false,
      selection: {}, // 光标位置
      fullheight: document.documentElement.clientHeight, // 给quill容器设置了个高度
      quill: null, // 待初始化的编辑器
    }
  },
  mounted () {
    // 初始化编辑器
    this._initEditor()
    // 暴露方法绑定到window上，给public\pluging\xiumi-ue-dialog-v5.html使用
    window.setRichText = this.setRichText
    // 调用135页面的时候 带入数据 getHtml()
    window.getHtml = this.getHtml
  },
  methods:{
    // 初始化编辑器
    _initEditor () {
      // 获取编辑器的DOM容器
      let editorDom = this.$el.querySelector('.editor')
      // 初始化编辑器
      this.quill = new Quill(editorDom, this.options)
      // 监听光标位置
      this.quill.on('selection-change', range => {
        this.selection = this.quill.getSelection()
        if (!range) {
          this.$emit('blur', this.quill)
        } else {
          this.$emit('focus', this.quill)
        }
      })
      // 双向绑定代码 v-model
      this.quill.on('text-change', () => {
          this.emitChange()
          this.selection = this.quill.getSelection()
        })
      // 插入内容
      this.firstSetHtml()
      // 粘贴板监听
      this.listenPaste()
      this.$emit('ready', this.quill)
    },
    // 回显内容时检查秀米代码
    firstSetHtml() {
      // value 为回显内容
      if(this.value) {
        // 判断是否有秀米和或135元素
        if(this.value.indexOf('xiumi.us') > -1 || this.value.indexOf('135editor.com') > -1 ) {
          let originNode =  new DOMParser().parseFromString(this.value,'text/html').body.childNodes
          this.nodesInQuill(originNode)
        } else {
          // 正常插入
          this.quill.clipboard.dangerouslyPasteHTML(this.value)
        }
      }
    },
    // 根据node类型分发处理
    nodesInQuill(originNode) {
      for(let i = originNode.length - 1; i >= 0; i --) {
        if(originNode[i].localName === 'section') {
          // 秀米类型代码，走新blot
          this.setRichText(originNode[i].outerHTML, 0)
        } else {
          // 正常插入
          this.quill.clipboard.dangerouslyPasteHTML(0, originNode[i].outerHTML)
        }
      }
    },
    // 监听粘贴板
    listenPaste() {
        this.quill.root.addEventListener('paste', (e) => {
          let msg = (e.clipboardData || window.clipboardData).getData('text/html') // 获取粘贴板文本
          if(msg) {
            if(msg.indexOf("xiumi.us") > -1 || msg.indexOf("_135editor") > -1) {
              let value = new DOMParser().parseFromString(msg,'text/html').body.childNodes // 获取nodes
              e.preventDefault() // 阻止复制动作
              e.stopPropagation()// 阻止冒泡
              this.nodesInQuill(value) // 根据不同标签，使用不同的插入方法
            }
          }
        })
    },
    // 更新text-change
    emitChange() {
      // 获取到quill 根dom中的html
      let html = this.$refs.editor.children[0].innerHTML
      const quill = this.quill
      const text = this.quill.getText()
      if (html === '<p><br></p>') html = ''
      // v-model相关
      this.$emit('input', html)
      this.$emit('change', { html, text, quill })
      // 返回quill中文本长度
      // bug注意：这个方法无法计算秀米代码的中的文字长度！
      this.$emit("getConetntLength", this.quill.getLength())
    },
    setRichText(e, t) {
      // 插入位置
      const index = this.selection?this.selection.index: 0
      // 插入数据方法（位置，blot，数据）
      this.quill.insertEmbed(t || index, 'AppPanelEmbed', e)
      // 插入成功后，不管有没有用到a-modal，设置为false就对了
      this.visible = false
      this.visible2 = false
    },
    // 秀米 modal
    showModal() {
      this.visible = true
    },
    // 135 modal
    showModal2() {
      this.visible2 = true
    },
    // 获取html内容
    getHtml() {
      return this.$refs.editor.children[0].innerHTML
    }

  }
}
</script>

<style>

</style>
