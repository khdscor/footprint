# Footprint
Footprint는 지도 위에 글을 남기고 개인이, 그룹과, 혹은 모든 사람들과 공유하는 SNS/커뮤니티 서비스입니다. 21년 서울과학기술대학교 캡스톤디자인으로 진행한 프로젝트를 재구성한 것으로 처음부터 다시 구현하면서 리펙토링하였습니다.  이전에 사용한 JPA 대신 Mybatis를 사용하여 영속성 컨텍스트를 사용하지 않는 순수한 쿼리를 이해하고자 하였습니다. 


# Overview
현재 아키텍처 구조는 레어어드 아키텍처(DDD 방식의 폴더 구조를 차용했을 뿐, 레이어드 아키텍처를 기반으로 구현)로 설계하였습니다.
서비스 레이어는 인터페이스를 구현한 구현체들과 공통 메서드 사용을 위한 추상클래스를 통해 구현하였습니다. 

Controller - Service - Repository 순으로 의존성을 가집니다.

레이어간 데이터 이동은 Dto 혹은 일반 필드들로 전달하였습니다.

Dto 명칭은 프론트 단에서 Controller로 데이터를 넘길 때는 'Request'로,

Controller에서 Service로 넘길 때는 'Command'로,

Service에서 Repository로 넘길 때는 'Dto' 혹은 Entity로,
프론트단으로 넘길 때는 'Response' 명칭으로 정하였습니다.
<br>
<br>
<br>
진행일지 및 상세 내용은 아래의 노션을 확인하길 바랍니다.
<br><br><br>

### 노션 주소: 
https://pickled-slip-03d.notion.site/0de20f6d1da04d8485df5bec9382fc52

### 기술 스택:
Java, Springboot, Mybatis, MYSQL, Redis, React, Docker, JWT, AWS(EC2, RDS)

### 도메인 주소: 
https://korea.yourfootprint.shop
<br><br>
<br><br>
# 시연 사진
![사진 짜집기본](https://github.com/khdscor/footprint/assets/45135492/e3a89c46-699d-4017-b605-ed7c3b0e4018)


<br><br>
# ERD
![image](https://github.com/khdscor/footprint/assets/45135492/539a3179-18a5-4a38-87ce-ea2d149f4c56)
<br><br>
<br><br>
# 기능 상세 설명 
서비스의 주된 기능은 지도 위에 글을 작성, 공유하는 것입니다.

글은 Private(자신만 볼 수 있도록), Grouped(자신이 선택한 그룹만 볼 수 있도록), Public(모든 사람들이 볼 수 있도록) 등 MapType로 구분된 3가지의 접근 권한을 가집니다. 3가지 권한 모두 가질 수 있고 한 가지 권한만 가질 수도 있습니다.

1. **member**
    - 회원 가입, 회원 탈퇴, 로그인
    - 마이페이지 조회
    - 회원 이미지 URL 조회
2. **article**
    - 게시글 생성, 수정, 삭제
    - Public, Private, Grouped 별 지도 상의 게시글 리스트 조회(위도, 경도와 Zoom(지도 확대, 축소 범위) 값에 따라 게시글 리스트 범위 지정)
    - 게시글 상세페이지 조회
3. **article_like**
    - 게시글 좋아요 추가, 삭제
4. **group**
    - 그룹 생성, 가입, 탈퇴
    - 그룹원 추방
    - 그룹 이름 조회, 수정
    - 그룹 즐겨찾기 등록, 해제
    - 내 그룹 리스트 조회
    - 내 즐겨찾기 그룹 리스트 조회
    - 그룹 상세페이지 조회
5. **comment**
    - 댓글 생성, 수정, 삭제
    - 커서를 통한 페이지 별 댓글 리스트 조회
6. **comment_like**
    - 댓글 좋아요 추가, 삭제

PostMan을 통해 API 문서를 작성하였습니다. 

자세한 내용은 아래의 문서를 참고해 주시기 바랍니다.

[**REST API 문서**](https://documenter.getpostman.com/view/14960809/2s9YyzbHLC)

