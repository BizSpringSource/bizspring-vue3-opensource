import type { RadioGroupProps } from 'element-plus/es/components';
import type { InjectionKey } from 'vue';
export interface RadioGroupContext extends RadioGroupProps {
    changeEvent: (val: RadioGroupProps['modelValue']) => void;
}
export declare const radioGroupKey: InjectionKey<RadioGroupContext>;
