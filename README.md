# openfeign-spring-tutorial

[Spring Cloud OpenFeign + WireMock Test](https://hyune-c.tistory.com/57)

### Environment

- spring-openfeign, wiremock

## Feature

### OpenFeign

```http request
GET http://localhost:8080/posts/3

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 02 Feb 2023 16:21:33 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "userId": 1,
  "id": 3,
  "title": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
  "body": "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
}
```

- 애플리케이션으로 요청이 오면 fake api 를 호출
    - https://jsonplaceholder.typicode.com
- ErrorDecoder
    - 400, 500
- Retryer
- FallBack (not yet)

### 테스트 코드 with WireMock

![image](https://user-images.githubusercontent.com/55722186/216709951-f2675874-7141-44db-9b77-899591ce1cbb.png)

- wiremock 으로 test stub 구성
- 200 외 응답코드 대응
- retryer 테스트 - stub 서버 강제 중지
