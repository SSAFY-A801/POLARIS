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
      '/p-api': {
        target: "http://www.aladin.co.kr/ttb",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },

    },
  }, 
})
