## **☕ Cafe Menu Management Service**
> **Spring Boot 카페 메뉴 관리 서비스 제작**  

<div align="left">
	<p>🛠 Tools 🛠</p>
</div>
<p align="left">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> 
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white">
</p>
<div align=left>
	<p>🎨 IDE 및 개발 도구 🎨</p>
</div>
<p align="left">
  <img src="https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white" />
	<img src="https://img.shields.io/badge/datagrip-000000?style=flat&logo=datagrip&logoColor=white" />
</p>

## 🙋‍♀️ **프로젝트 소개**
> **작은 로컬 카페의 온라인 주문 시스템**
- **로그인** 및 회원가입 기능을 제공합니다.
- 고객이 **커피 상품**을 온라인에서 주문할 수 있습니다.
- **오후 2시 기준으로** 주문받은 상품을 다음날 일괄 배송합니다.


## 👯 **팀원 소개**

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/cba700"><img src="https://github.com/user-attachments/assets/2c84fe57-d0c8-46ad-bc3e-c145ffe7bcff" width="150px"/></a><br/>
      <a href="https://github.com/cba700"><b>임창인</b></a>
    </td>
    <td align="center">
      <a href="https://github.com/iamjieunkim"><img src="https://avatars.githubusercontent.com/u/83564946?v=4" width="150px"/></a><br/>
      <a href="https://github.com/iamjieunkim"><b>김지은</b></a>
    </td>
    <td align="center">
      <a href="https://github.com/jwh946"><img src="https://avatars.githubusercontent.com/u/141805602?v=4" width="150px"/></a><br/>
      <a href="https://github.com/jwh946"><b>조우현</b></a>
    </td>
    <td align="center">
      <a href="https://github.com/audwls239"><img src="https://avatars.githubusercontent.com/u/77625332?v=4" width="150px"/></a><br/>
      <a href="https://github.com/audwls239"><b>김명진</b></a>
    </td>
    <td align="center">
      <a href="https://github.com/urinaner"><img src="https://avatars.githubusercontent.com/u/27186972?v=4" width="150px"/></a><br/>
      <a href="https://github.com/urinaner"><b>장영재</b></a>
    </td>
  </tr>
  <tr>
    <td align="center">Backend</td>
    <td align="center">Backend</td>
    <td align="center">Backend</td>
    <td align="center">Backend</td>
    <td align="center">Backend</td>
  </tr>
</table>

---


### ✅ 브랜치 타입 정리

| 타입     | 설명                                         |
|----------|----------------------------------------------|
| feat   | 새로운 기능 개발 (기능 추가, API 구현 등)     |
| fix    | 버그 수정 (오류 해결, 예외 처리 등)           |
| refactor | 리팩토링 (기능 변화 없이 코드 구조 개선)     |
| docs   | 문서 작업 (README 수정, API 명세 등)          |
| test   | 테스트 코드 작성                              |
| chore  | 설정, 빌드, 패키지 등 기타 작업               |


### ✏️ 브랜치 네이밍 예시

| 작업 내용         | 브랜치명                  |
|------------------|---------------------------|
| 로그인 API 구현   | feat/login-api          |
| 주문 버그 수정    | fix/order-total-bug     |
| JPA 설정 변경     | chore/jpa-config        |


### 📘 브랜치 사용 규칙

- 모든 작업은 **develop에서 브랜치 생성**
- 기능 단위로 브랜치 생성, 기능 완료 후 Pull Request
- 병합 전 반드시 팀원 리뷰 & 테스트
- 병합 후 브랜치 삭제 (Delete branch 클릭)

> ✅ **브랜치명은 영문 소문자, 하이픈(-)으로 구분하며 의미 있게 작성합니다.**



---

### ✅ 커밋 타입 목록

| 타입 | 설명 |
|------|------|
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 (README 등) |
| `style` | 코드 포맷팅, 세미콜론 누락 등 (기능 변경 없음) |
| `refactor` | 코드 리팩토링 (기능 변화 없음) |
| `test` | 테스트 코드 추가 또는 수정 |
| `chore` | 빌드 설정, 패키지 매니저 설정 등 기타 작업 |



### ✏️ 작성 예시

| 커밋 메시지 | 의미 |
|--------------|------|
| `feat: 로그인 API 구현` | 로그인 기능 추가 |
| `fix: 회원가입 시 이메일 중복 오류 수정` | 기능 오류 수정 |
| `docs: ERD 다이어그램 추가` | 문서 변경 |
| `refactor: OrderService 로직 정리` | 코드 구조 개선 |
| `chore: .gitignore에 .env 추가` | 기타 설정 작업 |



### 📘 커밋 컨벤션 요약

- 영어/한글 둘 다 OK (단, 팀원끼리 통일)
- 메시지는 **짧고 명확하게!**
- 하나의 커밋은 **하나의 목적만 가지도록!**

> 🚨 예외 없이 모두 이 컨벤션을 따릅니다.



---
# 🛠️ Tech
### ERD
[📊 ERD](https://drive.google.com/file/d/11kaZTF6Gn4xA4PGaGT-ekl99OerANUd2/view)
<br/>
<br/>

### API 명세서
[📝 API 명세서](https://docs.google.com/spreadsheets/d/1fS4KQHj3Z7prCQ4IoYbMheJlYhEeVNMuyXf_ZIkh-o4/edit?gid=0#gid=0)
<br/>
<br/>
