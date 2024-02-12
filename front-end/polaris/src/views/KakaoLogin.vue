<template>

<p>카카오 로그인 중</p>

</template>

<script setup lang="ts">

import { onMounted } from 'vue'
import axios from 'axios'

const code = new URL(window.location.href).searchParams.get('code');

const fetchToken = async () => {
  try {
    console.log(code)
    await axios.post(`https://i10a801.p.ssafy.io:8082/user/login/oauth2/code/kakao?code=${code}`, {})
    .then(function(response) {
      if (response.data.status === 200) {
        console.log(response)
      } else {
        console.log(response)
      }
    })
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  fetchToken();
})




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