import type { ExtractPropTypes } from 'vue';
import type Row from './row.vue';
export declare const RowJustify: readonly ["start", "center", "end", "space-around", "space-between", "space-evenly"];
export declare const RowAlign: readonly ["top", "middle", "bottom"];
export declare const rowProps: {
    readonly tag: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "div", boolean>;
    readonly gutter: import("element-plus/es/utils").EpPropFinalized<NumberConstructor, unknown, unknown, 0, boolean>;
    readonly justify: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "center" | "space-around" | "space-between" | "space-evenly" | "end" | "start", unknown, "start", boolean>;
    readonly align: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "top" | "bottom" | "middle", unknown, "top", boolean>;
};
export declare type RowProps = ExtractPropTypes<typeof rowProps>;
export declare type RowInstance = InstanceType<typeof Row>;
