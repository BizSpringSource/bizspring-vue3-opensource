'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

const fg = require('fast-glob');
const getEtag = require('etag');
const cors = require('cors');
const fs = require('fs-extra');
const path = require('pathe');
const Debug = require('debug');
const SVGCompiler = require('svg-baker');
const svgo = require('svgo');
const vite = require('vite');

function _interopDefaultLegacy (e) { return e && typeof e === 'object' && 'default' in e ? e["default"] : e; }

const fg__default = /*#__PURE__*/_interopDefaultLegacy(fg);
const getEtag__default = /*#__PURE__*/_interopDefaultLegacy(getEtag);
const cors__default = /*#__PURE__*/_interopDefaultLegacy(cors);
const fs__default = /*#__PURE__*/_interopDefaultLegacy(fs);
const path__default = /*#__PURE__*/_interopDefaultLegacy(path);
const Debug__default = /*#__PURE__*/_interopDefaultLegacy(Debug);
const SVGCompiler__default = /*#__PURE__*/_interopDefaultLegacy(SVGCompiler);

const SVG_ICONS_REGISTER_NAME = "virtual:svg-icons-register";
const SVG_ICONS_CLIENT = "virtual:svg-icons-names";
const SVG_DOM_ID = "__svg__icons__dom__";
const XMLNS = "http://www.w3.org/2000/svg";
const XMLNS_LINK = "http://www.w3.org/1999/xlink";

