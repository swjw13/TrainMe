# TrainMe
  트레이너 찾기부터 매칭까지 손가락 하나면 끝!
  트레이닝 어플을 만들어 보자
  <br>< 암튼 자세한 설명 >

# TodoList
    1. 트레이너 화면에 나타 날 질문 + 메인화면 만들기
    2. 회원상태 / 비회원 상태를 구분시켜주는 화면 만들어서 연동하기
    3. RealtimeDataBase에 저장 하고 그걸 받아올 객체 형태 지정하기
    4. 요청서 보내기 / 매칭 시키기 로직 짜기
    5. 마지막엔 animation 넣어서 fun하고 cool하고 sexy하게 어플 만들기

# 순서(예상)

    Ask_Trainer_or_User -- Login(User) -- MainActivity(비회원) -- UserMainProfile 
                                                              -- ChatRoom
                                       -- YES_OR_NO           -- MainActivity
                                                              -- Q1(질문)
                                       -- Register
                        -- Login_trainer -- Yes_OR_NO_Trainer -- Q1_Trainer ~  -- MainActivity(트레이너를 위한 Main을 만들어야함)
# adapter
    - 리스트의 아이템들에 listener를 달아주는 걸 adapter 안에서 해 줬는데 설명을 안달았음 
    - 궁금하면 500원
# kt파일들 위치
  D:\android\TrainMePractice\app\src\main\java\com\jw\trainmepractice
