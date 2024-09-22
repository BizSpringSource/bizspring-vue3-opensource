import type { CSSProperties, ComputedRef, InjectionKey, Ref } from 'vue';
import type { useNamespace } from 'element-plus/es/hooks';
export declare type DialogContext = {
    dialogRef: Ref<HTMLElement | undefined>;
    headerRef: Ref<HTMLElement | undefined>;
    bodyId: Ref<string>;
    ns: ReturnType<typeof useNamespace>;
    rendered: Ref<boolean>;
    style: ComputedRef<CSSProperties>;
};
export declare const dialogInjectionKey: InjectionKey<DialogContext>;
