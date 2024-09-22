import type { ExtractPropTypes } from 'vue';
import type TabBar from './tab-bar.vue';
export declare const tabBarProps: {
    readonly tabs: import("element-plus/es/utils").EpPropFinalized<(new (...args: any[]) => {
        uid: number;
        slots: Readonly<{
            [name: string]: import("vue").Slot | undefined;
        }>;
        props: {
            readonly disabled: boolean;
            readonly label: string;
            readonly closable: boolean;
            readonly lazy: boolean;
            readonly name?: import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown> | undefined;
        };
        paneName: string | number | undefined;
        active: boolean;
        index: string | undefined;
        isClosable: boolean;
    }[]) | (() => {
        uid: number;
        slots: Readonly<{
            [name: string]: import("vue").Slot | undefined;
        }>;
        props: {
            readonly disabled: boolean;
            readonly label: string;
            readonly closable: boolean;
            readonly lazy: boolean;
            readonly name?: import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown> | undefined;
        };
        paneName: string | number | undefined;
        active: boolean;
        index: string | undefined;
        isClosable: boolean;
    }[]) | ((new (...args: any[]) => {
        uid: number;
        slots: Readonly<{
            [name: string]: import("vue").Slot | undefined;
        }>;
        props: {
            readonly disabled: boolean;
            readonly label: string;
            readonly closable: boolean;
            readonly lazy: boolean;
            readonly name?: import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown> | undefined;
        };
        paneName: string | number | undefined;
        active: boolean;
        index: string | undefined;
        isClosable: boolean;
    }[]) | (() => {
        uid: number;
        slots: Readonly<{
            [name: string]: import("vue").Slot | undefined;
        }>;
        props: {
            readonly disabled: boolean;
            readonly label: string;
            readonly closable: boolean;
            readonly lazy: boolean;
            readonly name?: import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown> | undefined;
        };
        paneName: string | number | undefined;
        active: boolean;
        index: string | undefined;
        isClosable: boolean;
    }[]))[], unknown, unknown, () => [], boolean>;
};
export declare type TabBarProps = ExtractPropTypes<typeof tabBarProps>;
export declare type TabBarInstance = InstanceType<typeof TabBar>;
