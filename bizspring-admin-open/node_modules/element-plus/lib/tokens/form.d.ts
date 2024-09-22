import type { InjectionKey, SetupContext, UnwrapRef } from 'vue';
import type { RuleItem, ValidateError, ValidateFieldsError } from 'async-validator';
import type { ComponentSize } from 'element-plus/es/constants';
import type { FormEmits, FormItemProp, FormItemProps, FormItemValidateState, FormLabelWidthContext, FormProps } from 'element-plus/es/components/form';
import type { Arrayable } from 'element-plus/es/utils';
export interface FormItemRule extends RuleItem {
    trigger?: Arrayable<string>;
}
export declare type FormRules = Partial<Record<string, Arrayable<FormItemRule>>>;
export declare type FormValidationResult = Promise<boolean>;
export declare type FormValidateCallback = (isValid: boolean, invalidFields?: ValidateFieldsError) => void;
export interface FormValidateFailure {
    errors: ValidateError[] | null;
    fields: ValidateFieldsError;
}
export declare type FormContext = FormProps & UnwrapRef<FormLabelWidthContext> & {
    emit: SetupContext<FormEmits>['emit'];
    addField: (field: FormItemContext) => void;
    removeField: (field: FormItemContext) => void;
    resetFields: (props?: Arrayable<FormItemProp>) => void;
    clearValidate: (props?: Arrayable<FormItemProp>) => void;
    validateField: (props?: Arrayable<FormItemProp>, callback?: FormValidateCallback) => FormValidationResult;
};
export interface FormItemContext extends FormItemProps {
    $el: HTMLDivElement | undefined;
    size: ComponentSize;
    validateState: FormItemValidateState;
    isGroup: boolean;
    labelId: string;
    inputIds: string[];
    hasLabel: boolean;
    addInputId: (id: string) => void;
    removeInputId: (id: string) => void;
    validate: (trigger: string, callback?: FormValidateCallback) => FormValidationResult;
    resetField(): void;
    clearValidate(): void;
}
export declare const formContextKey: InjectionKey<FormContext>;
export declare const formItemContextKey: InjectionKey<FormItemContext>;
