'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

require('./hooks/index.js');
require('./constants/index.js');
var version = require('./version.js');
var key = require('./constants/key.js');
var index = require('./hooks/use-global-config/index.js');

const makeInstaller = (components = []) => {
  const install = (app, options) => {
    if (app[key.INSTALLED_KEY])
      return;
    app[key.INSTALLED_KEY] = true;
    components.forEach((c) => app.use(c));
    if (options)
      index.provideGlobalConfig(options, app, true);
  };
  return {
    version: version.version,
    install
  };
};

exports.makeInstaller = makeInstaller;
//# sourceMappingURL=make-installer.js.map
