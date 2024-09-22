import type { Ref } from 'vue';
/**
 * This method provides dialogable components the ability to restore previously activated element before
 * the dialog gets opened
 */
export declare const useRestoreActive: (toggle: Ref<boolean>, initialFocus?: Ref<HTMLElement> | undefined) => void;
