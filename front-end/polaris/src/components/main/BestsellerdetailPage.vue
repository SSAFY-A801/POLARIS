<template>
  <Navvar></Navvar>

    <div class="container mt-60 mx-auto mt-32 max-w-6xl min-w-[700px] bg-backgroundgray p-4 font-[gowun-dodum]">
      <h1 class="text-2xl font-bold">도서 상세보기</h1>
        <div class="container grid grid-cols-12 gap-8">
          <!-- 도서 상세 좌측 -->
          <div class="col-span-4 grid items-center">
            <div>
              <img :src="bookDetail.cover" class="p-2 shadow-md shadow-gray-500  border-gray-500 w-50">
            </div>                  
          </div>
          <!-- 도서 상세 우측 -->
          <div class="col-span-8 m-2">
            <div id="name" class="container grid grid-cols-6 flex">
              <div class="text-maintheme1 m-2 font-bold col-span-1">
                <div class="mb-2">도서명</div>
              </div>
              <div class="col-span-4 text-maintheme1 m-2">
                <div class="mb-2">{{ bookDetail.title}}</div>
              </div>
            </div>
            <div id="author" class="container grid grid-cols-6 flex">
              <div class="text-maintheme1 m-2 font-bold col-span-1">
                <div class="mb-2">저자</div>
              </div>
              <div class="col-span-4 text-maintheme1 m-2">
                <div class="mb-2">{{ bookDetail.author}}</div>
              </div>
            </div>
            <div id="publisher" class="container grid grid-cols-6 flex">
              <div class="text-maintheme1 m-2 font-bold col-span-1">
                <div class="mb-2">출판사</div>
              </div>
              <div class="col-span-4 text-maintheme1 m-2">
                <div class="mb-2">{{ bookDetail.publisher}}</div>
              </div>
            </div>
            <div id="publish-date" class="container grid grid-cols-6 flex">
              <div class="text-maintheme1 m-2 font-bold col-span-1">
                <div class="mb-2">출간일</div>
              </div>
              <div class="col-span-4 text-maintheme1 m-2">
                <div class="mb-2">{{ bookDetail.pubDate.toString().split('T')[0]}}</div>
              </div>
            </div>
            <div id="price" class="container grid grid-cols-6">
              <div class="text-maintheme1 m-2 font-bold col-span-1">
                <div class="mb-2">정가</div>
              </div>
              <div class="col-span-4 text-maintheme1 m-2">
                <div class="mb-2">{{ bookDetail.priceStandard.toLocaleString()}}원</div>
              </div>
            </div>
            <div id="ISBN" class="container grid grid-cols-6 flex">
              <div class="text-maintheme1 m-2 font-bold col-span-1">
                <div class="mb-2">ISBN</div>
              </div>
              <div class="col-span-4 text-maintheme1 m-2">
                <div class="mb-2">{{ bookDetail.isbn }}</div>
              </div>
            </div>
            <div id="book-description" class="container grid grid-cols-6 flex mb-4">
              <div class="text-maintheme1 m-2 font-bold col-span-1">
                <div>도서 설명</div>
              </div>
              <div class="col-span-4 text-maintheme1 m-2">
                {{ bookDetail.description}}
              </div>
            </div>
           
          </div>
      </div>
    </div>
</template>

  
<script setup lang="ts">
import Navvar from '@/components/common/Navvar.vue'
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useBestsellerStore } from '@/stores/bestsellercounter' 

const store = useBestsellerStore()
const route = useRoute()


const bookDetail = computed(() => {
  const id = Number(route.params.id) // 라우터 파라미터에서 id 가져오기
  return store.booklistItem?.item[id-1] || { title: '', author: '', categoryName: '', cover: '', description: '', isbn: '', pubDate: '', publisher: '', priceStandard: ''  }
})

</script>
  
<style scoped>
@font-face {
  font-family: 'gowun-dodum';
  src: url('../../../public/GowunDodum-Regular.ttf');
}
    #book-image {
      @apply w-[240px] h-[320px] ml-10
    }
  
</style>