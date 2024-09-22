'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
require('../../../utils/index.js');
require('../../../hooks/index.js');
var token = require('./token.js');
var vnode = require('../../../utils/vue/vnode.js');
var style = require('../../../utils/dom/style.js');
var index = require('../../../hooks/use-namespace/index.js');

var ElDescriptionsCell = vue.defineComponent({
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
    const descriptions = vue.inject(token.descriptionsKey, {});
    return {
      descriptions
    };
  },
  render() {
    var _a, _b, _c, _d, _e, _f;
    const item = vnode.getNormalizedProps(this.cell);
    const { border, direction } = this.descriptions;
    const isVertical = direction === "vertical";
    const label = ((_c = (_b = (_a = this.cell) == null ? void 0 : _a.children) == null ? void 0 : _b.label) == null ? void 0 : _c.call(_b)) || item.label;
    const content = (_f = (_e = (_d = this.cell) == null ? void 0 : _d.children) == null ? void 0 : _e.default) == null ? void 0 : _f.call(_e);
    const span = item.span;
    const align = item.align ? `is-${item.align}` : "";
    const labelAlign = item.labelAlign ? `is-${item.labelAlign}` : align;
    const className = item.className;
    const labelClassName = item.labelClassName;
    const style$1 = {
      width: style.addUnit(item.width),
      minWidth: style.addUnit(item.minWidth)
    };
    const ns = index.useNamespace("descriptions");
    switch (this.type) {
      case "label":
        return vue.h(this.tag, {
          style: style$1,
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
        return vue.h(this.tag, {
          style: style$1,
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
        return vue.h("td", {
          style: style$1,
          class: [ns.e("cell"), align],
          colSpan: span
        }, [
          vue.h("span", {
            class: [ns.e("label"), labelClassName]
          }, label),
          vue.h("span", {
            class: [ns.e("content"), className]
          }, content)
        ]);
    }
  }
});

exports["default"] = ElDescriptionsCell;
//# sourceMappingURL=descriptions-cell.js.map
