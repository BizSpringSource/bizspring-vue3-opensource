import type { FormItemContext } from 'element-plus/es/tokens';
import type { ComputedRef, Ref } from 'vue';
export declare const useFormItem: () => {
    form: import("element-plus/es/tokens").FormContext | undefined;
    formItem: FormItemContext | undefined;
};
export declare type IUseFormItemInputCommonProps = {
    id?: string;
    label?: string | number | boolean | Record<string, any>;
};
export declare const useFormItemInputId: (props: Partial<IUseFormItemInputCommonProps>, { formItemContext, disableIdGeneration, disableIdManagement, }: {
    formItemContext?: FormItemContext | undefined;
    disableIdGeneration?: Ref<boolean> | ComputedRef<boolean> | undefined;
    disableIdManagement?: Ref<boolean> | ComputedRef<boolean> | undefined;
}) => {
    isLabeledByFormItem: ComputedRef<boolean>;
    inputId: Ref<string | undefined>;
};
