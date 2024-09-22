import { defineComponent, inject, computed, openBlock, createElementBlock, unref, normalizeClass, normalizeStyle, createElementVNode, renderSlot, toDisplayString, createVNode, withCtx, createBlock, resolveDynamicComponent, createCommentVNode } from 'vue';
import { ElIcon } from '../../icon/index.mjs';
import '../../focus-trap/index.mjs';
import '../../../hooks/index.mjs';
import '../../../utils/index.mjs';
import '../../../tokens/index.mjs';
import { dialogContentProps, dialogContentEmits } from './dialog-content.mjs';
import _export_sfc from '../../../_virtual/plugin-vue_export-helper.mjs';
import { useLocale } from '../../../hooks/use-locale/index.mjs';
import { CloseComponents } from '../../../utils/vue/icon.mjs';
import { dialogInjectionKey } from '../../../tokens/dialog.mjs';
import { FOCUS_TRAP_INJECTION_KEY } from '../../focus-trap/src/tokens.mjs';
import { composeRefs } from '../../../utils/vue/refs.mjs';
import { useDraggable } from '../../../hooks/use-draggable/index.mjs';

const _hoisted_1 = ["aria-label"];
const _hoisted_2 = ["id"];
const __default__ = defineComponent({ name: "ElDialogContent" });
const _sfc_main = /* @__PURE__ */ defineComponent({
  ...__default__,
  props: dialogContentProps,
  emits: dialogContentEmits,
  setup(__props) {
    const props = __props;
    const { t } = useLocale();
    const { Close } = CloseComponents;
    const { dialogRef, headerRef, bodyId, ns, style } = inject(dialogInjectionKey);
    const { focusTrapRef } = inject(FOCUS_TRAP_INJECTION_KEY);
    const composedDialogRef = composeRefs(focusTrapRef, dialogRef);
    const draggable = computed(() => props.draggable);
    useDraggable(dialogRef, headerRef, draggable);
    return (_ctx, _cache) => {
      return openBlock(), createElementBlock("div", {
        ref: unref(composedDialogRef),
        class: normalizeClass([
          unref(ns).b(),
          unref(ns).is("fullscreen", _ctx.fullscreen),
          unref(ns).is("draggable", unref(draggable)),
          unref(ns).is("align-center", _ctx.alignCenter),
          { [unref(ns).m("center")]: _ctx.center },
          _ctx.customClass
        ]),
        style: normalizeStyle(unref(style)),
        tabindex: "-1"
      }, [
        createElementVNode("header", {
          ref_key: "headerRef",
          ref: headerRef,
          class: normalizeClass(unref(ns).e("header"))
        }, [
          renderSlot(_ctx.$slots, "header", {}, () => [
            createElementVNode("span", {
              role: "heading",
              class: normalizeClass(unref(ns).e("title"))
            }, toDisplayString(_ctx.title), 3)
          ]),
          _ctx.showClose ? (openBlock(), createElementBlock("button", {
            key: 0,
            "aria-label": unref(t)("el.dialog.close"),
            class: normalizeClass(unref(ns).e("headerbtn")),
            type: "button",
            onClick: _cache[0] || (_cache[0] = ($event) => _ctx.$emit("close"))
          }, [
            createVNode(unref(ElIcon), {
              class: normalizeClass(unref(ns).e("close"))
            }, {
              default: withCtx(() => [
                (openBlock(), createBlock(resolveDynamicComponent(_ctx.closeIcon || unref(Close))))
              ]),
              _: 1
            }, 8, ["class"])
          ], 10, _hoisted_1)) : createCommentVNode("v-if", true)
        ], 2),
        createElementVNode("div", {
          id: unref(bodyId),
          class: normalizeClass(unref(ns).e("body"))
        }, [
          renderSlot(_ctx.$slots, "default")
        ], 10, _hoisted_2),
        _ctx.$slots.footer ? (openBlock(), createElementBlock("footer", {
          key: 0,
          class: normalizeClass(unref(ns).e("footer"))
        }, [
          renderSlot(_ctx.$slots, "footer")
        ], 2)) : createCommentVNode("v-if", true)
      ], 6);
    };
  }
});
var ElDialogContent = /* @__PURE__ */ _export_sfc(_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/dialog/src/dialog-content.vue"]]);

export { ElDialogContent as default };
//# sourceMappingURL=dialog-content2.mjs.map
