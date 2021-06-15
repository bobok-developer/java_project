# 자바를 이용한 피시방 관리 프로그램 (미니 팀 프로젝트)
`21. 02. 09. - 03. 05.`
<br>

## ✨ ppt 설명
https://www.slideshare.net/secret/v7KqRVh4FdmnOX
- 선결제 후 이용 가능한 시스템입니다.
- 회원/비회원에 따라 요금에 차이가 있고, 남은 시간 축적 가능 여부가 달라집니다.
<br>


## 🎈 기능 설명- 이용자 (맡은 부분- 로그인, 이용상태 관리)

### 1. 회원가입
- 유효성 검증을 거친 후 회원가입을 할 수 있습니다.
<br>

![image](https://user-images.githubusercontent.com/85592459/121303634-4bad5600-c936-11eb-9519-42063cfd647d.png)

---
<br>

### 2. 로그인

#### 2-1. 회원 로그인

![image](https://user-images.githubusercontent.com/85592459/121308425-02f89b80-c93c-11eb-98ad-7729dbe9e9f3.png)
<br>

#### 2-2. 비회원 로그인

<br>

![image](https://user-images.githubusercontent.com/85592459/121307198-b791bd80-c93a-11eb-8e28-c737ee1104af.png)
<br>

✔ 비회원은 전화번호를 입력한 후 이용 가능한 일회성 로그인입니다.

---
<br>

### 3. 좌석선택
![image](https://user-images.githubusercontent.com/85592459/121309084-b82b5380-c93c-11eb-96da-b429afc45315.png)
<br>

✔ 좌석에 대한 기능은 선택, 변경이 있습니다. 간단하게 이용중인지 빈 좌석인지만을 나타냅니다.

---
<br>

### 4. 요금결제
![image](https://user-images.githubusercontent.com/85592459/121312253-2a516780-c940-11eb-9b9c-7beceb5d8109.png)
<br>

✔ 회원 요금(위)과 비회원 요금(아래)에는 차이가 있습니다.
![image](https://user-images.githubusercontent.com/85592459/121973540-79702000-cdb8-11eb-9354-381bb31d14ea.png)

---
<br>

### 5. 음식구매
![image](https://user-images.githubusercontent.com/85592459/121974425-4c247180-cdba-11eb-9816-00b0bc9b34ee.png)
<br>

✔ 음식을 구매할 수 있으며 관리자 메뉴의 매출/재고 관리에서 이용자가 구매한 내역을 확인할수 있습니다.

---
<br>

### 6. 이용상태관리
![image](https://user-images.githubusercontent.com/85592459/121975275-33b55680-cdbc-11eb-9aa4-0baed4f62787.png)
<br>

✔ 이전에 사용하고 남은 시간이 있다면 결제한 요금의 시간과 합쳐서 남은 시간에 표시 됩니다. (회원의 경우)
✔ 시연 발표를 하기 위해 표시는 '분'으로 되어있지만 실제 흐르는 시간은 '초'입니다.
✔ 남은 시간을 초과하여 이용하는 경우 추가된 시간만큼의 요금 결제를 해야 하며, 초과된 시간인 음수값의 남은 시간이 0 이상이 될 때까지 추가 요금 결제 화면이 계속 보입니다. (아래 이미지)
<br>

![image](https://user-images.githubusercontent.com/85592459/121975728-4d0ad280-cdbd-11eb-8e6b-d6c19381d499.png)
<br>

---
<br>

### 7. 정보수정

- 이용자와 관리자는 회원 정보를 수정할 수 있습니다.
<br>

![](https://images.velog.io/images/levinni/post/f083e5d5-a60c-4a14-a4c7-15ea932c9266/image.png)
<br>

✔ 이용자는 본인의 비밀번호와 전화번호를 수정할 수 있습니다.
<br>

---
<br>

## 🎈 기능 설명- 관리자

### 1. 회원관리
![](https://images.velog.io/images/levinni/post/dc4e1f4a-ea9f-4898-bdf4-3639ee1e5213/image.png)
<br>

✔ 관리자는 회원의 정보를 수정하거나 삭제할 수 있으며 회원의 남은시간도 수정할 수 있습니다.

---
<br>

### 2. 재고관리
![](https://images.velog.io/images/levinni/post/d851f524-b7b5-4cdc-b7f2-cba7b7c2664d/image.png)
<br>

✔ 음식구매에 이용되는 상품들을 주문할 수 있고, 구매내역이 기록되어 매출관리에서 확인할 수 있습니다.

---
<br>

### 3. 매출관리
![](https://images.velog.io/images/levinni/post/c7b01faa-a301-4b09-9e2d-f1e501bb23e2/image.png)

✔ 이용자가 결제한 내역들은 `판매내역`에, 관리자가 재고관리에서 주문한 내역은 `구매내역`에 기록되며, 각각 매출과 매입을 나타내고 순이익인 영업이익까지 확인할 수 있습니다.

