/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
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
