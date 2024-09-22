import '../../../../utils/index.mjs';
import { panelRangeSharedProps } from './shared.mjs';
import { buildProps } from '../../../../utils/vue/props/runtime.mjs';

const panelMonthRangeProps = buildProps({
  ...panelRangeSharedProps
});
const panelMonthRangeEmits = ["pick", "set-picker-option"];

export { panelMonthRangeEmits, panelMonthRangeProps };
//# sourceMappingURL=panel-month-range.mjs.map
