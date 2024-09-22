import '../../../utils/index.mjs';
import { buildProps } from '../../../utils/vue/props/runtime.mjs';

const descriptionsRowProps = buildProps({
  row: {
    type: Array,
    default: () => []
  }
});

export { descriptionsRowProps };
//# sourceMappingURL=descriptions-row.mjs.map