const debug = Debug__default.debug("vite-plugin-svg-icons");
function createSvgIconsPlugin(opt) {
  const cache = /* @__PURE__ */ new Map();
  let isBuild = false;
  const options = {
    svgoOptions: true,
    symbolId: "icon-[dir]-[name]",
    inject: "body-last",
    customDomId: SVG_DOM_ID,
    ...opt
  };
  let { svgoOptions } = options;
  const { symbolId } = options;
  if (!symbolId.includes("[name]")) {
    throw new Error("SymbolId must contain [name] string!");
  }
  if (svgoOptions) {
    svgoOptions = typeof svgoOptions === "boolean" ? {} : svgoOptions;
  }
  debug("plugin options:", options);
  return {
    name: "vite:svg-icons",
    configResolved(resolvedConfig) {
      isBuild = resolvedConfig.command === "build";
      debug("resolvedConfig:", resolvedConfig);
    },
    resolveId(id) {
      if ([SVG_ICONS_REGISTER_NAME, SVG_ICONS_CLIENT].includes(id)) {
        return id;
      }
      return null;
    },
    async load(id, ssr) {
      if (!isBuild && !ssr)
        return null;
      const isRegister = id.endsWith(SVG_ICONS_REGISTER_NAME);
      const isClient = id.endsWith(SVG_ICONS_CLIENT);
      if (ssr && !isBuild && (isRegister || isClient)) {
        return `export default {}`;
      }
      const { code, idSet } = await createModuleCode(cache, svgoOptions, options);
      if (isRegister) {
        return code;
      }
      if (isClient) {
        return idSet;
      }
    },
    configureServer: ({ middlewares }) => {
      middlewares.use(cors__default({ origin: "*" }));
      middlewares.use(async (req, res, next) => {
        const url = vite.normalizePath(req.url);
        const registerId = `/@id/${SVG_ICONS_REGISTER_NAME}`;
        const clientId = `/@id/${SVG_ICONS_CLIENT}`;
        if ([clientId, registerId].some((item) => url.endsWith(item))) {
          res.setHeader("Content-Type", "application/javascript");
          res.setHeader("Cache-Control", "no-cache");
          const { code, idSet } = await createModuleCode(cache, svgoOptions, options);
          const content = url.endsWith(registerId) ? code : idSet;
          res.setHeader("Etag", getEtag__default(content, { weak: true }));
          res.statusCode = 200;
          res.end(content);
        } else {
          next();
        }
      });
    }
  };
}
async function createModuleCode(cache, svgoOptions, options) {
  const { insertHtml, idSet } = await compilerIcons(cache, svgoOptions, options);
  const xmlns = `xmlns="${XMLNS}"`;
  const xmlnsLink = `xmlns:xlink="${XMLNS_LINK}"`;
  const html = insertHtml.replace(new RegExp(xmlns, "g"), "").replace(new RegExp(xmlnsLink, "g"), "");
  const code = `
       if (typeof window !== 'undefined') {
         function loadSvg() {
           var body = document.body;
           var svgDom = document.getElementById('${options.customDomId}');
           if(!svgDom) {
             svgDom = document.createElementNS('${XMLNS}', 'svg');
             svgDom.style.position = 'absolute';
             svgDom.style.width = '0';
             svgDom.style.height = '0';
             svgDom.id = '${options.customDomId}';
             svgDom.setAttribute('xmlns','${XMLNS}');
             svgDom.setAttribute('xmlns:link','${XMLNS_LINK}');
           }
           svgDom.innerHTML = ${JSON.stringify(html)};
           ${domInject(options.inject)}
         }
         if(document.readyState === 'loading') {
           document.addEventListener('DOMContentLoaded', loadSvg);
         } else {
           loadSvg()
         }
      }
        `;
  return {
    code: `${code}
export default {}`,
    idSet: `export default ${JSON.stringify(Array.from(idSet))}`
  };
}
function domInject(inject = "body-last") {
  switch (inject) {
    case "body-first":
      return "body.insertBefore(svgDom, body.firstChild);";
    default:
      return "body.insertBefore(svgDom, body.lastChild);";
  }
}
async function compilerIcons(cache, svgOptions, options) {
  const { iconDirs } = options;
  let insertHtml = "";
  const idSet = /* @__PURE__ */ new Set();
  for (const dir of iconDirs) {
    const svgFilsStats = fg__default.sync("**/*.svg", {
      cwd: dir,
      stats: true,
      absolute: true
    });
    for (const entry of svgFilsStats) {
      const { path: path2, stats: { mtimeMs } = {} } = entry;
      const cacheStat = cache.get(path2);
      let svgSymbol;
      let symbolId;
      let relativeName = "";
      const getSymbol = async () => {
        relativeName = vite.normalizePath(path2).replace(vite.normalizePath(dir + "/"), "");
        symbolId = createSymbolId(relativeName, options);
        svgSymbol = await compilerIcon(path2, symbolId, svgOptions);
        idSet.add(symbolId);
      };
      if (cacheStat) {
        if (cacheStat.mtimeMs !== mtimeMs) {
          await getSymbol();
        } else {
          svgSymbol = cacheStat.code;
          symbolId = cacheStat.symbolId;
          symbolId && idSet.add(symbolId);
        }
      } else {
        await getSymbol();
      }
      svgSymbol && cache.set(path2, {
        mtimeMs,
        relativeName,
        code: svgSymbol,
        symbolId
      });
      insertHtml += `${svgSymbol || ""}`;
    }
  }
  return { insertHtml, idSet };
}
async function compilerIcon(file, symbolId, svgOptions) {
  if (!file) {
    return null;
  }
  let content = fs__default.readFileSync(file, "utf-8");
  if (svgOptions) {
    const { data } = await svgo.optimize(content, svgOptions);
    content = data || content;
  }
  content = content.replace(/stroke="[a-zA-Z#0-9]*"/, 'stroke="currentColor"');
  const svgSymbol = await new SVGCompiler__default().addSymbol({
    id: symbolId,
    content,
    path: file
  });
  return svgSymbol.render();
}
function createSymbolId(name, options) {
  const { symbolId } = options;
  if (!symbolId) {
    return name;
  }
  let id = symbolId;
  let fName = name;
  const { fileName = "", dirName } = discreteDir(name);
  if (symbolId.includes("[dir]")) {
    id = id.replace(/\[dir\]/g, dirName);
    if (!dirName) {
      id = id.replace("--", "-");
    }
    fName = fileName;
  }
  id = id.replace(/\[name\]/g, fName);
  return id.replace(path__default.extname(id), "");
}
function discreteDir(name) {
  if (!vite.normalizePath(name).includes("/")) {
    return {
      fileName: name,
      dirName: ""
    };
  }
  const strList = name.split("/");
  const fileName = strList.pop();
  const dirName = strList.join("-");
  return { fileName, dirName };
}

exports.compilerIcon = compilerIcon;
exports.compilerIcons = compilerIcons;
exports.createModuleCode = createModuleCode;
exports.createSvgIconsPlugin = createSvgIconsPlugin;
exports.createSymbolId = createSymbolId;
exports.discreteDir = discreteDir;
