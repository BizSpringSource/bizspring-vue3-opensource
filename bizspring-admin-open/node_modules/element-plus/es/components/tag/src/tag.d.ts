import type Tag from './tag.vue';
import type { ExtractPropTypes } from 'vue';
export declare const tagProps: {
    readonly closable: BooleanConstructor;
    readonly type: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "" | "success" | "warning" | "info" | "danger", unknown, "", boolean>;
    readonly hit: BooleanConstructor;
    readonly disableTransitions: BooleanConstructor;
    readonly color: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    readonly size: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "" | "default" | "small" | "large", unknown, "", boolean>;
    readonly effect: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "plain" | "dark" | "light", unknown, "light", boolean>;
    readonly round: BooleanConstructor;
};
export declare type TagProps = ExtractPropTypes<typeof tagProps>;
export declare const tagEmits: {
    close: (evt: MouseEvent) => boolean;
    click: (evt: MouseEvent) => boolean;
};
export declare type TagEmits = typeof tagEmits;
export declare type TagInstance = InstanceType<typeof Tag>;
