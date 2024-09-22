import { defineComponent, inject, h } from 'vue';
import '../../../utils/index.mjs';
import '../../../hooks/index.mjs';
import { descriptionsKey } from './token.mjs';
import { getNormalizedProps } from '../../../utils/vue/vnode.mjs';
import { addUnit } from '../../../utils/dom/style.mjs';
import { useNamespace } from '../../../hooks/use-namespace/index.mjs';

var ElDescriptionsCell = defineComponent({
  name: "ElDescriptionsCell",
  props: {
    cell: {
      type: Object
    },
    tag: {
      type: String
    },
    type: {
      type: String
    }
  },
  setup() {
    const descriptions = inject(descriptionsKey, {});
    return {
      descriptions
    };
  },
  render() {
    var _a, _b, _c, _d, _e, _f;
    const item = getNormalizedProps(this.cell);
    const { border, direction } = this.descriptions;
    const isVertical = direction === "vertical";
    const label = ((_c = (_b = (_a = this.cell) == null ? void 0 : _a.children) == null ? void 0 : _b.label) == null ? void 0 : _c.call(_b)) || item.label;
    const content = (_f = (_e = (_d = this.cell) == null ? void 0 : _d.children) == null ? void 0 : _e.default) == null ? void 0 : _f.call(_e);
    const span = item.span;
    const align = item.align ? `is-${item.align}` : "";
    const labelAlign = item.labelAlign ? `is-${item.labelAlign}` : align;
    const className = item.className;
    const labelClassName = item.labelClassName;
    const style = {
      width: addUnit(item.width),
      minWidth: addUnit(item.minWidth)
    };
    const ns = useNamespace("descriptions");
    switch (this.type) {
      case "label":
        return h(this.tag, {
          style,
          class: [
            ns.e("cell"),
            ns.e("label"),
            ns.is("bordered-label", border),
            ns.is("vertical-label", isVertical),
            labelAlign,
            labelClassName
          ],
          colSpan: isVertical ? span : 1
        }, label);
      case "content":
        return h(this.tag, {
          style,
          class: [
            ns.e("cell"),
            ns.e("content"),
            ns.is("bordered-content", border),
            ns.is("vertical-content", isVertical),
            align,
            className
          ],
          colSpan: isVertical ? span : span * 2 - 1
        }, content);
      default:
        return h("td", {
          style,
          class: [ns.e("cell"), align],
          colSpan: span
        }, [
          h("span", {
            class: [ns.e("label"), labelClassName]
          }, label),
          h("span", {
            class: [ns.e("content"), className]
          }, content)
        ]);
    }
  }
});

export { ElDescriptionsCell as default };
//# sourceMappingURL=descriptions-cell.mjs.map
