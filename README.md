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
 <img width="700" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/1c15c169-2d47-4610-9b18-9b11df610059/structure.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T010735Z&X-Amz-Expires=86400&X-Amz-Signature=07eb873dc91cedc610ea7d29209ca06cb1a206b65a81b7d1cf464c66e89446e8&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22structure.jpg%22&x-id=GetObject">

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
    
    기존) 네이버 폼을 통해 로그인에 필요한 계정을 입력받고 희망근무타임을 설정 → 이후 관리자가 프로그램에 회원가입 작업 진행
    
    <p>
 <img width="400" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/959d1e62-feaa-470e-a9cf-850277f89436/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T012545Z&X-Amz-Expires=86400&X-Amz-Signature=7c7421eabd7f89dd95baa7642cc74643ed3afe92a7214e3b4f77d9a703b5926a&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject">
 </p>
 <p>
 <img width="400" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/6064af2d-3dc4-4e96-82bb-00de84bb6a9e/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T012635Z&X-Amz-Expires=86400&X-Amz-Signature=bca1f72780504985fd03fef87cb7eaa12c79d70c0114f69f9d449f5b0ac39356&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject">
 </p>
   

- 보안을 고려한 회원가입 절차 & 회원가입 중 희망근무 타임 설정

 <p>
 <img width="400" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ed79aa54-d860-4831-a62d-3829e37812f1/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T012725Z&X-Amz-Expires=86400&X-Amz-Signature=d45d79ec133551ba09a1e0e017d4c97ad325b620c6d5e6823579d17f259349c4&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject">
 </p>

<br>


<br>

### **(2) 스케줄 배정 및 조회**

- 기존의 문제점
    
    기존) 스프레드시트를 사용하여 수동으로 스케줄 배정 → 해당 문서를 기반으로 매니저별 일정 관리 문서 제작
    
     <p>
 <img width="500" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/04ec766b-6ec1-499f-bc6d-e8fe10314161/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T011011Z&X-Amz-Expires=86400&X-Amz-Signature=c63f87cef22ac6a0b39077f20006c63a0558f1732ae8f0a8bd173340dcff2c3e&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject">
 </p>

<br>

▶ **스케줄 배정 기능 서비스 흐름도**

 <img width="700" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/dfcf89bc-d29b-4719-bf89-f6d4dd5cf3bd/%EA%B7%B8%EB%A6%BC3.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T011032Z&X-Amz-Expires=86400&X-Amz-Signature=860cd6c03e4e34e8b110ad9040973c862e70ca8112afbe797496bcd961a0011c&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22%25EA%25B7%25B8%25EB%25A6%25BC3.jpg%22&x-id=GetObject">
 </p>
 <br>

1) 이분매칭 알고리즘을 적용하여 스케줄 자동 배정 가능

      <p>
 <img width="700" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/5269d365-5252-4a77-b7ae-01aff68dd8e2/%EA%B7%BC%EB%AC%B4%EC%8A%A4%EC%BC%80%EC%A4%84%EB%B0%B0%EC%A0%95_UI.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T011115Z&X-Amz-Expires=86400&X-Amz-Signature=9a0e10554f76a3ee006a37a223fe14ce5abc0196899179efb24f1e32cccb24ae&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22%25EA%25B7%25BC%25EB%25AC%25B4%25EC%258A%25A4%25EC%25BC%2580%25EC%25A4%2584%25EB%25B0%25B0%25EC%25A0%2595_UI.png%22&x-id=GetObject">
 </p>
 
 <br>
 
2) 카카오톡 API를 활용하여 스케줄 등록 시 매니저에게 알림 발송

      <p>
 <img width="500" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/f493ade6-ef0a-444c-be14-d51c36fb374e/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T011140Z&X-Amz-Expires=86400&X-Amz-Signature=4493ebf53b3da7acc323625d35fbf2c85f36c90b701071deb0fa3c355290d088&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject">
 </p>
 
 <br>
 
3) Full-calendar 오픈소스 라이브러리를 사용하여 원하는 캘린더 유형으로 조회 가능

      <p>
 <img width="500" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/5f2194a4-8752-428c-8937-c042c364ebee/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T011156Z&X-Amz-Expires=86400&X-Amz-Signature=945e5ab2c045d0245c01499a96a5e69a05395df0cc90fee7edc20805c9a4e45e&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject">
 </p>
       <p>
 <img width="500" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/386df059-9234-4872-b0b5-9893444da264/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T011208Z&X-Amz-Expires=86400&X-Amz-Signature=974dddfbe04f73f8ebd10a2761c12c76d2eae87b254aad9f9381ff88602f12ee&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject">
 </p>
 

<br>
<br>

### (3) 근무 기록 및 진행도를 제공하는 마이페이지

 <img width="700" alt="" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/55afe010-5534-474b-bd9a-4ff0d415cbf2/image01.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T011221Z&X-Amz-Expires=86400&X-Amz-Signature=d1e831a3f7fbdd8bc6ef4745d80ed88c813da9a3a8ad8cd3f85d3d5588f66d21&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22image01.jpg%22&x-id=GetObject">

