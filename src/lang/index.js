import { createI18n } from 'vue-i18n'
import Store from '@/store'
import elementEnLocale from 'element-plus/es/locale/lang/en'
import elementZhLocale from 'element-plus/es/locale/lang/zh-cn'
import enLocale from './en'
import zhLocale from './zh'
export const messages = {
  en: {
    ...enLocale,
    ...elementEnLocale
  },
  'zh-cn': {
    ...zhLocale,
    ...elementZhLocale
  }
}
export default createI18n({
  locale: Store.getters.language,
  messages
})
