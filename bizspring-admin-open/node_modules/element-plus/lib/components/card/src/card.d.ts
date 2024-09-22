import type { ExtractPropTypes, StyleValue } from 'vue';
export declare const cardProps: {
    readonly header: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    readonly bodyStyle: import("element-plus/es/utils").EpPropFinalized<(new (...args: any[]) => StyleValue & {}) | (() => StyleValue) | ((new (...args: any[]) => StyleValue & {}) | (() => StyleValue))[], unknown, unknown, "", boolean>;
    readonly shadow: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "hover" | "always" | "never", unknown, "always", boolean>;
};
export declare type CardProps = ExtractPropTypes<typeof cardProps>;
