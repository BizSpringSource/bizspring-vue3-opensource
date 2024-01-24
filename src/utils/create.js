import * as Vue from 'vue'

/**
 * @param Component 组件实例的选项对象
 * @param props 组件实例中的prop
 */

export function create(Component, props) {
  const comp = new Component({ propsData: props }).$mount()
  document.body.appendChild(comp.$el)
  comp.remove = () => {
    document.body.removeChild(comp.$el)
    comp.$destroy()
  }
  return comp
}

