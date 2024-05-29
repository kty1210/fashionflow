

<a name="readme-top"></a>

🛫 스프링 부트 의류 중고 거래 팀 프로젝트
==

<br/>

<!-- Table of Contents -->
## :bookmark_tabs: 목차
- [:clock12: 소개](#소개)
- [:clock1: 일정](#일정)
- [:clock2: 요구사항 명세서](#요구사항-명세서)
- [:clock3: 시퀀스 다이어그램](#시퀀스-다이어그램)
- [:clock4: 와이어 프레임](#와이어-프레임)
- [:clock5: 주요 라이브러리](#주요-라이브러리)
- [:clock6: 클래스 다이어그램](#클래스-다이어그램)
- [:clock7: 엔티티 관계도](#엔티티-관계도)
- [:clock8: 이슈](#이슈)
   - i. [문제](#문제)
   - ii. [원인](#원인)
   - iii. [해결방법](#해결방법)
- [:clock9: 서버 구축](#서버-구축)
- [:clock10: 기능 시연](#기능-시연)
- [:clock11: 주요 기술스택](#주요-기술스택)
- [:clock12: 마치며](#마치며)
<br/>

<br/>
<br/>

<!-- ABOUT THE PROJECT -->
## :clock12: <a name="소개"></a> 소개
<br/>
<br/>


C2C를 기반으로 진행되는 상거래 사이트

<br/>

API를 이용한 기능 구현

<br/>
슈"></a> 이슈

### i.  <a name="문제"></a>  문제

<br/>
<br/>


![image](https://github.com/kty1210/fashionflow/assets/154123644/9416b8ae-2248-44f7-b1eb-13176001e981)

<br/>


UserDetailsService의 loadUserByUsername메소드를 이용하여 사용자 인증 필터 오버라이딩을 정상적으로했음에도 <br/>
BLACK 등급의 회원 차단시 기본 로그인 실패 메세지 출력

<br/>
<br/>
<br/>


### ii. <a name="원인"></a> 원인

<br/>
<br/>


![image](https://github.com/kty1210/fashionflow/assets/154123644/d9053305-161a-4ca8-b37d-3e492cab00b7)

<br/>


AuthenticationException를 상속받는 예외가 발생하면, 스프링 시큐리티는 이를 InternalAuthenticationServiceException 자동적으로 한번 더 래핑해서 처리

<br/>
<br/>
<br/>


### iii. <a name="해결방법"></a> 해결방법

<br/>
<br/>

![image](https://github.com/kty1210/fashionflow/assets/154123644/07830bf9-8ca3-4ac6-9df2-82008740adeb)

<br/>

InternalAuthenticationServiceException의 원인이 되는 예외로 특정시켜 메세지가 출력

<br/>
<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<br/>
<br/>
<br/>
<br/>



## :clock9:  <a name="서버-구축"></a> 서버 구축


<br/>
<br/>

![ec2](https://github.com/kty1210/fashionflow/assets/154123644/c0988f5d-2e51-4085-b92e-7aced4134df3)
![rds](https://github.com/kty1210/fashionflow/assets/154123644/00723a88-5b34-458c-9b3b-50a43c965439)



<br/>
<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<br/>
<br/>
<br/>
<br/>



## :clock10: <a name="기능-시연"></a>  기능 시연

<br/>

<br/> 

* 담당부분

[![Video Label](http://img.youtube.com/vi/yRVWUnFJsqE/0.jpg)](https://youtu.be/yRVWUnFJsqE)

https://youtu.be/yRVWUnFJsqE?si=EqvX258-7EbXQsi7
<br/>
<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<br/>
<br/>
<br/>
<br/>



<!-- 기술스택 -->
## :clock11:  <a name="주요-기술스택"></a> 주요 기술스택

<br/>
<br/>

* <img src="https://img.shields.io/badge/Java-FF0000?style=for-the-badge&logo=Java&logoColor=white">
* <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
* <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
* <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
* <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jqueryt&logoColor=white">
* <img src="https://img.shields.io/badge/jpa-E53525?style=for-the-badge&logo=jpa&logoColor=white">
* <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
* <img src="https://img.shields.io/badge/openjdk-000000?style=for-the-badge&logo=openjdk&logoColor=white">
* <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
* <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
* <img src="https://img.shields.io/badge/gitkraken-179287?style=for-the-badge&logo=gitkraken&logoColor=white">



<br/>
<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<br/>


<br/>
<br/>
<br/>


## 🛬  <a name="마치며"></a> 마치며...


<br/>
<br/>

생각해봐야할 점

<br/>

* 사이트가 볼륨이 커지게 될때 서버 구축시 데이터 처리를 어떻게 해야되는가?
* Git merge시 공통 부분 처리하게 될때 좀 더 효과적인 merge방법은 어떤것이 있는가?

<br/>
<br/>

긍정적인 측면


<br/>

* API 사용법 
* AWS의 EC2를 이용한 서버 구축과 RDS를 이용한 관계형데이터베이스 구축
* 팀 단위 프로젝트를 통해 코드 이해도 향상과 팀원과의 귀중한 커뮤니케이션 경험



<br/>
<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

