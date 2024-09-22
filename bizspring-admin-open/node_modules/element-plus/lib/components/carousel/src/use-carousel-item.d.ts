import type { CarouselItemProps } from './carousel-item';
export declare const useCarouselItem: (props: CarouselItemProps, componentName: string) => {
    active: import("vue").Ref<boolean>;
    animating: import("vue").Ref<boolean>;
    hover: import("vue").Ref<boolean>;
    inStage: import("vue").Ref<boolean>;
    isVertical: import("vue").Ref<boolean>;
    translate: import("vue").Ref<number>;
    isCardType: import("vue").Ref<boolean>;
    scale: import("vue").Ref<number>;
    ready: import("vue").Ref<boolean>;
    handleItemClick: () => void;
};
