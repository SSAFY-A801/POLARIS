![polarisLogo](/uploads/785a93976e9c338f38a4bdbd4c60cbe4/polarisLogo.png)

# 목차
- [개요](#개요)
- [개발 환경](#개발-환경)
- [서비스 화면](#서비스-화면)
- [주요 기능](#주요-기능)
- [기술 소개](#기술-소개)
- [설계 문서](#설계-문서)
- [팀원 소개](#팀원-소개)
# 개요
    
 **“읽지 않는 도서 가치 창출을 위한 위치 기반 중고 도서 거래 서비스”**
    
지역 기반의 도서 거래를 통해 지식 공유를 이끌어내고, 고객들에게 읽어보지 않은 책에 대한 독후감 또한 제공하여 새로운 도서를 접할 수 있도록 하겠습니다.
    
    
서점에서 구입한 도서는 한 번 읽고, 더 이상 읽지 않는 경우가 많습니다. 이러한 도서는 어떤 가치도 창출하지 못하고, 서재에서 장기간 방치됩니다.
    
이러한 문제점들을 해결하기 위해 위치 기반 도서 거래 서비스 **"북극성"** 을 기획하게 되었습니다.  
    
또한, 기존의 중고 거래 플랫폼의 경우 도서를 중심으로 이루어지지 않아 도서를 교환하거나 판매하는 데에 불편함이 있었는데, 이러한 문제점을 해결하기 위해 위치 기반 도서 교환 및 거래 서비스를 생각해냈고 이를 통해 도서를 읽는 사람들끼리 지식 공유가 가능하도록 하였습니다.


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

# 서비스 화면



# 주요 기능


1. 실시간 1:1 채팅 기능
- 채팅방 안에서 거래하고자 하는 도서를 추가하거나, 삭제할 수 있음
- redis를 통해 캐싱된 이전 채팅 내역을 볼 수 있음
2. 사용자 주변 거래 도서 목록 제공
- 위치를 통해 검색함으로써 자신 주변의 도서들을 검색할 수 있음
3. 독후감 작성 및 crud 기능

4. 이 주의 독후감 기능
- 가장 많이 스크랩된 독후감과 도서를 보여주는 기능

5. 사용자 인기 도서 제공 기능
- 사용자들의 서재에 가장 많이 들어간 도서를 보여주는 기능
- 보이는 도서를 클릭해 내 서재에 추가할 수 있음
- redis를 통해 캐싱함으로써 DB까지 접근하지 않고도 가져올 수 있음

6. 내 서재 편집 기능
- 본인이 담고 싶은 도서를 알라딘 오픈 API를 활용한 도서 검색을 통해 추가하는 기능
- 도서 상태 수정 및 도서 삭제 기능

# 기술 소개



# 설계 문서



# 팀원 소개

### ✔️Back-end
<table>
    <tbody>
        <tr>
    <td align="center"><a href="https://github.com/DeveloperYard"><img src="" width="100px;" alt=""/><br /><sub><b>팀장 : 김승우</b></sub></a><br /></td>
      <td align="center"><a href=""><img src="" width="100px;" alt=""/><br /><sub><b>BE 팀장 : 김미서</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/madcom96"><img src="" width="100px;" alt=""/><br /><sub><b>BE 팀원 : 황진하</b></sub></a><br /></td>
</tr>
    </tbody>
<table>

### ✔️Frond-end
<table>
  <tbody>
<tr>
      <td align="center"><a href=""><img src="" width="100px;" alt=""/><br /><sub><b>FE 팀장 : 권원영</b></sub></a><br /></td>
      <td align="center"><a href=""><img src="" width="100px;" alt=""/><br /><sub><b>FE 팀원 : 박주현 </b></sub></a><br /></td>
      <td align="center"><a href=""><img src="" width="100px;" alt=""/><br /><sub><b>FE 팀원 : 조서현 </b></sub></a><br /></td>
     <tr/>
      
    
  </tbody>
</table>
