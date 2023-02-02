# openfeign-spring-tutorial

### Environment

- spring-openfeign, wiremock

## Feature

### OpenFeign 활용

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

### WireMock 을 활용한 테스트 코드 구현

![image](https://user-images.githubusercontent.com/55722186/216381219-e641abc6-017f-41e8-b5d0-0614ae156a12.png)

- ErrorDecoder 로 bad request 예외 처리
  - wiremock 으로 test stub 구성
- FallBack 구현 (not yet)
