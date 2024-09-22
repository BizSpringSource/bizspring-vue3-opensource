const cubic = (value) => {
  return value ** 3;
};
const easeInOutCubic = (value) => value < 0.5 ? cubic(value * 2) / 2 : 1 - cubic((1 - value) * 2) / 2;

export { cubic, easeInOutCubic };
//# sourceMappingURL=animation.mjs.map
