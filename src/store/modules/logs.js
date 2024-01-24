/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import { getStore, setStore } from '@/util/store'
import dayjs from 'dayjs'

const logs = {
  state: {
    logsList: getStore({ name: 'logsList' }) || []
  },
  actions: {
    // 发送错误日志
    SendLogs({ state, commit }) {
      return new Promise((resolve, reject) => {
        resolve()
      })
    }
  },
  mutations: {
    ADD_LOGS: (state, { type, message, stack, info }) => {
      state.logsList.push(Object.assign({
        id: state.logsList.length,
        url: window.location.href,
        time: dayjs().format('YYYY-MM-DD HH:mm:ss')
      }, {
        type,
        message,
        stack,
        info: info.toString()
      }))
      setStore({ name: 'logsList', content: state.logsList })
    },
    CLEAR_LOGS: (state) => {
      state.logsList = []
      setStore({ name: 'logsList', content: state.logsList })
    }
  }

}

export default logs
