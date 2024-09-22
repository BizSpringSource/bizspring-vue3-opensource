import type { ExtractPropTypes } from 'vue';
import type { IDatePickerType } from '../date-picker.type';
export declare const datePickerProps: {
    readonly type: import("element-plus/es/utils").EpPropFinalized<(new (...args: any[]) => IDatePickerType & {}) | (() => IDatePickerType) | ((new (...args: any[]) => IDatePickerType & {}) | (() => IDatePickerType))[], unknown, unknown, "date", boolean>;
};
export declare type DatePickerProps = ExtractPropTypes<typeof datePickerProps>;
