# E-Commerce

E-Commerce 를 개발하며 공부하는 저장소입니다.

## 왜 만드나요?

- 습관적인 코딩습관을 고치고 이유를 생각하며 코드를 작성하는 연습을 하기 위함
- 주니어개발자면 쇼핑몰 하나는 만들어봐야지 라는 생각

## 개발 스택

- Java 8
- SpringBoot 2.2.6
- Spring Data JPA
- H2, MySQL
- thymeleaf

### 기능 구현 목록 (프로젝트 관리와 이슈 추가 하면서 업데이트)

- 상품
    - 상품 코드, 카테고리, 상품명, 기본 가격, 재고 수, 기본 배송비
    - 특수한 상품이 있을 수 있음(이벤트 물품이라거나, 1개만 구매할 수 있음)
- 검색
    - 상품 검색 가능
    - 카테고리 별 검색 가능
    - 정렬 기능 제공(가격 별, 이름 순, 무료배송만)
- 회원
    - 회원 가입
    - kakao, naver 아이디로 로그인
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
    
### 중점적으로 생각할 사항

#### Front

- 메인 페이지에 띄울 항목 고려
- 각 페이지 구성
- 프론트 프레임 워크의 장점 고려

#### Back

- 테스트 주도 개발
- 동시성 제어

#### Infra

- 배포 환경 구성