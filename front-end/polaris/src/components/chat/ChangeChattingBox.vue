<template>
  <div class="first-container">
    <div class="content">
  <div class="text-2xl m-3 font-semibold">도서교환을 위한 채팅공간</div>  
  <div class="chatcontainer">
  <div class="flex">
  <div class="bg-white shadow rounded-lg p-4 ml-4" style="height: 700px;">
  <div>
    <h3 class="text-xl font-bold text-gray-900 mb-2">Chat Room</h3>
    <span class="text-base font-normal text-gray-500">상대방과 이야기하여 거래도서를 선택해보세요</span>
  </div>
  <div>
    <div class="bg-gray-50 shadow rounded-lg mb-8 mr-4 ml-4 mt-4" style="width: 400px; height: 500px;">
      <div>대화를 나눠보자</div>
    </div>
  </div>
  <div class="flex justify-center">
      <button class="inline-block rounded bg-maintheme1 h-8 px-2 py-1 text-sm font-medium text-white mr-3">img</button>
      <textarea class="border" style="width: 300px;"></textarea>
      <button  class="inline-block rounded bg-maintheme1 h-8 px-4 py-2 text-xs font-medium text-white hover:bg-maintheme2 ml-3">전송</button>
    </div>
  </div>
</div>

<div class="list-container ml-10">
  <div class="container">
    <div class="book-list bg-white shadow rounded-lg p-4 sm:p-6 xl:p-8">
      <div class="book-list-header mb-4 flex items-center justify-between">
        <div>
          <h3 class="text-xl font-bold text-gray-900 mb-2">교환받을 도서목록</h3>
          <span class="text-base font-normal text-gray-500">내가 빌릴 도서의 목록입니다.</span>
        </div>
      </div>
      <div class="book-list-content flex flex-col mt-8">
        <div class="overflow-x-auto rounded-lg">
          <div class="book-table align-middle inline-block min-w-full">
            <div class="shadow overflow-y-auto h-72 sm:rounded-lg">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                      도서 이름
                    </th>
                    <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                      저자
                    </th>
                    <th scope="col" class="p-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">
                      ISBN
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white">
                  <tr>
                    <td class="p-4 w-auto text-sm font-normal text-gray-900"></td>
                    <td class="p-4 w-auto text-sm font-normal text-gray-500"></td>
                    <td class="p-4 whitespace-nowrap text-sm font-normal text-gray-900"></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="my-changing-book-container">
    <MyChangingBook/>
  </div>
</div>

</div>
</div></div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  import MyChangingBook from './MyChangingBook.vue';

  // 상태표시등의 상태와 상태버튼 연동
  // 상태표시등은 서로 배타적(동시에 뜨는 경우 없음)
  // 빨간불일 경우 거래확인 버튼이 보여야 함
  // 파란불일 경우 거래수정과 거래완료 버튼이 보여야 함
  // 거래완료 버튼 클릭시, 담긴 교환도서를 저장
  </script>
  
  <style scoped>
  .chatbox {
    width: 600px; 
    height: 600px;
    border: 1px solid lightgrey;
    border-radius: 2%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .chatheader {
    text-align: center;
  
  }

  .input {
  background-color: rgb(246, 244, 221);
  border-top: 1px solid lightgray;
  display: flex;
  padding: 5px;
  }
  
  .message {
  width: 85%;
  }

  .chatcontainer {
    display: flex;
  }

  /* .listcontainer {
    display: flex;
    flex-direction: column;
  } */


  .box2{
    width: 600px;
    height: auto;
    border: 1px solid lightgrey;

  }


  .select-book{
    position: absolute;
    right: 0;
    margin-right: 10px;
  }
  
  .change-condition {
    background-color: blanchedalmond;
    display: flex;
    position: relative;

  }

  .list{
    border: 1px solid lightgray;
    height: 163px;
  }

  .button-div{
    position: absolute;
    right: 0;
    margin-right: 10px;
  }

  .container {
    max-height: 300px;
    overflow-y: auto;
    padding-bottom: 15px;
  }
  
  .first-container {
    padding-top: 50px;
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    height: 100vh;
    align-items: center;
  }
  .content {
    grid-column: 2;
  }
  </style>