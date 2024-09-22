import '../../utils/index.mjs';
import ConfigProvider from './src/config-provider.mjs';
export { configProviderProps, messageConfig } from './src/config-provider.mjs';
import { withInstall } from '../../utils/vue/install.mjs';

const ElConfigProvider = withInstall(ConfigProvider);

export { ElConfigProvider, ElConfigProvider as default };
//# sourceMappingURL=index.mjs.map
