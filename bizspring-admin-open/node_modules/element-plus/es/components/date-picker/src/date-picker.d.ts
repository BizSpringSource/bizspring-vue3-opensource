declare const _default: import("vue").DefineComponent<{
    type: import("../../../utils").EpPropFinalized<(new (...args: any[]) => import("./date-picker.type").IDatePickerType & {}) | (() => import("./date-picker.type").IDatePickerType) | ((new (...args: any[]) => import("./date-picker.type").IDatePickerType & {}) | (() => import("./date-picker.type").IDatePickerType))[], unknown, unknown, "date", boolean>;
    disabledDate: {
        readonly type: import("vue").PropType<Function>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    cellClassName: {
        readonly type: import("vue").PropType<Function>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    shortcuts: import("../../../utils").EpPropFinalized<ArrayConstructor, unknown, unknown, () => never[], boolean>;
    arrowControl: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    label: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, undefined, boolean>;
    tabindex: import("../../../utils").EpPropFinalized<(new (...args: any[]) => (string | number) & {}) | (() => string | number) | ((new (...args: any[]) => (string | number) & {}) | (() => string | number))[], unknown, unknown, 0, boolean>;
    validateEvent: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, true, boolean>;
    unlinkPanels: BooleanConstructor;
    disabledHours: {
        readonly type: import("vue").PropType<import("../../time-picker/src/props/shared").GetDisabledHours>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    disabledMinutes: {
        readonly type: import("vue").PropType<import("../../time-picker/src/props/shared").GetDisabledMinutes>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    disabledSeconds: {
        readonly type: import("vue").PropType<import("../../time-picker/src/props/shared").GetDisabledSeconds>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    id: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<(new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>))[], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    name: import("../../../utils").EpPropFinalized<(new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>))[], unknown, unknown, "", boolean>;
    popperClass: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    format: StringConstructor;
    valueFormat: StringConstructor;
    clearable: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, true, boolean>;
    clearIcon: import("../../../utils").EpPropFinalized<(new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) | ((new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>))[], unknown, unknown, import("vue").DefineComponent<{}, {}, {}, import("vue").ComputedOptions, import("vue").MethodOptions, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, {}, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{}>>, {}>, boolean>;
    editable: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, true, boolean>;
    prefixIcon: import("../../../utils").EpPropFinalized<(new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) | ((new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>))[], unknown, unknown, "", boolean>;
    size: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<StringConstructor, "" | "default" | "small" | "large", never>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    disabled: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    placeholder: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    popperOptions: import("../../../utils").EpPropFinalized<(new (...args: any[]) => Partial<import("@popperjs/core").Options>) | (() => Partial<import("@popperjs/core").Options>) | ((new (...args: any[]) => Partial<import("@popperjs/core").Options>) | (() => Partial<import("@popperjs/core").Options>))[], unknown, unknown, () => {}, boolean>;
    modelValue: import("../../../utils").EpPropFinalized<(new (...args: any[]) => import("element-plus/es/components/time-picker").ModelValueType & {}) | (() => import("element-plus/es/components/time-picker").ModelValueType) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").ModelValueType & {}) | (() => import("element-plus/es/components/time-picker").ModelValueType))[], unknown, unknown, "", boolean>;
    rangeSeparator: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, "-", boolean>;
    startPlaceholder: StringConstructor;
    endPlaceholder: StringConstructor;
    defaultValue: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<(new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>) | ((new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>))[], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    defaultTime: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<(new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>) | ((new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>))[], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    isRange: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
}, () => JSX.Element, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, "update:modelValue"[], "update:modelValue", import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
    type: import("../../../utils").EpPropFinalized<(new (...args: any[]) => import("./date-picker.type").IDatePickerType & {}) | (() => import("./date-picker.type").IDatePickerType) | ((new (...args: any[]) => import("./date-picker.type").IDatePickerType & {}) | (() => import("./date-picker.type").IDatePickerType))[], unknown, unknown, "date", boolean>;
    disabledDate: {
        readonly type: import("vue").PropType<Function>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    cellClassName: {
        readonly type: import("vue").PropType<Function>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    shortcuts: import("../../../utils").EpPropFinalized<ArrayConstructor, unknown, unknown, () => never[], boolean>;
    arrowControl: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    label: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, undefined, boolean>;
    tabindex: import("../../../utils").EpPropFinalized<(new (...args: any[]) => (string | number) & {}) | (() => string | number) | ((new (...args: any[]) => (string | number) & {}) | (() => string | number))[], unknown, unknown, 0, boolean>;
    validateEvent: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, true, boolean>;
    unlinkPanels: BooleanConstructor;
    disabledHours: {
        readonly type: import("vue").PropType<import("../../time-picker/src/props/shared").GetDisabledHours>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    disabledMinutes: {
        readonly type: import("vue").PropType<import("../../time-picker/src/props/shared").GetDisabledMinutes>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    disabledSeconds: {
        readonly type: import("vue").PropType<import("../../time-picker/src/props/shared").GetDisabledSeconds>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    id: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<(new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>))[], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    name: import("../../../utils").EpPropFinalized<(new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>))[], unknown, unknown, "", boolean>;
    popperClass: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    format: StringConstructor;
    valueFormat: StringConstructor;
    clearable: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, true, boolean>;
    clearIcon: import("../../../utils").EpPropFinalized<(new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) | ((new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>))[], unknown, unknown, import("vue").DefineComponent<{}, {}, {}, import("vue").ComputedOptions, import("vue").MethodOptions, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, {}, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{}>>, {}>, boolean>;
    editable: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, true, boolean>;
    prefixIcon: import("../../../utils").EpPropFinalized<(new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) | ((new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>))[], unknown, unknown, "", boolean>;
    size: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<StringConstructor, "" | "default" | "small" | "large", never>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    readonly: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    disabled: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
    placeholder: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, "", boolean>;
    popperOptions: import("../../../utils").EpPropFinalized<(new (...args: any[]) => Partial<import("@popperjs/core").Options>) | (() => Partial<import("@popperjs/core").Options>) | ((new (...args: any[]) => Partial<import("@popperjs/core").Options>) | (() => Partial<import("@popperjs/core").Options>))[], unknown, unknown, () => {}, boolean>;
    modelValue: import("../../../utils").EpPropFinalized<(new (...args: any[]) => import("element-plus/es/components/time-picker").ModelValueType & {}) | (() => import("element-plus/es/components/time-picker").ModelValueType) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").ModelValueType & {}) | (() => import("element-plus/es/components/time-picker").ModelValueType))[], unknown, unknown, "", boolean>;
    rangeSeparator: import("../../../utils").EpPropFinalized<StringConstructor, unknown, unknown, "-", boolean>;
    startPlaceholder: StringConstructor;
    endPlaceholder: StringConstructor;
    defaultValue: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<(new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>) | ((new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>))[], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    defaultTime: {
        readonly type: import("vue").PropType<import("../../../utils").EpPropMergeType<(new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>) | ((new (...args: any[]) => Date | [Date, Date]) | (() => import("element-plus/es/components/time-picker").SingleOrRange<Date>))[], unknown, unknown>>;
        readonly required: false;
        readonly validator: ((val: unknown) => boolean) | undefined;
        __epPropKey: true;
    };
    isRange: import("../../../utils").EpPropFinalized<BooleanConstructor, unknown, unknown, false, boolean>;
}>> & {
    "onUpdate:modelValue"?: ((...args: any[]) => any) | undefined;
}, {
    type: import("../../../utils").EpPropMergeType<(new (...args: any[]) => import("./date-picker.type").IDatePickerType & {}) | (() => import("./date-picker.type").IDatePickerType) | ((new (...args: any[]) => import("./date-picker.type").IDatePickerType & {}) | (() => import("./date-picker.type").IDatePickerType))[], unknown, unknown>;
    disabled: import("../../../utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    name: import("../../../utils").EpPropMergeType<(new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").SingleOrRange<string> & {}) | (() => import("element-plus/es/components/time-picker").SingleOrRange<string>))[], unknown, unknown>;
    label: string;
    modelValue: import("../../../utils").EpPropMergeType<(new (...args: any[]) => import("element-plus/es/components/time-picker").ModelValueType & {}) | (() => import("element-plus/es/components/time-picker").ModelValueType) | ((new (...args: any[]) => import("element-plus/es/components/time-picker").ModelValueType & {}) | (() => import("element-plus/es/components/time-picker").ModelValueType))[], unknown, unknown>;
    validateEvent: import("../../../utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    editable: import("../../../utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    popperClass: string;
    popperOptions: Partial<import("@popperjs/core").Options>;
    placeholder: string;
    readonly: import("../../../utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    clearable: import("../../../utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    prefixIcon: import("../../../utils").EpPropMergeType<(new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) | ((new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>))[], unknown, unknown>;
    tabindex: import("../../../utils").EpPropMergeType<(new (...args: any[]) => (string | number) & {}) | (() => string | number) | ((new (...args: any[]) => (string | number) & {}) | (() => string | number))[], unknown, unknown>;
    clearIcon: import("../../../utils").EpPropMergeType<(new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) | ((new (...args: any[]) => (string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>) & {}) | (() => string | import("vue").Component<any, any, any, import("vue").ComputedOptions, import("vue").MethodOptions>))[], unknown, unknown>;
    rangeSeparator: string;
    isRange: import("../../../utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    shortcuts: unknown[];
    arrowControl: import("../../../utils").EpPropMergeType<BooleanConstructor, unknown, unknown>;
    unlinkPanels: boolean;
}>;
export default _default;
