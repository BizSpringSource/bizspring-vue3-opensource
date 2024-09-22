import type { IDescriptionsInject } from './descriptions.type';
declare const _default: import("vue").DefineComponent<{
    readonly row: import("../../../utils").EpPropFinalized<ArrayConstructor, unknown, unknown, () => never[], boolean>;
}, {
    descriptions: IDescriptionsInject;
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
        descriptions: IDescriptionsInject;
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
    readonly row: import("../../../utils").EpPropFinalized<ArrayConstructor, unknown, unknown, () => never[], boolean>;
}>>, {
    readonly row: unknown[];
}>;
export default _default;
