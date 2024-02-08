<template>
  <div 
  class="bg-gray-100 inline-block rounded-lg px-4 py-1 shadow-sm shadow-stone-400 hover:bg-gray-200">
    <button 
      @click="showEssaydetail"
      class="container p-2">
      <div class="flex justify-center py-2 rounded-lg">
        <div>
          <img
              alt="Home"
              src="@\assets\book-image.jpg"
              class="rounded-md object-cover"
          >
        </div>
      </div>
      <div class="mt-2">
          <dl>
            <div class="border-b text-start mb-2">
                <dt class="sr-only">Title</dt>
                <dd   class="font-bold text-maintheme1">글 제목</dd>
                <dd   class="font-bold text-maintheme1">작성자</dd>
            </div>
            <div class="text-start">
                <dt class="sr-only">Author</dt>
                <dd class="font-normal text-maintheme1">도서정보</dd>
                <dd class="font-normal text-maintheme1">책제목 / 저자 / 출판사</dd>
            </div>
          </dl>
      </div>
      <!-- 도서상태 -->
      <div class="mt-4 grid grid-cols-12 gap-2 text-xs items-center">
        <div class="col-span-4 text-start">
          <p class="mb-1">작성일</p>
          <p>2020-03-20</p>
        </div>
        <div class="flex col-span-4 justify-around">
          <p>스크랩</p>
          <p>10</p>
        </div>
        <div class="flex col-span-4 justify-around">
          <p>조회수</p>
          <p>20</p>
        </div>
      </div>
    </button>
  </div>
</template>

<script setup lang="ts">
  import { useRouter } from "vue-router"

  const router = useRouter();


  const showEssaydetail = () => {
    router.push({name: 'essaydetail'})
  }
</script>

<style scoped>
  button {
    height: auto;
  }

  img {
    height: 240px;
  }
</style>