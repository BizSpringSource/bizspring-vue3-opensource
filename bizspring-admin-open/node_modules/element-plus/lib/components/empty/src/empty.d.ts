import type { ExtractPropTypes } from 'vue';
import type Empty from './empty.vue';
export declare const emptyProps: {
    readonly image: {
        readonly type: StringConstructor;
        readonly default: "";
    };
    readonly imageSize: NumberConstructor;
    readonly description: {
        readonly type: StringConstructor;
        readonly default: "";
    };
};
export declare type EmptyProps = ExtractPropTypes<typeof emptyProps>;
export declare type EmptyInstance = InstanceType<typeof Empty>;
