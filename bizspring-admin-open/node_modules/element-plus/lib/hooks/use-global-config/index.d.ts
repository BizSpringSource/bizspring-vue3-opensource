import type { MaybeRef } from '@vueuse/core';
import type { App, Ref } from 'vue';
import type { ConfigProviderContext } from 'element-plus/es/tokens';
export declare function useGlobalConfig<K extends keyof ConfigProviderContext, D extends ConfigProviderContext[K]>(key: K, defaultValue?: D): Ref<Exclude<ConfigProviderContext[K], undefined> | D>;
export declare function useGlobalConfig(): Ref<ConfigProviderContext>;
export declare const provideGlobalConfig: (config: MaybeRef<ConfigProviderContext>, app?: App<any> | undefined, global?: boolean) => import("vue").ComputedRef<Partial<import("../..").ConfigProviderProps>> | undefined;
