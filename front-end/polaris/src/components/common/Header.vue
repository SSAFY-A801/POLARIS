<template>
    <nav class="bg-white fixed top-0 w-full z-50">
        <div class="px-12 mx-0 flex justify-center"> 
            <div class="flex mt-2 items-center space-x-2 sm:space-x-4 md:space-x-6 lg:space-x-8">
                <router-link :to="{name: 'home'}" class="text-white">
                    <img id="logo" src="@\assets\polarisLogo.png" alt="">
                </router-link>
            </div>
        </div>
    </nav>
</template>


<script setup lang="ts">

</script>

<style scoped>

#logo {
    height: 80px;
}
</style>