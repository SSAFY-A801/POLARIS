
import { ref } from 'vue'
import axios, { AxiosError, type AxiosResponse } from 'axios'

// axios 인스턴스 생성
const baseURL = 'https://i10a801.p.ssafy.io:8082';
const token = ref(localStorage.getItem('user_token'))
const instance = axios.create({
  baseURL,
  timeout: 5000,
  headers: { 'Authorization': token.value?.replace("\"", "") },
});

// 응답 인터셉터 추가
instance.interceptors.response.use(
  (response: AxiosResponse) => response,
  async (error: AxiosError) => {
    if (error.config && error.response && error.response.status === 401) {
      // 액세스 토큰이 만료된 경우
      const refreshToken = localStorage.getItem('refresh_token');
      // 리프레시 토큰으로 새 액세스 토큰 요청
      const response = await axios.post(`${baseURL}/user/reissue`, {}, {
        headers: { 'Authorization': refreshToken,
        'Content-Type' : 'application/json'
    }});

      // 새로 발급받은 액세스 토큰 저장
      localStorage.setItem('user_token', response.data.data.access);
      token.value = localStorage.getItem('user_token')
      error.config.headers['Authorization'] = token.value?.replace("\"", "")

      // 실패한 요청 재실행
      return instance.request(error.config);
    }

    return Promise.reject(error);
  }
);

const axiosInstance = ref(instance);
export default axiosInstance;

