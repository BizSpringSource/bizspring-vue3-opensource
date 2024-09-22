import '../../../../utils/index.mjs';
import { buildProps, definePropType } from '../../../../utils/vue/props/runtime.mjs';

const datePickerProps = buildProps({
  type: {
    type: definePropType(String),
    default: "date"
  }
});

export { datePickerProps };
//# sourceMappingURL=date-picker.mjs.map
