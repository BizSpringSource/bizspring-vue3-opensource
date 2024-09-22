import { inject } from 'vue';
import '../../../tokens/index.mjs';
import { elPaginationKey } from '../../../tokens/pagination.mjs';

const usePagination = () => inject(elPaginationKey, {});

export { usePagination };
//# sourceMappingURL=usePagination.mjs.map
