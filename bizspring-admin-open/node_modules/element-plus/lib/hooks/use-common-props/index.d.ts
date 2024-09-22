import type { ComponentSize } from 'element-plus/es/constants';
import type { MaybeRef } from '@vueuse/core';
export declare const useSizeProp: {
    readonly type: import("vue").PropType<import("element-plus/es/utils").EpPropMergeType<StringConstructor, "" | "default" | "small" | "large", never>>;
    readonly required: false;
    readonly validator: ((val: unknown) => boolean) | undefined;
    __epPropKey: true;
};
export declare const useSize: (fallback?: MaybeRef<ComponentSize | undefined>, ignore?: Partial<Record<'prop' | 'form' | 'formItem' | 'global', boolean>>) => import("vue").ComputedRef<"" | "default" | "small" | "large">;
export declare const useDisabled: (fallback?: MaybeRef<boolean | undefined>) => import("vue").ComputedRef<boolean>;
