import type { TabsPaneContext } from 'element-plus/es/tokens';
import type { ExtractPropTypes } from 'vue';
import type { Awaitable } from 'element-plus/es/utils';
export declare type TabPaneName = string | number;
export declare const tabsProps: {
    readonly type: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "" | "card" | "border-card", unknown, "", boolean>;
    readonly activeName: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly closable: BooleanConstructor;
    readonly addable: BooleanConstructor;
    readonly modelValue: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly editable: BooleanConstructor;
    readonly tabPosition: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "left" | "right" | "top" | "bottom", unknown, "top", boolean>;
    readonly beforeLeave: import("element-plus/es/utils").EpPropFinalized<(new (...args: any[]) => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<void | boolean>) | (() => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<void | boolean>) | {
        (): (newName: TabPaneName, oldName: TabPaneName) => Awaitable<void | boolean>;
        new (): any;
        readonly prototype: any;
    } | ((new (...args: any[]) => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<void | boolean>) | (() => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<void | boolean>) | {
        (): (newName: TabPaneName, oldName: TabPaneName) => Awaitable<void | boolean>;
        new (): any;
        readonly prototype: any;
    })[], unknown, unknown, () => true, boolean>;
    readonly stretch: BooleanConstructor;
};
export declare type TabsProps = ExtractPropTypes<typeof tabsProps>;
export declare const tabsEmits: {
    "update:modelValue": (name: TabPaneName) => boolean;
    tabClick: (pane: TabsPaneContext, ev: Event) => boolean;
    tabChange: (name: TabPaneName) => boolean;
    edit: (paneName: TabPaneName | undefined, action: 'remove' | 'add') => boolean;
    tabRemove: (name: TabPaneName) => boolean;
    tabAdd: () => boolean;
};
export declare type TabsEmits = typeof tabsEmits;
export declare type TabsPanes = Record<number, TabsPaneContext>;
declare const _default: import("vue").DefineComponent<{
    readonly type: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "" | "card" | "border-card", unknown, "", boolean>;
    readonly activeName: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly closable: BooleanConstructor;
    readonly addable: BooleanConstructor;
    readonly modelValue: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly editable: BooleanConstructor;
    readonly tabPosition: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "left" | "right" | "top" | "bottom", unknown, "top", boolean>;
    readonly beforeLeave: import("element-plus/es/utils").EpPropFinalized<(new (...args: any[]) => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | (() => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | {
        (): (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>;
        new (): any;
        readonly prototype: any;
    } | ((new (...args: any[]) => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | (() => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | {
        (): (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>;
        new (): any;
        readonly prototype: any;
    })[], unknown, unknown, () => true, boolean>;
    readonly stretch: BooleanConstructor;
}, () => JSX.Element, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, {
    "update:modelValue": (name: TabPaneName) => boolean;
    tabClick: (pane: {
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
    }, ev: Event) => boolean;
    tabChange: (name: TabPaneName) => boolean;
    edit: (paneName: TabPaneName | undefined, action: "add" | "remove") => boolean;
    tabRemove: (name: TabPaneName) => boolean;
    tabAdd: () => boolean;
}, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<ExtractPropTypes<{
    readonly type: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "" | "card" | "border-card", unknown, "", boolean>;
    readonly activeName: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly closable: BooleanConstructor;
    readonly addable: BooleanConstructor;
    readonly modelValue: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<readonly [StringConstructor, NumberConstructor], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly editable: BooleanConstructor;
    readonly tabPosition: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "left" | "right" | "top" | "bottom", unknown, "top", boolean>;
    readonly beforeLeave: import("element-plus/es/utils").EpPropFinalized<(new (...args: any[]) => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | (() => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | {
        (): (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>;
        new (): any;
        readonly prototype: any;
    } | ((new (...args: any[]) => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | (() => (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>) | {
        (): (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>;
        new (): any;
        readonly prototype: any;
    })[], unknown, unknown, () => true, boolean>;
    readonly stretch: BooleanConstructor;
}>> & {
    "onUpdate:modelValue"?: ((name: TabPaneName) => any) | undefined;
    onTabClick?: ((pane: {
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
    }, ev: Event) => any) | undefined;
    onTabChange?: ((name: TabPaneName) => any) | undefined;
    onEdit?: ((paneName: TabPaneName | undefined, action: "add" | "remove") => any) | undefined;
    onTabRemove?: ((name: TabPaneName) => any) | undefined;
    onTabAdd?: (() => any) | undefined;
}, {
    readonly type: import("element-plus/es/utils").EpPropMergeType<StringConstructor, "" | "card" | "border-card", unknown>;
    readonly closable: boolean;
    readonly tabPosition: import("element-plus/es/utils").EpPropMergeType<StringConstructor, "left" | "right" | "top" | "bottom", unknown>;
    readonly beforeLeave: (newName: TabPaneName, oldName: TabPaneName) => Awaitable<boolean | void>;
    readonly addable: boolean;
    readonly editable: boolean;
    readonly stretch: boolean;
}>;
export default _default;
