<!-- <template>
  <div class="container mx-auto">
    <div id="carouselExample" class="carousel slide" style="width: 90%; margin: auto;">
      <div class="carousel-inner">
        <div class="carousel-item" v-for="(n, index) in 4" :key="n" :class="{ active: index === 0 }">
          <div class="d-flex flex-wrap justify-content-center">
            <div v-for="bookItem in (store.booklistItem ? store.booklistItem.item.slice((n-1)*5, n*5) : [])" :key="bookItem.itemId" class="p-2">
              <router-link :to="{ name: 'bestsellerdatail', params: { id: bookItem.bestRank }}" style="text-decoration: none;">
                <div class="m-auto overflow-hidden rounded-lg shadow-lg cursor-pointer w-48 h-72">
                  <div class="block w-full h-full">
                    <div class="font-medium text-maintheme1 font-bold text-md mx-auto text-center mt-2 mb-2">
                      {{bookItem.bestRank}}위
                    </div>
                    <img :src="bookItem.cover" class="object-cover mx-auto" style="width: 85px;"/> 
                    <div class="w-full p-4 bg-white dark:bg-gray-800">
                      <p class="mb-2 text-xs font-medium text-gray-800 ">
                        {{truncateTitle(bookItem.title)}}
                      </p>
                      <p class="font-light text-gray-400  text-xs">
                        {{truncateTitle(bookItem.author)}}
                      </p>
                    </div>
                  </div>
                </div>
              </router-link>
            </div>
          </div>
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev" style="left: -50px;">
        <span class="carousel-control-prev-icon" aria-hidden="true" style="color: rgb(50, 63, 89);"></span>
        <span class="visually-hidden" style="color: rgb(50, 63, 89); ">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next" style="right: -50px; ">
        <span class="carousel-control-next-icon" aria-hidden="true" style="color:rgb(50, 63, 89)"></span>
        <span class="visually-hidden" style="color: rgb(50, 63, 89)">Next</span>
      </button>
    </div>
  </div>
</template>



  
  
<script setup lang="ts">
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import { ref, onMounted } from 'vue';
import axios from 'axios'
import { Carousel } from 'bootstrap'
import { useBestsellerStore } from '@/stores/bestsellercounter';

const store = useBestsellerStore()

onMounted(async () => {
  await store.fetchBooklistItem()
})
  
  
  const truncateTitle = (title: string) => {
    return title.length > 25 ? title.substring(0, 25) + "..." : title;
  };
  
  
  
  onMounted(() => {
    const myCarousel = document.querySelector('#carouselExample');
    if (myCarousel) {
      const carousel = new Carousel(myCarousel, {
        interval: 2000,
        wrap: false
      });
    }
  });
  
  
  </script>
  
  <style scoped>
  .carousel-control-prev-icon,
.carousel-control-next-icon {
  background-image: none !important;
}

.carousel-control-prev-icon::before,
.carousel-control-next-icon::before {
  content: '‹' !important; 
  color: rgb(50, 63, 89);
  font-size: 2em;
}

.carousel-control-next-icon::before {
  content: '›' !important;
}
  </style> -->