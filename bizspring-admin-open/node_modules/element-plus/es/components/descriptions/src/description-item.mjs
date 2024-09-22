import { defineComponent } from 'vue';

var DescriptionsItem = defineComponent({
  name: "ElDescriptionsItem",
  props: {
    label: {
      type: String,
      default: ""
    },
    span: {
      type: Number,
      default: 1
    },
    width: {
      type: [String, Number],
      default: ""
    },
    minWidth: {
      type: [String, Number],
      default: ""
    },
    align: {
      type: String,
      default: "left"
    },
    labelAlign: {
      type: String,
      default: ""
    },
    className: {
      type: String,
      default: ""
    },
    labelClassName: {
      type: String,
      default: ""
    }
  }
});

export { DescriptionsItem as default };
//# sourceMappingURL=description-item.mjs.map
