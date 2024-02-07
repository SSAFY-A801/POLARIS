<template>

<div class="m-auto overflow-hidden rounded-lg shadow-lg cursor-pointer h-90 w-60 md:w-80">
    <a href="#" class="block w-full h-full">
        <img alt="cover" src="@\assets\book-image.jpg" class="object-cover w-full max-h-40"/>
        <div class="w-full p-4 bg-white dark:bg-gray-800">
            <p class="font-medium text-indigo-500 text-md">
                Article
            </p>
            <p class="mb-2 text-xl font-medium text-gray-800 dark:text-white">
                제모오오오옥
            </p>
            <p class="font-light text-gray-400 dark:text-gray-300 text-md">
                저자
            </p>
            <div class="flex items-center mt-4">
              <img alt="profile" src="@\assets\profile-man.jpg" class="mx-auto object-cover rounded-full h-10 w-10 "/>
                  <p class="text-gray-800 dark:text-white">
                      Jean Jacques
                  </p> 
                  
                  <!-- <div v-if="bookinfo.userBookTradeType =='PURCHASE'" id="trade"  class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2  bg-red-600"  >
                    <p  class="p-1.5 text-lightgray">판매</p>
                  </div>
                  <div v-else-if="bookinfo.userBookTradeType == 'EXCHANGE'" id="exchange"   class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 bg-yellow-500">
                    <p  class="p-1.5 text-lightgray">교환</p>
                  </div>
                  <div v-else id="undefined"   class="col-span-3 sm:inline-flex sm:shrink-0 sm:items-center sm:gap-2 bg-gray-500">
                    <p  class="p-1.5 text-lightgray">미거래</p>
                  </div> -->
            </div>
        </div>
    </a>
</div>



</template>

<script setup lang="ts">

  
</script>
  
  
<style scoped>

  
</style>