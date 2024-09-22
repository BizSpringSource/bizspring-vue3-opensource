'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('../../utils/index.js');
var configProvider = require('./src/config-provider.js');
var install = require('../../utils/vue/install.js');

const ElConfigProvider = install.withInstall(configProvider["default"]);

exports.configProviderProps = configProvider.configProviderProps;
exports.messageConfig = configProvider.messageConfig;
exports.ElConfigProvider = ElConfigProvider;
exports["default"] = ElConfigProvider;
//# sourceMappingURL=index.js.map
