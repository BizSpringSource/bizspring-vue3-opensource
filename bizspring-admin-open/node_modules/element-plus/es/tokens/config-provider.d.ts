import type { ConfigProviderProps } from 'element-plus/es/components/config-provider';
import type { InjectionKey, Ref } from 'vue';
export declare type ConfigProviderContext = Partial<ConfigProviderProps>;
export declare const configProviderContextKey: InjectionKey<Ref<ConfigProviderContext>>;
