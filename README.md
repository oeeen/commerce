# E-Commerce

E-Commerce 를 개발하며 공부하는 저장소입니다.

## 왜 만드나요?

- 습관적인 코딩습관을 고치고 이유를 생각하며 코드를 작성하는 연습을 하기 위함
- 주니어개발자면 쇼핑몰 하나는 만들어봐야지 라는 생각
- 새로운 기술을 적용 해보고 공부하기 위함

## 개발 스택

- Java 8
- SpringBoot 2.2.6
- Spring Data JPA
- Spring Security
- H2, MySQL
- thymeleaf

## 현재 가장 중요한 이슈 (2020/06/13)

- Logout Test, 인증 상태에 따른 html 값 변경 확인 테스트
- 사용자 권한 계층적 관리
- 사용자 권한에 따른 api call 제한
- WithMockCustomUser Annotation 적용 방법?
    - TestSecurityContextHolder 의 TestSecurityContext 내부의 Authentication 까지 들어오는 것은 확인했음
    - 그 다음 request가 anonymous로 빠지는 이유가 뭔지 모르겠음. FilterInterceptor에서 SecurityContext 확인해보면 Authentication이 null임

### 기능 구현 목록 (프로젝트 관리와 이슈 추가 하면서 업데이트)

#### Back

- 상품
    - 상품 코드, 카테고리, 상품명, 기본 가격, 재고 수, 기본 배송비
    - 특수한 상품이 있을 수 있음(이벤트 물품이라거나, 1개만 구매할 수 있음) - 상품 타입 추가
- 상품 카테고리
    - 식품 - 신선 식품 - 쌀, 식품 - 신선 식품 - 김치 등과 같이 3단계로 구성
    - Database 에 두기로 계획
    - 카테고리 추가, 수정, 삭제는 ADMIN 권한으로만 가능(현재 웹으로는 불가능 - 어드민 페이지 없음, 테스트 코드로**만** 확인)
    - 카테고리 조회는 Anonymous도 가능
- 검색
    - 상품 검색 가능
    - 카테고리 별 검색 가능
    - 정렬 기능 제공(가격 별, 이름 순, 무료배송만)
- 회원
    - 회원가입 기능
        - [x] 카카오 아이디로 로그인
        - [x] 네이버 아이디로 로그인
        - [x] 그냥 이메일로 회원가입
            - 이메일, 비밀번호, 이름 등
        - [x] 회원 가입시 필요한 정보
            - 실명
            - 닉네임
            - 이메일
            - 비밀번호
            - 추후 추가
    - 로그인
        - [x] 단순 로그인 기능
        - [ ] 로그인 유지 기능
    - [x] 로그아웃 기능
    - 필터
        - [ ] 세션 제어 필터
        - [ ] csrf 필터
    - [ ] 인증 거부 처리
    - [ ] 사용자 권한 계층 처리
        - ROLE_ADMIN > ROLE_SELLER > ROLE_BUYER
    - [ ] 회원 탈퇴 기능
        - 탈퇴는 flag 형식으로 할 수도 있지만, 일단 실제로 DB에서 제거하는 방식으로 구현
    - 내 정보 조회 기능
    - [x] 관리자(ROLE_ADMIN) 권한으로 전체 회원 정보 조회 가능
- 판매자(사용자)
    - 단순 일반 사용자에서 판매자로 전환 기능
    - 일반 사용자와 기업 사용자로 나뉨
    - 판매 상품을 등록 가능
    - 배송 상태 변경 가능(실제로는 판매자가 하지 않지만, 구현 상 판매자 쪽으로 둠)
- 구매자(사용자)
    - 상품을 구매할 수 있음
    - 상품을 찜할 수 있음
    - 상품을 장바구니에 담을 수 있음
    - 구매 완료 시 해당 사용자에게 구매 목록이 들어간다.
    - 배송 정보는 **일단** 단순 적용(일괄 배송준비중)
    - 배송 완료가 되면 구매확정 가능
    - (추후) 최근 본 상품 목록 저장 해놓기 - 일반 커머스 사이트는 이 정보를 많이 활용하더라(쿠키는 아니고 회원정보에 귀속 되는 듯?)
- 장바구니
    - 상품을 담을 수 있음
    - 상품 삭제, 개수 수정 가능
- 할인 및 쿠폰
    - 특정 카테고리 특정 금액 이상 시 적용 가능
    - 중복 가능한 쿠폰도 있을 수 있다.
- 포인트
    - 상품 구매 확정 시 포인트가 적립된다.
    - 포인트는 기본적으로(이벤트가 아닐 시) 최종 결제 금액의 1%가 적립된다.
- 결제
    - 특정 조건에만 구매가 가능할 수 있다.(특정 금액 이상 구매시)
    - 상품 기본 가격 + 기본 배송비
    - 할인 및 쿠폰 적용, 배송비 할인 적용
    - 포인트로 구매 가능
    - 결제 수단 선택 가능
- 구매
    - 결제 이후 교환, 반품 신청 가능
    - 일부 교환, 반품 이후 포인트 적립 및 할인 쿠폰 적용 여부 확인
- 리뷰
    - 구매 확정 이후 7일간 리뷰 작성 가능
    - (추후) 사진 첨부 가능
    - 리뷰 작성 시 50 포인트 지급
    
#### Front

- 메인 페이지
    - 로그인 전
        - 로그인, 회원가입, 고객센터 버튼
    - 로그인 후
        - 로그아웃, 고객센터 버튼
    - 카테고리
    - Logo
    - 검색 영역
    - 마이 페이지 버튼
    - 장바구니 버튼
    - 광고 영역
    - 일부 상품 목록 영역 (이미지) 
- 회원 관련
    - 회원가입 페이지
    - 로그인 페이지
    - 마이 페이지
        - 내 정보 조회, 변경 가능
        - 회원 탈퇴 가능
        - 내 구매 정보 조회 가능
    - 회원 조회 페이지(ROLE_ADMIN)
- 상품 관련
    - 상품 목록 페이지 (검색 결과/카테고리 별 조회)
    - 장바구니 페이지
    - 상품 상세 페이지 (product_detail)
        - 리뷰 영역
- 결제 관련
    - 결제 페이지
        - 포인트 사용 영역
        - 할인 및 쿠폰 적용 영역
        
### 중점적으로 생각할 사항

#### Front

- 메인 페이지에 띄울 항목 고려
- 각 페이지 구성
- 프론트 프레임 워크의 장점 고려
    - 바닐라 자바스크립트 활용
    - 전반적인 기능 구현 마무리 후 Vue.js/React.js 로 변경 예정

#### Back

- 테스트 주도 개발
- 동시성 제어
- 새로운 기술 적용 해볼 것(Spring Security, Elastic Stack, k8s 등)

#### Infra

- 배포 환경 구성
- Docker image로 만들어서 배포
- Blue-Green 배포로 결정
- 배포 흐름
    - Github pull request merge
    - Github actions 에서 jar file 실행 이미지를 Dockerhub push
    - AWS EC2위 Docker - jenkins 에서 dockerhub 이미지 다운로드
    - 이미지 다운로드 후 해당 컨테이너 up(포트 번호 변경)
    - 애플리케이션 서버 앞단의 NGINX에서 서버 포트 변경
    - 기존 애플리케이션 컨테이너 down
- 그러면 새로운 버전의 컨테이너가 문제가 생기면 roll-back은 어떻게 할까?
    - 고민 중..
