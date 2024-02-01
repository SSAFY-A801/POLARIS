import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  define:{
    'global' :{},
  },
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': {
        target: "http://www.aladin.co.kr/ttb",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },

      '/another-api': {
        target: 'http://i10a801.p.ssafy.io:8082',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/another-api/, ''), // 이 부분이 rewrite 설정
      },
    },
  }, 
})
