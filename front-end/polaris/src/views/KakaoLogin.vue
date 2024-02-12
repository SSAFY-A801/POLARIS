<template>

</template>

<script setup lang="ts">
import axios from 'axios'

let code = new URL(window.location.href).searchParams.get('code');

// 백으로 인가코드 전송
const fetchToken = async () => {
  try {
    console.log(code)
    const response = await axios.post(`https://i10a801.p.ssafy.io:8082/user/login/oauth2/code/kakao?code=${code}`)
  } catch (error) {
    console.error(error);
  }
};

fetchToken();
</script>

<style scoped>
#kakaologincontainer {
  width: 1200px;
  height: 600px;
  margin: 250px auto 50px;
  display: flex;
  flex-grow:1;
}
</style>