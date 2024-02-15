import typography from '@tailwindcss/typography'

/** @type {import('tailwindcss').Config} */
export default {

  content: [
    './src/**/*.{html,js,ts,vue}',
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {
      colors: {
        maintheme1: '#323f59',
        maintheme2: '#30446a',
        maintheme3: '#f9e385',
        maintheme4: '#b14742',
        maintheme5: '#8c2c16',
        deepgray: '#828282',
        lightgray: '#F6F6F6',
        kakao_yellow: '#FEE500',
        kakao_brown: '#3A1D1D',
        naver_green: '#03C75A',
        backgroundgray: '#F9F9F9',

      }
    }
  },
    plugins: [typography],
  }
