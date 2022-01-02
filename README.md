# search-post-back
우편찾기 back

api 명세


http://search-post.herokuapp.com/swagger-ui.html#/post-api-controller


![swagger1](./images/swagger-post-man1.PNG)
tracking-number : 운송장 번호

![swagger2](./images/swagger-post-man2.PNG)

```
반환값 json 형식
{
  "response": [
    {
      "result": [
        {
          "date": "2021.08.04",
          "time": "19:25",
          "position": "서울광진우체국",
          "state": "접수"
        },
        {
          "date": "2021.08.04",
          "time": "19:48",
          "position": "서울광진우체국",
          "state": "발송"
        },
        {
          "date": "2021.08.04",
          "time": "19:50",
          "position": "동서울물류센터",
          "state": "도착"
        },
        {
          "date": "2021.08.04",
          "time": "20:59",
          "position": "동서울물류센터",
          "state": "발송"
        },
        {
          "date": "2021.08.04",
          "time": "23:50",
          "position": "중부권광역우편물류센터",
          "state": "도착"
        },
        {
          "date": "2021.08.05",
          "time": "02:42",
          "position": "중부권광역우편물류센터",
          "state": "발송"
        },
        {
          "date": "2021.08.05",
          "time": "03:59",
          "position": "천안우편집중국",
          "state": "도착"
        },
        {
          "date": "2021.08.05",
          "time": "06:25",
          "position": "천안우편집중국",
          "state": "발송"
        },
        {
          "date": "2021.08.05",
          "time": "06:26",
          "position": "천안우체국",
          "state": "도착"
        },
        {
          "date": "2021.08.05",
          "time": "07:47",
          "position": "천안우체국",
          "state": "배달준비"
        },
        {
          "date": "2021.08.05",
          "time": "09:57",
          "position": "천안우체국",
          "state": "배달완료"
        }
      ],
      "companyName": "epost"
    }
  ]
}
```
