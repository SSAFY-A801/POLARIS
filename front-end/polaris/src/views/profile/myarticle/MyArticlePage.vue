<template>
    <!-- 작은 화면 -->
    <div>
      <div class="sm:hidden">
        <label for="Tab" class="sr-only">Tab</label>
        
        <select
        id="Tab"
        class="w-full rounded-md border-gray-200 dark:border-gray-700 dark:bg-gray-900 dark:text-white"
        >
        <option>나의 독후감</option>
        <option select>나의 홍보게시물</option>
      </select>
    </div>
    <!-- 전체 화면 -->

    <div>
      <div class="hidden sm:block">
        <div>
          <nav class="flex gap-6 m-2" aria-label="Tabs">
            <RouterLink :to="{ name: 'MyEssayPage'  ,params: {id: user.id} }">
              <a
              href="#"
              class="shrink-0 border-b-2 border-transparent px-1 pb-2 text-sm font-medium text-gray-500 hover:border-gray-300 hover:font-bold"
              >
                나의 독후감
            </a>
            </RouterLink>
            <RouterLink :to="{ name: 'MyPromotionPage', params: {id: user.id} }">
              <a
                href="#"
                class="shrink-0 border-b-2 border-transparent px-1 pb-2 text-sm font-medium text-gray-500 hover:border-gray-300 hover:font-bold"
              >
                나의 홍보게시글
              </a>
            </RouterLink>
          </nav>
        </div>
      </div>
    </div>

    <RouterView></RouterView>
  </div>
</template>

<script setup lang="ts">
  import { profileCounterStore } from '@/stores/profilecounter';
  
  const store = profileCounterStore();
  const user =store.user
</script>

<style scoped> 
</style>