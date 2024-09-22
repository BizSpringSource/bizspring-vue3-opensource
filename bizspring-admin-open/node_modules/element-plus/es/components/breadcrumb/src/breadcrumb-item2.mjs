import { defineComponent, getCurrentInstance, inject, toRefs, ref, openBlock, createElementBlock, normalizeClass, unref, createElementVNode, renderSlot, createBlock, withCtx, resolveDynamicComponent, toDisplayString } from 'vue';
import { ElIcon } from '../../icon/index.mjs';
import '../../../tokens/index.mjs';
import '../../../hooks/index.mjs';
import { breadcrumbItemProps } from './breadcrumb-item.mjs';
import _export_sfc from '../../../_virtual/plugin-vue_export-helper.mjs';
import { breadcrumbKey } from '../../../tokens/breadcrumb.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';

const __default__ = defineComponent({
  name: "ElBreadcrumbItem"
});
const _sfc_main = /* @__PURE__ */ defineComponent({
  ...__default__,
  props: breadcrumbItemProps,
  setup(__props) {
    const props = __props;
    const instance = getCurrentInstance();
    const breadcrumbContext = inject(breadcrumbKey, void 0);
    const ns = useNamespace("breadcrumb");
    const { separator, separatorIcon } = toRefs(breadcrumbContext);
    const router = instance.appContext.config.globalProperties.$router;
    const link = ref();
    const onClick = () => {
      if (!props.to || !router)
        return;
      props.replace ? router.replace(props.to) : router.push(props.to);
    };
    return (_ctx, _cache) => {
      return openBlock(), createElementBlock("span", {
        class: normalizeClass(unref(ns).e("item"))
      }, [
        createElementVNode("span", {
          ref_key: "link",
          ref: link,
          class: normalizeClass([unref(ns).e("inner"), unref(ns).is("link", !!_ctx.to)]),
          role: "link",
          onClick
        }, [
          renderSlot(_ctx.$slots, "default")
        ], 2),
        unref(separatorIcon) ? (openBlock(), createBlock(unref(ElIcon), {
          key: 0,
          class: normalizeClass(unref(ns).e("separator"))
        }, {
          default: withCtx(() => [
            (openBlock(), createBlock(resolveDynamicComponent(unref(separatorIcon))))
          ]),
          _: 1
        }, 8, ["class"])) : (openBlock(), createElementBlock("span", {
          key: 1,
          class: normalizeClass(unref(ns).e("separator")),
          role: "presentation"
        }, toDisplayString(unref(separator)), 3))
      ], 2);
    };
  }
});
var BreadcrumbItem = /* @__PURE__ */ _export_sfc(_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/breadcrumb/src/breadcrumb-item.vue"]]);

export { BreadcrumbItem as default };
//# sourceMappingURL=breadcrumb-item2.mjs.map
