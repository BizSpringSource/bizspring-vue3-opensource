import type { ExtractPropTypes } from 'vue';
import type Divider from './divider.vue';
export declare type BorderStyle = CSSStyleDeclaration['borderStyle'];
export declare const dividerProps: {
    readonly direction: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "vertical" | "horizontal", unknown, "horizontal", boolean>;
    readonly contentPosition: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "left" | "right" | "center", unknown, "center", boolean>;
    readonly borderStyle: import("element-plus/es/utils").EpPropFinalized<(new (...args: any[]) => string & {}) | (() => string) | ((new (...args: any[]) => string & {}) | (() => string))[], unknown, unknown, "solid", boolean>;
};
export declare type DividerProps = ExtractPropTypes<typeof dividerProps>;
export declare type DividerInstance = InstanceType<typeof Divider>;
