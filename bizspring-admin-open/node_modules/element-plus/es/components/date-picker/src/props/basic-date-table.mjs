import '../../../../utils/index.mjs';
import { datePickerSharedProps, selectionModeWithDefault } from './shared.mjs';
import { buildProps, definePropType } from '../../../../utils/vue/props/runtime.mjs';

const basicDateTableProps = buildProps({
  ...datePickerSharedProps,
  cellClassName: {
    type: definePropType(Function)
  },
  showWeekNumber: Boolean,
  selectionMode: selectionModeWithDefault("date")
});

export { basicDateTableProps };
//# sourceMappingURL=basic-date-table.mjs.map
