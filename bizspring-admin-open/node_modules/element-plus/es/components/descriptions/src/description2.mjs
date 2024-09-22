import { defineComponent, useSlots, provide, computed, openBlock, createElementBlock, normalizeClass, unref, createElementVNode, renderSlot, createTextVNode, toDisplayString, createCommentVNode, Fragment, renderList, createBlock } from 'vue';
import '../../../utils/index.mjs';
import '../../../hooks/index.mjs';
import ElDescriptionsRow from './descriptions-row2.mjs';
import { descriptionsKey } from './token.mjs';
import { descriptionProps } from './description.mjs';
import _export_sfc from '../../../_virtual/plugin-vue_export-helper.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';
import { useSize } from '../../../hooks/use-common-props/index.mjs';
import { flattedChildren } from '../../../utils/vue/vnode.mjs';

const __default__ = defineComponent({
  name: "ElDescriptions"
});
const _sfc_main = /* @__PURE__ */ defineComponent({
  ...__default__,
  props: descriptionProps,
  setup(__props) {
    const props = __props;
    const ns = useNamespace("descriptions");
    const descriptionsSize = useSize();
    const slots = useSlots();
    provide(descriptionsKey, props);
    const descriptionKls = computed(() => [ns.b(), ns.m(descriptionsSize.value)]);
    const filledNode = (node, span, count, isLast = false) => {
      if (!node.props) {
        node.props = {};
      }
      if (span > count) {
        node.props.span = count;
      }
      if (isLast) {
        node.props.span = span;
      }
      return node;
    };
    const getRows = () => {
      var _a;
      const children = flattedChildren((_a = slots.default) == null ? void 0 : _a.call(slots)).filter((node) => {
        var _a2;
        return ((_a2 = node == null ? void 0 : node.type) == null ? void 0 : _a2.name) === "ElDescriptionsItem";
      });
      const rows = [];
      let temp = [];
      let count = props.column;
      let totalSpan = 0;
      children.forEach((node, index) => {
        var _a2;
        const span = ((_a2 = node.props) == null ? void 0 : _a2.span) || 1;
        if (index < children.length - 1) {
          totalSpan += span > count ? count : span;
        }
        if (index === children.length - 1) {
          const lastSpan = props.column - totalSpan % props.column;
          temp.push(filledNode(node, lastSpan, count, true));
          rows.push(temp);
          return;
        }
        if (span < count) {
          count -= span;
          temp.push(node);
        } else {
          temp.push(filledNode(node, span, count));
          rows.push(temp);
          count = props.column;
          temp = [];
        }
      });
      return rows;
    };
    return (_ctx, _cache) => {
      return openBlock(), createElementBlock("div", {
        class: normalizeClass(unref(descriptionKls))
      }, [
        _ctx.title || _ctx.extra || _ctx.$slots.title || _ctx.$slots.extra ? (openBlock(), createElementBlock("div", {
          key: 0,
          class: normalizeClass(unref(ns).e("header"))
        }, [
          createElementVNode("div", {
            class: normalizeClass(unref(ns).e("title"))
          }, [
            renderSlot(_ctx.$slots, "title", {}, () => [
              createTextVNode(toDisplayString(_ctx.title), 1)
            ])
          ], 2),
          createElementVNode("div", {
            class: normalizeClass(unref(ns).e("extra"))
          }, [
            renderSlot(_ctx.$slots, "extra", {}, () => [
              createTextVNode(toDisplayString(_ctx.extra), 1)
            ])
          ], 2)
        ], 2)) : createCommentVNode("v-if", true),
        createElementVNode("div", {
          class: normalizeClass(unref(ns).e("body"))
        }, [
          createElementVNode("table", {
            class: normalizeClass([unref(ns).e("table"), unref(ns).is("bordered", _ctx.border)])
          }, [
            createElementVNode("tbody", null, [
              (openBlock(true), createElementBlock(Fragment, null, renderList(getRows(), (row, index) => {
                return openBlock(), createBlock(ElDescriptionsRow, {
                  key: index,
                  row
                }, null, 8, ["row"]);
              }), 128))
            ])
          ], 2)
        ], 2)
      ], 2);
    };
  }
});
var Descriptions = /* @__PURE__ */ _export_sfc(_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/descriptions/src/description.vue"]]);

export { Descriptions as default };
//# sourceMappingURL=description2.mjs.map
