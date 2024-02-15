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
    // port: 5173,
    // https: true,
    // hmr: {
    //     host: "i10a801.p.ssafy.io",
    //     port: 8082,
    //     protocol: "wss"},
    proxy: {
      '/p-api': {
        target: "http://www.aladin.co.kr/ttb",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
      '/api': {
        target: 'https://i10a801.p.ssafy.io:8082',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
        ws: true,
        secure: false
      },
      

    },
  }, 
})
