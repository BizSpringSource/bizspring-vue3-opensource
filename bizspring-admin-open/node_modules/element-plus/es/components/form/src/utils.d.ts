import type { Arrayable } from 'element-plus/es/utils';
import type { FormItemContext } from 'element-plus/es/tokens';
import type { FormItemProp } from './form-item';
export declare function useFormLabelWidth(): {
    autoLabelWidth: import("vue").ComputedRef<string>;
    registerLabelWidth: (val: number, oldVal: number) => void;
    deregisterLabelWidth: (val: number) => void;
};
export declare const filterFields: (fields: FormItemContext[], props: Arrayable<FormItemProp>) => FormItemContext[];
