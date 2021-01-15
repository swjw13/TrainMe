package com.jw.trainmepractice

// 트레이너 객체
// 추후에 어떤 형식으로 바꿀지 DB에 넣는 방식에 따라 달라질 수 있을 거 같다

class Trainer(
    var name: String? = null,
    var sex: String? = null,
    var age: String? = null,
    var userid: String? = null
)