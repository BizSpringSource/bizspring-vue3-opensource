import type { InjectionKey } from 'vue';
import type { ButtonProps } from 'element-plus/es/components/button';
export interface ButtonGroupContext {
    size?: ButtonProps['size'];
    type?: ButtonProps['type'];
}
export declare const buttonGroupContextKey: InjectionKey<ButtonGroupContext>;
