'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../../utils/index.js');
require('../../../../hooks/index.js');
var draggable = require('../utils/draggable.js');
var pluginVue_exportHelper = require('../../../../_virtual/plugin-vue_export-helper.js');
var index = require('../../../../hooks/use-namespace/index.js');
var position = require('../../../../utils/dom/position.js');

const _sfc_main = vue.defineComponent({
  name: "ElColorAlphaSlider",
  props: {
    color: {
      type: Object,
      required: true
    },
    vertical: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {
    const ns = index.useNamespace("color-alpha-slider");
    const instance = vue.getCurrentInstance();
    const thumb = vue.shallowRef();
    const bar = vue.shallowRef();
    const thumbLeft = vue.ref(0);
    const thumbTop = vue.ref(0);
    const background = vue.ref();
    vue.watch(() => props.color.get("alpha"), () => {
      update();
    });
    vue.watch(() => props.color.value, () => {
      update();
    });
    function getThumbLeft() {
      if (!thumb.value)
        return 0;
      if (props.vertical)
        return 0;
      const el = instance.vnode.el;
      const alpha = props.color.get("alpha");
      if (!el)
        return 0;
      return Math.round(alpha * (el.offsetWidth - thumb.value.offsetWidth / 2) / 100);
    }
    function getThumbTop() {
      if (!thumb.value)
        return 0;
      const el = instance.vnode.el;
      if (!props.vertical)
        return 0;
      const alpha = props.color.get("alpha");
      if (!el)
        return 0;
      return Math.round(alpha * (el.offsetHeight - thumb.value.offsetHeight / 2) / 100);
    }
    function getBackground() {
      if (props.color && props.color.value) {
        const { r, g, b } = props.color.toRgb();
        return `linear-gradient(to right, rgba(${r}, ${g}, ${b}, 0) 0%, rgba(${r}, ${g}, ${b}, 1) 100%)`;
      }
      return "";
    }
    function handleClick(event) {
      const target = event.target;
      if (target !== thumb.value) {
        handleDrag(event);
      }
    }
    function handleDrag(event) {
      if (!bar.value || !thumb.value)
        return;
      const el = instance.vnode.el;
      const rect = el.getBoundingClientRect();
      const { clientX, clientY } = position.getClientXY(event);
      if (!props.vertical) {
        let left = clientX - rect.left;
        left = Math.max(thumb.value.offsetWidth / 2, left);
        left = Math.min(left, rect.width - thumb.value.offsetWidth / 2);
        props.color.set("alpha", Math.round((left - thumb.value.offsetWidth / 2) / (rect.width - thumb.value.offsetWidth) * 100));
      } else {
        let top = clientY - rect.top;
        top = Math.max(thumb.value.offsetHeight / 2, top);
        top = Math.min(top, rect.height - thumb.value.offsetHeight / 2);
        props.color.set("alpha", Math.round((top - thumb.value.offsetHeight / 2) / (rect.height - thumb.value.offsetHeight) * 100));
      }
    }
    function update() {
      thumbLeft.value = getThumbLeft();
      thumbTop.value = getThumbTop();
      background.value = getBackground();
    }
    vue.onMounted(() => {
      if (!bar.value || !thumb.value)
        return;
      const dragConfig = {
        drag: (event) => {
          handleDrag(event);
        },
        end: (event) => {
          handleDrag(event);
        }
      };
      draggable.draggable(bar.value, dragConfig);
      draggable.draggable(thumb.value, dragConfig);
      update();
    });
    return {
      thumb,
      bar,
      thumbLeft,
      thumbTop,
      background,
      handleClick,
      update,
      ns
    };
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return vue.openBlock(), vue.createElementBlock("div", {
    class: vue.normalizeClass([_ctx.ns.b(), _ctx.ns.is("vertical", _ctx.vertical)])
  }, [
    vue.createElementVNode("div", {
      ref: "bar",
      class: vue.normalizeClass(_ctx.ns.e("bar")),
      style: vue.normalizeStyle({
        background: _ctx.background
      }),
      onClick: _cache[0] || (_cache[0] = (...args) => _ctx.handleClick && _ctx.handleClick(...args))
    }, null, 6),
    vue.createElementVNode("div", {
      ref: "thumb",
      class: vue.normalizeClass(_ctx.ns.e("thumb")),
      style: vue.normalizeStyle({
        left: _ctx.thumbLeft + "px",
        top: _ctx.thumbTop + "px"
      })
    }, null, 6)
  ], 2);
}
var AlphaSlider = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["render", _sfc_render], ["__file", "/home/runner/work/element-plus/element-plus/packages/components/color-picker/src/components/alpha-slider.vue"]]);

exports["default"] = AlphaSlider;
//# sourceMappingURL=alpha-slider.js.map
