<template>
  <div class="container border mx-auto mt-24 max-w-6xl min-w-[700px] bg-backgroundgray p-5">
    <!-- 최상단 : 독후감 제목 & 프로필 -->
    <div class="flex justify-between items-center p-5 m-1 border-b  border-black">
      <div class="flex items-center">
        <span class="bg-maintheme1 text-white text-sm rounded-xl px-2 py-1 m-2">독후감</span>
        <div class="text-xl font-semibold">독후감 제목: 이런 책은 처음이에요.</div>
      </div>
      <div class="flex items-center">
        <button id="profile-image">프로필 이미지</button>
        <div>작성자 이름</div>
      </div>
    </div>
    <!-- 조회수 & 스크랩 & 작성일 -->
    <div class="flex justify-end gap-4 p-3">
      <div>조회수</div>
      <div>스크랩</div>
      <div>작성일자</div>
    </div>
    <!-- 독후감 정보 -->
    <div class="p-10">
      <!-- 도서 정보 -->
      <div class="grid grid-cols-12 gap-4 shadow-lg rounded-xl p-3">
        <div class="col-span-3 border">
          <img id="book-image" src="@\assets\book-image.jpg" alt="">
        </div>
        <div class="col-span-9">
          <div>책 제목: 이상한 나라의 아름다운 건물</div>
          <div class="mt-1">저자: 흥선대원군 / 출판사: 집현전 </div>
          <div class="mt-4">
            책 description: 이런저런 내용을 적어본다
          </div>
        </div>
      </div>
      <!-- 독후감 내용 -->
      <div class="mt-10">
        작년부터 여기저기 이야기가 나와 한번은 읽어봐야지 했던 '세이노의 가르침' 이북으로 보니까 드문드문 읽게 되어 4~5개월에 걸쳐 읽은듯합니다. 성공한 대가가 다양한 분야에 대한 자신의 가치관 삶의 철학 노하우를 이야기하는 책입니다. 저자의 필명인 '세이노(SayNo)처럼 남이 한다고 따르지 말고 본인만의 신념을 가지고 움직이라 조언하는 책입니다.
        <img class="m-2" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOs0ha7zg-fGhHqSZd16QtIKZKWG45yX0wsQ&usqp=CAU" alt="">
        본인의 철학과 가치관과 맞지 않을 수 있지만, 읽을 만한 가치는 있는 책이다.
        세이노의 가르침을 읽고 제가 내린 결론은 이렇습니다. 책을 좋아하는 '덕'이 붙은 많은 분들은 자기개발서는 읽어선 안된다고 하는 분들도 있지만, 저는 세상에 의미 없는 책은 없다는 주의라서요. 가벼운 책을 읽은 사람은 어려운 책도 읽을 수 있지만, 가벼운 책도 읽지 않는 사람은 어떤 책도 읽을 수 없습니다. 자신의 가치관과 삶의 의미에 맞는 책을 꾸준히 읽는 것을 추천합니다.

        “가시적 결과를 외부에서 찾지 말고 내부에서 찾아라. 당신 자신의 노력을 인정해 주고 칭찬하여야 할 주체는 타인이나 직장이나 사회가 아니다.”
      </div>
      <div class="flex justify-end border-b border-gray-400 mb-4">
        <button id="scrap-essay">스크랩</button>
      </div>
      <!-- 댓글 작성 -->
      <div class="flex">
        <input v-model="comment" type="text" id="book"  class="w-2/3 rounded-lg appearance-none border border-gray-500 py-4 px-4 m-2 bg-gray-50 text-maintheme1 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-gray-600 focus:border-transparent" placeholder="댓글 등록"/>
        <button @click="addComment(comment)" type="button" class="w-32 text-white  bg-maintheme1 hover:bg-gray-500 rounded-lg">
          댓글 등록
        </button>
      </div>
      <!-- 댓글 목록 -->
      <div>
        이런저런 댓글들 CommentList
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue';

  const comment = ref("")
  const addComment = (comment:string) => {
    alert("댓글 작성")
  }
</script>

<style scoped>
  #profile-image {
    @apply mx-2
  }

  #book-image {
    width: 150px;
  }

  #scrap-essay {
    @apply bg-white font-bold text-maintheme1 border border-maintheme1 border-2 px-2 py-1 m-2 rounded-lg hover:bg-slate-200
  }
</style>