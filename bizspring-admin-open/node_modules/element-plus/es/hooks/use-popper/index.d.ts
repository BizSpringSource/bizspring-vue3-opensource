import type { Ref } from 'vue';
import type { Instance, Options, State, VirtualElement } from '@popperjs/core';
declare type ElementType = HTMLElement | undefined;
declare type ReferenceElement = ElementType | VirtualElement;
export declare type PartialOptions = Partial<Options>;
export declare const usePopper: (referenceElementRef: Ref<ReferenceElement>, popperElementRef: Ref<ElementType>, opts?: Ref<PartialOptions> | PartialOptions) => {
    state: import("vue").ComputedRef<State | undefined>;
    styles: import("vue").ComputedRef<{
        [key: string]: Partial<CSSStyleDeclaration>;
    }>;
    attributes: import("vue").ComputedRef<{
        [key: string]: {
            [key: string]: string | boolean;
        };
    }>;
    update: () => Promise<Partial<State>> | undefined;
    forceUpdate: () => void | undefined;
    instanceRef: import("vue").ComputedRef<Instance | undefined>;
};
export {};
