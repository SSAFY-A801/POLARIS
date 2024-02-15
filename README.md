
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
⚙️ Management Tools

<img alt="Jira" src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&amp;logoColor=white"/>
<img alt="GitLab" src="https://img.shields.io/badge/GitLab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white"/>
<img alt="Mattermost" src="https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white"/>
<img alt="Notion" src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white"/>
<img alt="Figma" src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"/>

🖥️ **IDE**

<img alt="Visual Studio Code" src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=for-the-badge&logo=visual studio code&logoColor=white"/>
<img alt="IntelliJ" src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=intellij&logoColor=white"/>


🚀 **Infrastructure**

<img alt="Amazon AWS" src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=amazon aws&logoColor=white"/>
<img alt="Amazon S3" src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=amazon s3&logoColor=white"/>
<img alt="NGINX" src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=nginx&logoColor=white"/>
<img alt="Docker" src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
<img alt="Ubuntu" src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white"/>
<img alt="Jenkins" src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white"/>

📱 **Frontend**

<img alt="HTML5" src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white"/>
<img alt="Tailwind CSS" src="https://img.shields.io/badge/Tailwind CSS-06B6D4?style=for-the-badge&logo=tailwind css&logoColor=white"/>
<img alt="TypeScript" src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white"/>
<img alt="Vue.js" src="https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white"/>
<img alt="Vite" src="https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white"/>
<img alt="Axios" src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white"/>

💻 **Backend**

<img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white"/>
<img alt="Spring Security" src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white"/>
<img alt="JSON Web Token" src="https://img.shields.io/badge/JSON Web Token-000000?style=for-the-badge&logo=jwt&logoColor=white"/>
<img alt="Redis" src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"/>
<img alt="MariaDB" src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white"/>


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
- 이전 채팅 메세지 저장
    - in-memory DB인 Redis의 list 자료구조를 이용해 채팅 메세지 내역 캐싱
- 채팅방에서 도서를 등록하거나, 수정했을 경우 참여중인 모든 채팅방에 적용
  - Server Sent Event SSE를 이용하여 실시간 업데이트 제공
- 프로필 사진을 올리거나 수정
  - AWS S3를 이용, 버킷에 사진을 저장하여 해당 사진이 저장된 url을 반환함으로써 이용 가능
- 사용자 인기 도서
  - MariaDB 내의 가장 많이 등록된 사용자 도서를 가져와 In-memory DB의 SortedSet 자료 구조를 이용해 순서대로 보여줌
  
## FrontEnd




# 서비스 화면

### [ 도서 검색 ]
<table align=center>
  <tr>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/6574af80-99e8-416d-89a4-7133bbf4468a" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/19339f6f-8af2-41d4-84e5-7fc71de7a06b" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/edc02cff-da14-4898-b0bf-6ea00c92c556" width=380 />
    </td>
  </tr>
  <tr>
    <td>
      <p align=center>지역으로 검색</p>
    </td>
    <td>
      <p align=center>제목으로 검색</p>
    </td>
    <td>
      <p align=center>저자로 검색</p>
    </td>
  </tr>
</table>

- 도서를 지역으로 검색할 수 있습니다.
- 도서를 제목으로 검색할 수 있습니다.
- 도서를 저자로 검색할 수 있습니다.



### [ 도서 거래 채팅 ]
<table align=center>
  <tr>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/6574af80-99e8-416d-89a4-7133bbf4468a" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/19339f6f-8af2-41d4-84e5-7fc71de7a06b" width=380 />
    </td>
    <td>
      <img src="https://github.com/seomiii/seomiii/assets/86819719/edc02cff-da14-4898-b0bf-6ea00c92c556" width=380 />
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
      <p align=center>1:1 채팅</p>
    </td>
    <td>
      <p align=center>거래 가능 도서 선택</p>
    </td>
    <td>
      <p align=center>교환 채팅방</p>
    </td>
    <td>
      <p align=center>판매 및 구매 채팅방</p>
    </td>
    <td>
      <p align=center>작성한 독후감 상세 페이지</p>
    </td>
    <td>
      <p align=center>독후감 수정</p>
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
      <img src="https://github.com/seomiii/seomiii/assets/86819719/edc02cff-da14-4898-b0bf-6ea00c92c556" width=380 />
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
      <p align=center>독후감 작성</p>
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
- 독후감에 댓글을 작성, 수정, 삭제를 할 수 있습니다.



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

