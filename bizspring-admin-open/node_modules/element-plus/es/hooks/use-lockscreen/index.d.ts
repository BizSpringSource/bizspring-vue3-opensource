import type { Ref } from 'vue';
/**
 * Hook that monitoring the ref value to lock or unlock the screen.
 * When the trigger became true, it assumes modal is now opened and vice versa.
 * @param trigger {Ref<boolean>}
 */
export declare const useLockscreen: (trigger: Ref<boolean>) => void;
