import { remote } from '@/api/upms/dict'
export default (app) => {
  app.mixin({
    data() {
      return {
        dicts: {}
      }
    },
    created() {
      const list = this.$options.dicts
      if (list) {
        const result = []
        list.forEach(ele => {
          result.push(new Promise(resolve => {
            const obj = {}
            obj[ele] = []
            remote(ele).then(res => {
              obj[ele] = res.data.data
              resolve(obj)
            }).catch(() => {
              resolve(obj)
            })
          }))
        })
        Promise.all(result).then(res => {
          res.forEach(ele => {
            this.dict = Object.assign(this.dicts, ele)
          })
        })
      }
    }
  })
}
