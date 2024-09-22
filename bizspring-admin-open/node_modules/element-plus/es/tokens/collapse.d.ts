import type { CollapseActiveName } from 'element-plus/es/components/collapse';
import type { InjectionKey, Ref } from 'vue';
export interface CollapseContext {
    activeNames: Ref<CollapseActiveName[]>;
    handleItemClick: (name: CollapseActiveName) => void;
}
export declare const collapseContextKey: InjectionKey<CollapseContext>;
