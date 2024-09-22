import type { App, Plugin } from '@vue/runtime-core';
export declare const makeInstaller: (components?: Plugin[]) => {
    version: string;
    install: (app: App, options?: Partial<import("./components").ConfigProviderProps> | undefined) => void;
};
