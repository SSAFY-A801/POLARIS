<template>
  <Header></Header>
  <div>
    <div class="container md:container md:mx-auto max-w-6xl">
      <div class="grid grid-cols-12">
        <!-- 정렬을 원하는 내용을 12개의 컬럼에 배치 -->
        <div class="col-span-12">
          <div class="flex justify-end">
            <button>
              <RouterLink to="/">[ 홈 ]</RouterLink>
            </button>
            <button>
              <RouterLink :to="{name: 'ProfilePage'}">[프로필]</RouterLink>
            </button>
            <button>
              <RouterLink :to="{name: 'BookDetailPage'}">[사용자도서 상세조회]</RouterLink>
            </button>
          </div>
        </div>
        <div class="col-span-12">
          <RouterView/>
        </div>
      </div>
    </div>
  </div>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Header from '@/components/common/Header.vue'
import Footer from '@/components/common/Footer.vue'

</script>

<style scoped>

</style>