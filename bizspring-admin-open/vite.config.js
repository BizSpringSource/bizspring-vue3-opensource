import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from "@vitejs/plugin-vue-jsx"
import { globalBaseUrl }  from './public/config'
import path from 'path'

const {resolve} = require('path')
const  url = globalBaseUrl
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
  ],
  server: {
    port: 8088,
    cors: true,
    open: false,
    proxy: {
      '/api': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
      '/auth': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/auth/, '/auth')
      },
      '/upms': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/upms/, '/upms')
      },
      '/doc': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/doc/, '/doc')
      },
      '/webjars': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/webjars/, '/webjars')
      },
      '/swagger-resources': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/swagger-resources/, '/swagger-resources')
      },
      '/store': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/store/, '/store')
      },
      '/goods': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/goods/, '/goods')
      },
      '/order': {
        target: url,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/order/, '/order')
      },
    }
  },
  define: {
    __VUE_I18N_FULL_INSTALL__: true,
    __VUE_I18N_LEGACY_API__: false,
    __INTLIFY_PROD_DEVTOOLS__: false,
  },
  resolve: {
    alias: {
      "@": resolve(__dirname, "./src"),
      "components": resolve(__dirname, "./src/components"),
      "styles": resolve(__dirname, "./src/styles"),
      "utils": resolve(__dirname, "./src/utils"),
    }
  }
})
