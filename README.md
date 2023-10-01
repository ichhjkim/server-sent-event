# Server-Sent-Events(SSE)

## Server-Sent-Events란?

서버에서 클라이언트로 단방향으로 데이터를 전달할 수 있는 단방향 통신 채널입니다. 
Polling과 같이 클라이언트가 주기적으로 http요청을 보낼 필요 없이 처음 http 연결을 맺고 나면 서버에서 클라이언트로 실시간, 지속적으로 데이터를 보낼 수 있어 리소스 낭비가 적습니다.

단, 클라이언트의 요청없이 서버에서 클라이언트에게 단방향 통신을 하기 때문에 모든 실시간 통신이 필요한 기능에는 사용하지 못한다는 단점이 있습니다.
그래서 클라이언트가 서버로 실시간으로 요청을 보내야하는 채팅 프로그램에서는 사용하기 적절하지 않고, 주식시세/실시간 검색 목록 등 실시간 정보를 전달할 때 사용하기 적절합니다.

## 특징
- JavaScript는 SSE를 쉽게 사용할 수 있도록 EventSource API를 제공하고 있습니다.
- 별도의 프로토콜을 사용하지 않고 HTTP 프로토콜만으로 사용이 가능하기 때문에 훨씬 가볍습니다.
- 최대 동시 접속 수는 HTTP/1.1는 브라우저 당 6개, HTTP/2는 100개까지 가능합니다.
- IE를 제외한 브라우저에서 지원됩니다.
- 이벤트 데이터는 UTF-8 인코딩된 문자열만 지원됩니다.
- 클라이언트에서 페이지를 닫아도 서버에서 감지하기가 어렵다는 단점이 있습니다.

## [Client] Javascript EventSource API를 통한 SSE 구현

```javascript
const eventSource = new EventSource(url);

eventSource.onopen = () => {
  // 연결 하면 할 일
};

eventSource.onmessage = async (e) => {
  const res = await e.data;
  const parsedRes = JSON.parse(res);

  // 데이터를 받아온 후 할 일
  console.log(parsedRes);
};

// 에러 발생 시 할 일
eventSource.onerror = (e: any) => {
  
  eventSource.close(); // sse 종료

  if (e.error) {
    // 에러 발생 시 할 일
    console.log('error 발생')
  }

  if (e.target.readyState === EventSource.CLOSED) {
    // 종료 시 할 일
    console.log('closed');
  }
};
```
