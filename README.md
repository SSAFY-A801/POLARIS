
![logo](https://github.com/seomiii/seomiii/assets/86819719/0b826222-965c-4aa1-97c6-b030bddd20b7)
# 목차
- [개요](#개요)
- [개발 환경](#개발-환경)
- [서비스 화면](#서비스-화면)
- [주요 기능](#주요-기능)
- [기술 소개](#기술-소개)
- [설계 문서](#설계-문서)
- [팀원 소개](#팀원-소개)
# 개요
    
## **중고 도서 거래 서비스, " 북극성 ”**

서점에서 구입한 도서는 한 번 읽고, 더 이상 읽지 않는 경우가 많습니다. 이러한 도서는 어떤 가치도 창출하지 못하고, 서재에서 장기간 방치됩니다.
<br> 더불어, 기존의 중고 거래 플랫폼의 경우 거래가 도서를 중심으로 이루어 지지 않아 도서를 거래하는 데에 적합하지 않았습니다.
<br> 이러한 문제점들을 해결하기 위해 중고 도서 거래 서비스 **‘ 북극성 ’** 을 기획하게 되었습니다.

북극성의 채팅방 안에서는 도서 1권 대 1권 거래가 아닌 , 여러 권의 책들을 한번에 거래할 수 있습니다. <br>
주변 이웃들과 도서를 교환하거나, 판매 및 나눔을 통해 더 이상 읽지 않는 책에 가치를 창출해보세요 !
<p> 더불어, 내가 원하는 도서를 등록하여 나의 서재를 내 마음대로 꾸며보세요 !  <br> 독후감 작성을 통해 다른 사람들과 다양한 감상을 나눌 수 있습니다.


# 개발 환경
💻 **Backend**

<img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white"/>
<img alt="Spring Security" src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white"/>
<img alt="JSON Web Token" src="https://img.shields.io/badge/JSON Web Token-000000?style=for-the-badge&logo=jwt&logoColor=white"/>
<img alt="Redis" src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"/>
<img alt="MariaDB" src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white"/>

📱 **Frontend**

<img alt="HTML5" src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white"/>
<img alt="Tailwind CSS" src="https://img.shields.io/badge/Tailwind CSS-06B6D4?style=for-the-badge&logo=tailwind css&logoColor=white"/>
<img alt="TypeScript" src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white"/>
<img alt="Vue.js" src="https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white"/>
<img alt="Vite" src="https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white"/>
<img alt="Axios" src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white"/>

🚀 **Infrastructure**

<img alt="Amazon AWS" src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=amazon aws&logoColor=white"/>
<img alt="Amazon S3" src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=amazon s3&logoColor=white"/>
<img alt="NGINX" src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=nginx&logoColor=white"/>
<img alt="Docker" src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
<img alt="Ubuntu" src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white"/>
<img alt="Jenkins" src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white"/>

⚙️ Management Tools

<img alt="Jira" src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&amp;logoColor=white"/>
<img alt="GitLab" src="https://img.shields.io/badge/GitLab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white"/>
<img alt="Mattermost" src="https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white"/>
<img alt="Notion" src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white"/>
<img alt="Figma" src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"/>

🖥️ **IDE**

<img alt="Visual Studio Code" src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=for-the-badge&logo=visual studio code&logoColor=white"/>
<img alt="IntelliJ" src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=intellij&logoColor=white"/>


# 주요 기능

### 1. 실시간 1:1 채팅을 통한 도서 거래
- 채팅방 안에서 거래하고자 하는 도서를 추가하거나, 삭제할 수 있음
- 이를 통해 N:M 도서 거래가 가능함
- redis를 통해 캐싱된 이전 채팅 내역을 볼 수 있음

### 2. 나의 서재 기능
- 알라딘 open API를 통한 손쉬운 도서 등록
- 도서 공개 여부 설정 및 보유 여부에 따른 판매/나눔/교환 태그 표시
- 도서 상태 수정 및 도서 삭제 기능

### 3. 도서 검색
- 도서가 등록된 위치를 검색하여 자신 주변의 도서들을 검색할 수 있음
- 등록된 도서의 제목, 저자로 검색할 수 있음

### 4. 독후감 게시판 기능
- 독후감을 작성, 수정, 삭제를 할 수 있음
- 댓글과 스크랩을 할 수 있음

### 5. 이 주의 독후감 기능
- 가장 많이 스크랩된 독후감과 도서를 보여줌

### 6. 사용자 인기 도서 제공 기능
- 사용자들의 서재에 가장 많이 들어간 도서를 보여 줌
- 해당 도서를 내 서재에 추가할 수 있음
- redis에 캐싱하여 DB까지 접근하지 않고 해당 데이터를 빠르게 가져올 수 있음

# 기술 소개

## BackEnd
- 채팅 메세지 저장
  - Redis의 in-memory DB를 활용하여 채팅 메시지를 실시간으로 저장
    List 자료 구조를 활용하여 메시지 내역을 캐싱
- 도서 등록 및 수정 시 해당 채팅방 적용
  - 채팅방에 도서를 등록하거나 수정할 경우, 해당 정보를 참여 중인 모든 채팅방에 실시간으로 적용
  - Server Sent Event (SSE)를 활용한 실시간 업데이트 제공
- 프로필 사진 업로드 및 수정
  - AWS S3를 활용하여 프로필 사진을 업로드하고 수정
    사진이 저장된 URL을 반환하여 사용 가능하게 함
- 사용자 인기 도서
  - MariaDB에서 가장 많이 등록된 사용자 도서를 추출
  - In-memory DB의 SortedSet 자료 구조를 활용하여 도서의 인기 순서대로 보여줌
- Spring Data JPA 활용
  - 채팅, 게시글 등의 데이터베이스 쿼리 작업을 수행
  - 이를 통해 JDBC를 직접 이용하거나 쿼리를 개별적으로 작성하는 대신, JPA를 활용하여 쿼리 옵티마이저를 활용하고 데이터베이스 상호작용을 효율적으로 구현
- 카카오 OAuth를 통한 로그인 기능 도입
  - 사용자가 편리하게 로그인할 수 있도록 카카오 OAuth를 도입하여 소셜 로그인 기능을 제공
- 자체 회원 가입 기능 구현 및 메일 인증 도입
  - Gmail의 SMTP를 활용하여 이메일을 전송하고, 발송된 코드는 Redis에 저장
  - 이후 사용자가 입력한 코드가 Redis에 저장된 코드와 일치하는지 검사하여 인증을 완료

## FrontEnd
- 북극성 프로젝트의 사용자 인터페이스
  - Vue.js의 최신 버전인 Vue3를 사용하여 양방향 데이터 바인딩이 가능한 파일 컴포넌트 구조를 수립
  - Composition API를 통해 보다 논리적이고 재사용성이 높은 로직으로 코드의 가독성과 유지보수성 확보
- 알라딘 도서정보, 사용자 등록 도서, 채팅 메시지 등의 API 요청
  - HTTP 클라이언트 라이브러리인 Axios를 통해 다양한 API 요청과 비동기 통신을 구현하고 AJAX를 효과적으로 구현함
- 채팅, 독후감, 유저, 사용자 등록 도서 등 데이터 통합 관리
  - Pinia의 store를 통해 간결하고 직관적으로 애플리케이션의 상태를 통합적으로 저장하고 관리, 변수의 재사용성 확보
- 독후감 게시글 작성
  - WYSIWYG 에디터 라이브러리인 Tiptap editor를 이용하여 사용자 친화적인 게시글 작성 환경을 제공
- 로그인 및 회원가입, 도서 등록 등 사용자의 동작 성공 시 알림 제공
  - 클래스 기반의 유틸리티 퍼스트(CSS utility-first) 접근 방식을 채택하는 Tailwind CSS와 SweetAlert2를 이용하여 보다 직관적이고 효과적인 알림 정보 제공
  - fontawsome의 백터 아이콘을 이용하여 여러 페이지의 반응형 웹 디자인에 적용


# 서비스 화면

### [ 메인 페이지 ]
<table align=center>
  <tr>
    <td>
      <img width="380" src="https://github.com/DeveloperYard/algorithm/assets/59395755/cab94a03-7ffe-4771-9de7-87bd410c331d">
    </td>
    <td>
      <img width="380" src="https://github.com/DeveloperYard/algorithm/assets/59395755/14ba7839-572b-4a69-bf64-e92852fc6d55">
    </td>
    <td>
      <img width="380" src="https://github.com/DeveloperYard/algorithm/assets/59395755/7a510298-26fe-4901-9f3e-f7da0579e1ee">
    </td>
  </tr>
  <tr>
    <td>
      <p align=center>이 주의 책</p>
    </td>
    <td>
      <p align=center>서점 베스트 셀러</p>
    </td>
    <td>
      <p align=center>북극성 인기 도서</p>
    </td>
  </tr>
</table>

- 스크랩 수가 가장 많은 독후감을 보여줍니다. 해당 데이터는 7일 간격으로 갱신됩니다.
- 알라딘 open api에서 제공하는 서점 베스트 셀러 20권을 보여줍니다.
- 가장 많이 등록된 사용자 도서 20권을 보여줍니다. 


### [ 나의 서재 ]
<table align=center>
  <tr>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/93c2bc62-ce74-4a07-bb5d-4600a4f2c9e8" width=380 />
    </td>
    <td>
      <img src="https://github.com/DeveloperYard/algorithm/assets/59395755/52a31847-c62f-4039-8401-5f8355799e66" width="380">
    </td>
    <td>
      <img src="https://github.com/DeveloperYard/algorithm/assets/59395755/3826ac8b-5d3b-415f-ba67-953fdcbdaaed" width="380">
    </td>
    <td>
      <img src="https://github.com/DeveloperYard/algorithm/assets/59395755/3826ac8b-5d3b-415f-ba67-953fdcbdaaed" width="380">
    </td>
    <td>
      <img width="380" alt="image" src="https://github.com/seomiii/seomiii/assets/86819719/64458940-9175-4f76-8964-e8bd314553b8">
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/82a58fab-cdfe-4f45-b9bf-7d86beab91b7" width="380">
    </td>
  </tr>
  <tr>
    <td>
      <p align=center>도서 등록</p>
    </td>
    <td>
      <p align=center>사용자 도서 상세 정보</p>
    </td>
    <td>
      <p align=center>나의 독후감</p>
    </td>
    <td>
      <p align=center>스크랩한 독후감</p>
    </td>
    <td>
      <p align=center>나의 채팅 목록</p>
    </td>
    <td>
      <p align=center>팔로잉</p>
    </td>
  </tr>
</table>

- 알라딘 open api를 활용하여 손쉽게 도서를 등록할 수 있습니다.
- 등록한 도서의 공개 여부, 보유 여부를 설정하고 거래 유형을 지정할 수 있습니다.
- 사용자가 작성한 독후감, 스크랩한 독후감, 채팅목록, 팔로잉 리스트를 볼 수 있습니다.


### [ 도서 검색 ]
<table align=center>
  <tr>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/36613de9-8d52-465f-b2e0-f4ac1db162dc" width=380 />
    </td>
  </tr>
  <tr>
    <td>
      <p align=center>지역, 제목, 저자를 기준으로 도서 검색</p>
    </td>
  </tr>
</table>

- 북극성에 등록된 거래가 가능한 도서를 지역, 제목, 저자 기준으로 검색할 수 있습니다.

### [ 도서 거래 채팅 ]
<table align=center>
  <tr>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/a47e6f92-66b8-4fe7-a81d-35ff38a3d689" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/dfe1a835-6e1e-4fac-8926-ee6a04a37e7f" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/5853a7d7-c200-4383-8afd-b8a35ded1100" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/11c2c46a-eac1-44b0-b650-1afb08907932" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/d5897f3b-7dec-439b-9a1f-4b4b2e030449" width=380 />
    </td>

  </tr>
  <tr>
    <td>
      <p align=center>1:1 채팅</p>
    </td>
    <td>
      <p align=center>교환 채팅방 - 교환 도서 선택</p>
    </td>
    <td>
      <p align=center>교환 채팅방 - 교환 도서 선택 2</p>
    </td>
    <td>
      <p align=center>구매 채팅방</p>
    </td>
    <td>
      <p align=center>판매 채팅방</p>
    </td>
  
  </tr>
</table>

- 거래자와 실시간 1:1 채팅을 할 수 있습니다.
- 거래자와 N:M 도서 거래가 가능합니다.
- 채팅방 안에서 거래 도서를 추가하고, 삭제할 수 있습니다.
- 채팅방 안의 거래 도서가 변경되면 해당 채팅방에는 도서 목록이 실시간으로 업데이트 됩니다.

### [ 독후감 게시판 ]
<table align=center>
  <tr>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/6574af80-99e8-416d-89a4-7133bbf4468a" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/19339f6f-8af2-41d4-84e5-7fc71de7a06b" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/c94b3f76-3f72-49f8-b94c-920b0fb9c08c" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/1b3b8eaa-3978-4b82-a958-d131169dd24c" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/99620d2d-145d-4f2c-9226-4e74e3defc5f" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/5aee21e0-e1b6-4340-a631-5fda0cc4dbf2" width=380 />
    </td>

  </tr>
  <tr>
    <td>
      <p align=center>독후감 리스트</p>
    </td>
    <td>
      <p align=center>독후감 상세페이지</p>
    </td>
    <td>
      <p align=center>독후감 작성 및 스크랩</p>
    </td>
    <td>
      <p align=center>독후감 작성 완료</p>
    </td>
    <td>
      <p align=center>작성한 독후감 상세 페이지</p>
    </td>
    <td>
      <p align=center>독후감 수정</p>
    </td>
  </tr>
</table>

- 독후감을 통해 다양한 사용자들과 도서의 감상을 공유할 수 있습니다.
- 독후감을 작성, 수정, 삭제를 할 수 있습니다.
- 마음에 드는 독후감을 스크랩 할 수 있습니다.
- 독후감에 댓글을 작성, 수정, 삭제를 할 수 있습니다.

### [ 회원 가입 ]
<table align=center>
  <tr>
    <td>
      <img alt="image" src="https://github.com/DeveloperYard/algorithm/assets/59395755/2c082b4b-ee12-4e07-8aaf-b61a570eab5c" width="380">
    </td>
    <td>
      <img alt="image" src="https://github.com/DeveloperYard/algorithm/assets/59395755/87f6e5e6-ae9d-40de-8ae7-f6e809c2c1f8" width="380">
    </td>
    <td>
      <img alt="image" src="https://github.com/DeveloperYard/algorithm/assets/59395755/f986e0ee-7124-490e-a007-af32a14ccb70" width="380">
    </td>
    <td>
      <img alt="image" src="https://github.com/DeveloperYard/algorithm/assets/59395755/462e9e36-ec8f-4f2a-b4d3-74ce95524949" width="380">
    </td>
  </tr>
  <tr>
    <td>
      <p align=center>자체 로그인 회원가입 페이지</p>
    </td>
    <td>
      <p align=center>이메일 인증 - 인증번호 입력 전</p>
    </td>
    <td>
      <p align=center>이메일 인증 - 인증번호 입력 후</p>
    </td>
    <td>
      <p align=center>이메일 인증 - 인증 메일</p>
    </td>

  </tr>
</table>

- 이메일 인증을 통하여 자체 회원가입을 진행합니다.
- 카카오 소셜 로그인을 할 수 있습니다.

# 설계 문서

### 📄 ERD
![polaris_erd](https://github.com/seomiii/seomiii/assets/86819719/4f688444-8efd-4943-9fcf-01c81cb141a7)
### 📄 아키텍쳐
![최종 아키텍쳐](https://github.com/seomiii/seomiii/assets/86819719/e6b22e13-bc11-4221-ab87-a607e1331c71)
### 📄 [요구사항 정의서](https://fresh-fang-cb0.notion.site/7c27960a8fd54b18a1d729632fef056c)
### 📄 [기능 명세서](https://fresh-fang-cb0.notion.site/068178a6068145d4867c4c8be4302253?pvs=4)




# 팀원 소개

| ![image](https://avatars.githubusercontent.com/u/59395755?v=4) | ![image](https://avatars.githubusercontent.com/u/86819719) | ![image](https://avatars.githubusercontent.com/u/70102600?v=4) | ![image](https://avatars.githubusercontent.com/u/139421101?v=4) | ![image](https://avatars.githubusercontent.com/u/139318088?v=4) | ![image](https://avatars.githubusercontent.com/u/139318338?v=4) |
|:----------------------------------------------------------:|:--------------------------------------------------------------:|:--------------------------------------------------------------:|:---------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------:|:----------:|
|                   Team Leader & Back-End                   |                        Back-End Leader                         |                            Back-End                            |                        Front-End Leader                         |                                                Front-End                                                | Front-End  |
|          [김승우](https://github.com/DeveloperYard)           |                [김미서](ttps://github.com/seomiii)                |               [황진하](ttps://github.com/madcom96)                |              [권원영](https://github.com/iamadreamer10)              |                                        [조서현](https://github.com/jojoo99)                                         |  [박주현](https://github.com/Park0Juhyun)   |

