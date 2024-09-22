'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var index$1 = require('../../icon/index.js');
require('../../../tokens/index.js');
require('../../../hooks/index.js');
var breadcrumbItem = require('./breadcrumb-item.js');
var pluginVue_exportHelper = require('../../../_virtual/plugin-vue_export-helper.js');
var breadcrumb = require('../../../tokens/breadcrumb.js');
var index = require('../../../hooks/use-namespace/index.js');

const __default__ = vue.defineComponent({
  name: "ElBreadcrumbItem"
});
const _sfc_main = /* @__PURE__ */ vue.defineComponent({
  ...__default__,
  props: breadcrumbItem.breadcrumbItemProps,
  setup(__props) {
    const props = __props;
    const instance = vue.getCurrentInstance();
    const breadcrumbContext = vue.inject(breadcrumb.breadcrumbKey, void 0);
    const ns = index.useNamespace("breadcrumb");
    const { separator, separatorIcon } = vue.toRefs(breadcrumbContext);
    const router = instance.appContext.config.globalProperties.$router;
    const link = vue.ref();
    const onClick = () => {
      if (!props.to || !router)
        return;
      props.replace ? router.replace(props.to) : router.push(props.to);
    };
    return (_ctx, _cache) => {
      return vue.openBlock(), vue.createElementBlock("span", {
        class: vue.normalizeClass(vue.unref(ns).e("item"))
      }, [
        vue.createElementVNode("span", {
          ref_key: "link",
          ref: link,
          class: vue.normalizeClass([vue.unref(ns).e("inner"), vue.unref(ns).is("link", !!_ctx.to)]),
          role: "link",
          onClick
        }, [
          vue.renderSlot(_ctx.$slots, "default")
        ], 2),
        vue.unref(separatorIcon) ? (vue.openBlock(), vue.createBlock(vue.unref(index$1.ElIcon), {
          key: 0,
          class: vue.normalizeClass(vue.unref(ns).e("separator"))
        }, {
          default: vue.withCtx(() => [
            (vue.openBlock(), vue.createBlock(vue.resolveDynamicComponent(vue.unref(separatorIcon))))
          ]),
          _: 1
        }, 8, ["class"])) : (vue.openBlock(), vue.createElementBlock("span", {
          key: 1,
          class: vue.normalizeClass(vue.unref(ns).e("separator")),
          role: "presentation"
        }, vue.toDisplayString(vue.unref(separator)), 3))
      ], 2);
    };
  }
});
var BreadcrumbItem = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/breadcrumb/src/breadcrumb-item.vue"]]);

exports["default"] = BreadcrumbItem;
//# sourceMappingURL=breadcrumb-item2.js.map
