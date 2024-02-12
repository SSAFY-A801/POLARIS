
import { ref } from 'vue'
import axios, { AxiosError, type AxiosResponse } from 'axios'

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
  console.error("Error parsing user_info:", error);
  loginUser = {}; // JSON 파싱에 실패한 경우 빈 객체로 기본값 설정
}

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
    console.log(token.value)
    console.log(error)
    console.log(error.status)
    console.log(error.response)
    console.log(error.response?.data)
    console.log(error.response?.status)
    if (error.config && error.response && error.response.status === 401 && (<any>error.response.data).status === 420) {
      // 액세스 토큰이 만료된 경우
      const refreshToken = localStorage.getItem('refresh_token');
      // 리프레시 토큰으로 새 액세스 토큰 요청
      const response = await axios.post(`${baseURL}/user/reissue`, {"email": loginUser.email }, {
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

