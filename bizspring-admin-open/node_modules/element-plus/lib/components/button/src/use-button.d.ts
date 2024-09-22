import type { SetupContext } from 'vue';
import type { ButtonEmits, ButtonProps } from './button';
export declare const useButton: (props: ButtonProps, emit: SetupContext<ButtonEmits>['emit']) => {
    _disabled: import("vue").ComputedRef<boolean>;
    _size: import("vue").ComputedRef<"" | "default" | "small" | "large">;
    _type: import("vue").ComputedRef<"" | "default" | "primary" | "success" | "warning" | "info" | "danger" | "text">;
    _ref: import("vue").Ref<HTMLButtonElement | undefined>;
    shouldAddSpace: import("vue").ComputedRef<boolean>;
    handleClick: (evt: MouseEvent) => void;
};
