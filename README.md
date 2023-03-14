# Sketcher-scheduling
Sketcher team scheduling task

## 📌 Summary

> 캡스톤 디자인에서 진행한 프로젝트이며, **다수의 인력 배정 문제를 해결**하기 위해 스타트업 [스프린트](https://apps.apple.com/kr/app/%EC%8A%A4%ED%94%84%EB%A6%B0%ED%8A%B8-sprint-%EC%9E%90%EB%8F%99-%EC%8B%9D%EB%8B%A8-%EA%B8%B0%EB%A1%9D%EC%95%B1/id1505449642)와 **기업 연계형**으로 진행하였습니다.
개발한 결과물을 스타트업에 제안하였고,
**교내 캡스톤 디자인 경진대회에서 학부 1등**으로 선정되어 경기도지사상을 받았습니다.

> 매칭되지 않은 스케줄을 최소화하면서 50% 이상 시간 단축을 목적으로 **이분매칭(Bipartite Matching)** 알고리즘을 적용하여 **자동 스케줄 배정 기능을 개발**하였습니다. <br>이를 통해 평균 1시간 이상 소요되는 일주일 치 스케줄 배정을 최대 20분으로 절감하였습니다.


<p>
 <img width="700" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/8ef4756e-6ffc-49ca-9b18-793245a2d9ab/%ED%8C%9C%ED%94%8C%EB%A0%9B.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T010455Z&X-Amz-Expires=86400&X-Amz-Signature=78469f7aa8455734c6f3a9ae51529102e010b36eda31b2ee94c86874a8c4fe72&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22%25ED%258C%259C%25ED%2594%258C%25EB%25A0%259B.png%22&x-id=GetObject">
 </p>



프로젝트 기간 : `2022.01 ~ 2022.09`

[Notion](https://maddening-prawn-76c.notion.site/655f4a28ef994b6fb3a9eff636565d0c)에서 편하게 확인할 수 있습니다.

<br>

### 🔗 발표 및 데모 영상

[https://cms.tukorea.ac.kr/em/6357e7e01ae4c](https://cms.tukorea.ac.kr/em/6357e7e01ae4c)

<br>

### 🛠️ Specification

- Spring boot, Gradle
- Java, JavaScript
- JPA
- QueryDSL
- MySQL, H2
- Spring Security
- Docker
- AWS Elastic Beanstalk
- Github Action (CI/CD)
- Git, Notion, Slack

<br>

### 🏛️ ****Architecture****
 <img width="700" alt="" src="https://user-images.githubusercontent.com/48792230/224879050-e76ac97e-2c8b-4f7f-9888-189cf246c3cc.jpg">

<br>

### 🌟 ****Contributor****

| 이름 | 박태영 | 이혜원 | 정민환 |
| :---: | :---: | :---: | :---: |
| 깃헙 아이디 | [ty990520](https://github.com/ty990520) | [dclxxi](https://github.com/dclxxi) | [dokongMin](https://github.com/dokongMin) |
| 역할 | 팀 리드, 회원가입 및 로그인, <br>요청알림 관리, CI/CD 설정 | 페이징 및 검색 처리, 매니저 관리, <br>마이페이지, 카카오톡 API기반 알림 전송 | 오픈소스 Full-calendar기반 <br> 스케줄 CRUD, 휴가 매니저 관리 
| 포지션 | 프론트엔드 및 백엔드 | 프론트엔드 및 백엔드 | 백엔드 |

`스케줄 배정` 기능의 경우 페어 프로그래밍으로 진행하였습니다.

<br>

---
<br>

## 📋프로젝트 기능 설명

### **(1) 회원가입 및 희망근무타임 설정**

- 기존의 문제점
  - 네이버 폼을 통해 로그인에 필요한 계정을 입력받고 희망근무타임을 설정 <br> → 이후 관리자가 프로그램에 회원가입 작업 진행
  
- 보안을 고려한 회원가입 절차 & 회원가입 중 희망근무 타임 설정

 <p>
 <img width="600" alt="" src="https://user-images.githubusercontent.com/48792230/224879047-8fd00735-1525-4aa1-963a-0f4f5ba42902.png">
 </p>

<br>


<br>

### **(2) 스케줄 배정 및 조회**

- 기존의 문제점
  - 스프레드시트를 사용하여 수동으로 스케줄 배정

<br>

▶ **스케줄 배정 기능 서비스 흐름도**

 <img width="700" alt="" src="https://user-images.githubusercontent.com/48792230/224879043-f51b7637-ba91-4aed-990e-5cc6047f173b.jpg">
 </p>
 <br>

1) 이분매칭 알고리즘을 적용하여 스케줄 자동 배정 가능

      <p>
 <img width="700" alt="" src="https://user-images.githubusercontent.com/48792230/224879034-6f2ccafc-3839-4434-b898-c0034e7f11af.png">
 </p>
 
 <br>
 
2) 카카오톡 API를 활용하여 스케줄 등록 시 매니저에게 알림 발송

      <p>
 <img width="400" alt="" src="https://user-images.githubusercontent.com/48792230/224880528-b730fe43-8d1b-449f-92ca-a0a3b4b67cfe.png">
 </p>
 
 <br>
 
3) Full-calendar 오픈소스 라이브러리를 사용하여 원하는 캘린더 유형으로 조회 가능

      <p>
 <img width="400" alt="" src="https://user-images.githubusercontent.com/48792230/224879408-7f70a7da-43a8-4e8a-86d4-1a3409afe609.png"> <img width="400" alt="" src="https://user-images.githubusercontent.com/48792230/224878977-d08a2124-7e51-4632-a02d-eed4f9e15f3a.png">
 </p>
 

<br>
<br>

### (3) 근무 기록 및 진행도를 제공하는 마이페이지

 <img width="700" alt="" src="https://user-images.githubusercontent.com/48792230/224878968-45b8f4f2-e745-480e-8ee1-65a7cb7976be.jpg">

