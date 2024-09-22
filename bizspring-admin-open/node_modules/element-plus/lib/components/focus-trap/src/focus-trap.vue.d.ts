import type { PropType } from 'vue';
declare const _default: import("vue").DefineComponent<{
    loop: BooleanConstructor;
    trapped: BooleanConstructor;
    focusTrapEl: PropType<HTMLElement>;
    focusStartEl: {
        type: PropType<HTMLElement | "first" | "container">;
        default: string;
    };
}, {
    onKeydown: (e: KeyboardEvent) => void;
}, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, ("focusAfterTrapped" | "focusAfterReleased" | "focusin" | "focusout" | "focusout-prevented" | "release-requested")[], "focusAfterTrapped" | "focusAfterReleased" | "focusin" | "focusout" | "focusout-prevented" | "release-requested", import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
    loop: BooleanConstructor;
    trapped: BooleanConstructor;
    focusTrapEl: PropType<HTMLElement>;
    focusStartEl: {
        type: PropType<HTMLElement | "first" | "container">;
        default: string;
    };
}>> & {
    onFocusAfterTrapped?: ((...args: any[]) => any) | undefined;
    onFocusAfterReleased?: ((...args: any[]) => any) | undefined;
    onFocusin?: ((...args: any[]) => any) | undefined;
    onFocusout?: ((...args: any[]) => any) | undefined;
    "onFocusout-prevented"?: ((...args: any[]) => any) | undefined;
    "onRelease-requested"?: ((...args: any[]) => any) | undefined;
}, {
    loop: boolean;
    trapped: boolean;
    focusStartEl: HTMLElement | "first" | "container";
}>;
export default _default;
