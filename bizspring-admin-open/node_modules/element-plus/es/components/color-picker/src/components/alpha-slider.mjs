import { defineComponent, getCurrentInstance, shallowRef, ref, watch, onMounted, openBlock, createElementBlock, normalizeClass, createElementVNode, normalizeStyle } from 'vue';
import '../../../../utils/index.mjs';
import '../../../../hooks/index.mjs';
import { draggable } from '../utils/draggable.mjs';
import _export_sfc from '../../../../_virtual/plugin-vue_export-helper.mjs';
import { useNamespace } from '../../../../hooks/use-namespace/index.mjs';
import { getClientXY } from '../../../../utils/dom/position.mjs';

const _sfc_main = defineComponent({
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
    const ns = useNamespace("color-alpha-slider");
    const instance = getCurrentInstance();
    const thumb = shallowRef();
    const bar = shallowRef();
    const thumbLeft = ref(0);
    const thumbTop = ref(0);
    const background = ref();
    watch(() => props.color.get("alpha"), () => {
      update();
    });
    watch(() => props.color.value, () => {
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
      const { clientX, clientY } = getClientXY(event);
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
    onMounted(() => {
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
      draggable(bar.value, dragConfig);
      draggable(thumb.value, dragConfig);
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
  return openBlock(), createElementBlock("div", {
    class: normalizeClass([_ctx.ns.b(), _ctx.ns.is("vertical", _ctx.vertical)])
  }, [
    createElementVNode("div", {
      ref: "bar",
      class: normalizeClass(_ctx.ns.e("bar")),
      style: normalizeStyle({
        background: _ctx.background
      }),
      onClick: _cache[0] || (_cache[0] = (...args) => _ctx.handleClick && _ctx.handleClick(...args))
    }, null, 6),
    createElementVNode("div", {
      ref: "thumb",
      class: normalizeClass(_ctx.ns.e("thumb")),
      style: normalizeStyle({
        left: _ctx.thumbLeft + "px",
        top: _ctx.thumbTop + "px"
      })
    }, null, 6)
  ], 2);
}
var AlphaSlider = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "/home/runner/work/element-plus/element-plus/packages/components/color-picker/src/components/alpha-slider.vue"]]);

export { AlphaSlider as default };
//# sourceMappingURL=alpha-slider.mjs.map
