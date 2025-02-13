# [CH3 일정 관리 앱 Develop] 📝

## 📖 과제 소개
- **Lv1. 일정 CRUD**
  - 작성일, 수정일 필드는 JPA Auditing 활용
- **Lv2. 유저 CRUD**
  - 작성일, 수정일 필드는 JPA Auditing 활용
  - 일정과 N:1 단방향 연관관계
- **Lv3~Lv4. 회원가입 및 로그인(인증)**
  - Session을 활용해 로그인 기능 구현
  - Servlet Filter 적용하여 인증 처리
- **Lv5. 예외 처리**
  - Bean Validation, @ExceptionHandler 활용
- **Lv6. 비밀번호 암호**
  - BCrypt 활용하여 비밀번호 해싱 및 검증
- **Lv7. 댓글 CRUD**
  - 작성일, 수정일 필드는 JPA Auditing을 활용
  - 회원과 N:1 단방향 연관관계, 일정과 N:1 양방향 연관관계
- **Lv8. 일정 페이징 조회**
  - Spring Data JPA의 Pageable과 Page 인터페이스 활용하여 페이지네이션 구현

---
## 📋 ERD 설계
#### 📁 **[Lv1]**
<img width="355" alt="image" src="https://github.com/user-attachments/assets/f315563b-aeb2-4c19-ad25-8580ffe3d3ce" />

#### 📁 **[Lv2~Lv6]**
<img width="355" alt="image" src="https://github.com/user-attachments/assets/97a13c92-240d-48fc-8e73-400b711895cb" />

#### 📁 **[Lv7~Lv8]**
<img width="428" alt="image" src="https://github.com/user-attachments/assets/4534f798-a039-47cd-8a4a-9b3e2da88dd8" />

---
## 📚 API 명세서

### Postman
#### 📁 **[Lv1]**
[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/35384501/2sAYX8HgNB)

#### 📁 **[~Lv2]**
[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/35384501/2sAYX8JM6k)

#### 📁 **[~Lv8]**
[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/35384501/2sAYXBHfD2)

---
## 🧩 트러블슈팅
#### 트러블슈팅 기록 페이지
<a href="https://velog.io/@yyrkk/TIL-CH3-TROUBLESHOOTING2" target="_blank">
<img src="https://img.shields.io/badge/일정 관리 Develop 트러블슈팅-193440.svg?style=for-the-badge"/>
</a>

---
## ⚙️ 기술 스택
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
