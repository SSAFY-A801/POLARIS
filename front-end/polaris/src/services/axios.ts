import { ref } from 'vue'
import axios, { AxiosError, type AxiosResponse } from 'axios'
import { profileCounterStore } from '@/stores/profilecounter'
import { useUserStore } from '@/stores/authcounter'
import { essayStore } from '@/stores/essaycounter'
import { useChatStore } from '@/stores/chatcounter'

const userInfoString = ref<string>(localStorage.getItem('user_info') ?? "");
// 사용자 정보를 나타내는 인터페이스 정의
interface UserInfo {
  email?: string | null;
  // 다른 속성들도 필요에 따라 추가할 수 있습니다.
}

// userInfoString에서 가져온 값을 파싱하여 UserInfo 타입으로 사용
let loginUser: UserInfo;

try {
  loginUser = JSON.parse(userInfoString.value) || {}; // 빈 객체로 기본값 설정
} catch (error) {
  loginUser = {}; // JSON 파싱에 실패한 경우 빈 객체로 기본값 설정
}

// axios 인스턴스 생성
const baseURL = `${import.meta.env.VITE_API_KEY}`;
const token = ref(localStorage.getItem('user_token'))

const instance = axios.create({
  baseURL,
  timeout: 50000,
  headers: { 'Authorization': token.value?.replace("\"", "") },
});

// 응답 인터셉터 추가
instance.interceptors.response.use(
  (response: AxiosResponse) => response,
  async (error: AxiosError) => {
    if (error.config && error.response && error.response.status === 401 && (<any>error.response.data).status === 420) {
      // 액세스 토큰이 만료된 경우
      const refreshToken = localStorage.getItem('refresh_token')
      // console.log(refreshToken)
      // console.log(loginUser.email)
      
      // 리프레시 토큰으로 새 액세스 토큰 요청
      const response = await axios.post(`${baseURL}/user/reissue`, {"email": loginUser.email }, {
        headers: { 'Authorization': refreshToken,
        'Content-Type' : 'application/json'
    }});

      // 새로 발급받은 액세스 토큰 저장
      // console.log(response.data.data)
      localStorage.setItem('user_token', response.data.data.access);
      localStorage.setItem('refresh_token', response.data.data.refresh);
      // localStorage.setItem('user_info', response.data.data);
      token.value = localStorage.getItem('user_token')
      const profilestore = profileCounterStore()
      const userstore = useUserStore()
      const essaystore = essayStore()
      const chatstore = useChatStore()
      profilestore.token = token.value?token.value : ''
      essaystore.token = token.value
      chatstore.token = token.value
      error.config.headers['Authorization'] = token.value?.replace("\"", "")

      // 실패한 요청 재실행
      return instance.request(error.config)
    }

    return Promise.reject(error)
  }
);

const axiosInstance = ref(instance)
export default axiosInstance


