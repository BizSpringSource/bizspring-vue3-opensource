import store from './store'
export default {
  install: (app) => {
    app.config.errorHandler = (err, vm, info) => {
      store.commit('ADD_LOGS', {
        type: 'error',
        message: err.message,
        stack: err.stack,
        info
      })
      if (process.env.NODE_ENV === 'development') {
        console.group('errrrrrrrrrrrrrrrrrr BizSpring errrrrrrrrrrrrrrrrrr');
        console.log(info);
        console.groupEnd();
        console.group('bizzzzzzzzzzzzzzzzzzz Vue3 实例 bizzzzzzzzzzzzzzzzzzz');
        console.log(vm);
        console.groupEnd();
        console.group('errrrrrrrrrrrrrrrrrr Error errrrrrrrrrrrrrrrrrr');
        console.log(err);
        console.groupEnd();
      }
    }
  }
}
