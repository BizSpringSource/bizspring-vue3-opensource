export declare const ElDescriptions: import("element-plus/es/utils").SFCWithInstall<import("vue").DefineComponent<{
    readonly border: import("element-plus/es/utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    readonly column: import("element-plus/es/utils").EpPropFinalized<NumberConstructor, unknown, unknown, 3, boolean>;
    readonly direction: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "vertical" | "horizontal", unknown, "horizontal", boolean>;
    readonly size: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<StringConstructor, "" | "default" | "small" | "large", never>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly title: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    readonly extra: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
}, {
    props: Readonly<import("@vue/shared").LooseRequired<Readonly<import("vue").ExtractPropTypes<{
        readonly border: import("element-plus/es/utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
        readonly column: import("element-plus/es/utils").EpPropFinalized<NumberConstructor, unknown, unknown, 3, boolean>;
        readonly direction: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "vertical" | "horizontal", unknown, "horizontal", boolean>;
        readonly size: {
            readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<StringConstructor, "" | "default" | "small" | "large", never>>;
            readonly required: false;
            readonly validator: ((val: unknown) => boolean) | undefined;
            __epPropKey: true;
        };
        readonly title: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
        readonly extra: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    }>> & {
        [x: string & `on${string}`]: ((...args: any[]) => any) | ((...args: unknown[]) => any) | undefined;
    }>>;
    ns: {
        namespace: import("vue").Ref<string>;
        b: (blockSuffix?: string) => string;
        e: (element?: string | undefined) => string;
        m: (modifier?: string | undefined) => string;
        be: (blockSuffix?: string | undefined, element?: string | undefined) => string;
        em: (element?: string | undefined, modifier?: string | undefined) => string;
        bm: (blockSuffix?: string | undefined, modifier?: string | undefined) => string;
        bem: (blockSuffix?: string | undefined, element?: string | undefined, modifier?: string | undefined) => string;
        is: {
            (name: string, state: boolean | undefined): string;
            (name: string): string;
        };
        cssVar: (object: Record<string, string>) => Record<string, string>;
        cssVarName: (name: string) => string;
        cssVarBlock: (object: Record<string, string>) => Record<string, string>;
        cssVarBlockName: (name: string) => string;
    };
    descriptionsSize: import("vue").ComputedRef<"" | "default" | "small" | "large">;
    slots: Readonly<{
        [name: string]: import("vue").Slot | undefined;
    }>;
    descriptionKls: import("vue").ComputedRef<string[]>;
    filledNode: (node: any, span: any, count: any, isLast?: boolean) => any;
    getRows: () => never[];
    ElDescriptionsRow: import("vue").DefineComponent<{
        readonly row: import("element-plus/es/utils").EpPropFinalized<ArrayConstructor, unknown, unknown, () => never[], boolean>;
    }, {
        descriptions: import("./src/descriptions.type").IDescriptionsInject;
        ElDescriptionsCell: import("vue").DefineComponent<{
            cell: {
                type: ObjectConstructor;
            };
            tag: {
                type: StringConstructor;
            };
            type: {
                type: StringConstructor;
            };
        }, {
            descriptions: import("./src/descriptions.type").IDescriptionsInject;
        }, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
            cell: {
                type: ObjectConstructor;
            };
            tag: {
                type: StringConstructor;
            };
            type: {
                type: StringConstructor;
            };
        }>>, {}>;
    }, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
        readonly row: import("element-plus/es/utils").EpPropFinalized<ArrayConstructor, unknown, unknown, () => never[], boolean>;
    }>>, {
        readonly row: unknown[];
    }>;
}, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
    readonly border: import("element-plus/es/utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    readonly column: import("element-plus/es/utils").EpPropFinalized<NumberConstructor, unknown, unknown, 3, boolean>;
    readonly direction: import("element-plus/es/utils").EpPropFinalized<StringConstructor, "vertical" | "horizontal", unknown, "horizontal", boolean>;
    readonly size: {
        readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<StringConstructor, "" | "default" | "small" | "large", never>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly title: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    readonly extra: import("element-plus/es/utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
}>>, {
    readonly title: string;
    readonly column: number;
    readonly border: import("element-plus/es/utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    readonly direction: import("element-plus/es/utils").EpPropMergeType<StringConstructor, "vertical" | "horizontal", unknown>;
    readonly extra: string;
}>> & {
    DescriptionsItem: import("vue").DefineComponent<{
        label: {
            type: StringConstructor;
            default: string;
        };
        span: {
            type: NumberConstructor;
            default: number;
        };
        width: {
            type: (StringConstructor | NumberConstructor)[];
            default: string;
        };
        minWidth: {
            type: (StringConstructor | NumberConstructor)[];
            default: string;
        };
        align: {
            type: StringConstructor;
            default: string;
        };
        labelAlign: {
            type: StringConstructor;
            default: string;
        };
        className: {
            type: StringConstructor;
            default: string;
        };
        labelClassName: {
            type: StringConstructor;
            default: string;
        };
    }, unknown, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
        label: {
            type: StringConstructor;
            default: string;
        };
        span: {
            type: NumberConstructor;
            default: number;
        };
        width: {
            type: (StringConstructor | NumberConstructor)[];
            default: string;
        };
        minWidth: {
            type: (StringConstructor | NumberConstructor)[];
            default: string;
        };
        align: {
            type: StringConstructor;
            default: string;
        };
        labelAlign: {
            type: StringConstructor;
            default: string;
        };
        className: {
            type: StringConstructor;
            default: string;
        };
        labelClassName: {
            type: StringConstructor;
            default: string;
        };
    }>>, {
        label: string;
        width: string | number;
        className: string;
        minWidth: string | number;
        span: number;
        align: string;
        labelAlign: string;
        labelClassName: string;
    }>;
};
export declare const ElDescriptionsItem: import("element-plus/es/utils").SFCWithInstall<import("vue").DefineComponent<{
    label: {
        type: StringConstructor;
        default: string;
    };
    span: {
        type: NumberConstructor;
        default: number;
    };
    width: {
        type: (StringConstructor | NumberConstructor)[];
        default: string;
    };
    minWidth: {
        type: (StringConstructor | NumberConstructor)[];
        default: string;
    };
    align: {
        type: StringConstructor;
        default: string;
    };
    labelAlign: {
        type: StringConstructor;
        default: string;
    };
    className: {
        type: StringConstructor;
        default: string;
    };
    labelClassName: {
        type: StringConstructor;
        default: string;
    };
}, unknown, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
    label: {
        type: StringConstructor;
        default: string;
    };
    span: {
        type: NumberConstructor;
        default: number;
    };
    width: {
        type: (StringConstructor | NumberConstructor)[];
        default: string;
    };
    minWidth: {
        type: (StringConstructor | NumberConstructor)[];
        default: string;
    };
    align: {
        type: StringConstructor;
        default: string;
    };
    labelAlign: {
        type: StringConstructor;
        default: string;
    };
    className: {
        type: StringConstructor;
        default: string;
    };
    labelClassName: {
        type: StringConstructor;
        default: string;
    };
}>>, {
    label: string;
    width: string | number;
    className: string;
    minWidth: string | number;
    span: number;
    align: string;
    labelAlign: string;
    labelClassName: string;
}>>;
export default ElDescriptions;
export * from './src/description';
