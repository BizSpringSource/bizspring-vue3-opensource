import { Plugin } from 'vite';
import { OptimizeOptions } from 'svgo';

declare type DomInject = 'body-first' | 'body-last';
interface ViteSvgIconsPlugin {
    /**
     * icons folder, all svg files in it will be converted to svg sprite.
     */
    iconDirs: string[];
    /**
     * svgo configuration, used to compress svg
     * @default：true
     */
    svgoOptions?: boolean | OptimizeOptions;
    /**
     * icon format
     * @default: icon-[dir]-[name]
     */
    symbolId?: string;
    /**
     * icon format
     * @default: body-last
     */
    inject?: DomInject;
    /**
     * custom dom id
     * @default: __svg__icons__dom__
     */
    customDomId?: string;
}
interface FileStats {
    relativeName: string;
    mtimeMs?: number;
    code: string;
    symbolId?: string;
}

declare function createSvgIconsPlugin(opt: ViteSvgIconsPlugin): Plugin;
declare function createModuleCode(cache: Map<string, FileStats>, svgoOptions: OptimizeOptions, options: ViteSvgIconsPlugin): Promise<{
    code: string;
    idSet: string;
}>;
/**
 * Preload all icons in advance
 * @param cache
 * @param options
 */
declare function compilerIcons(cache: Map<string, FileStats>, svgOptions: OptimizeOptions, options: ViteSvgIconsPlugin): Promise<{
    insertHtml: string;
    idSet: Set<string>;
}>;
declare function compilerIcon(file: string, symbolId: string, svgOptions: OptimizeOptions): Promise<string | null>;
declare function createSymbolId(name: string, options: ViteSvgIconsPlugin): string;
declare function discreteDir(name: string): {
    fileName: string | undefined;
    dirName: string;
};

export { DomInject, FileStats, ViteSvgIconsPlugin, compilerIcon, compilerIcons, createModuleCode, createSvgIconsPlugin, createSymbolId, discreteDir };
