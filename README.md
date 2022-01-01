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
  "result": [
    {
      "date": "2021.08.13",
      "time": "11:12",
      "state": "배달준비"
    },
    {
      "date": "2021.08.13",
      "time": "12:12",
      "state": "미배달"
    }
  ]
}
```
