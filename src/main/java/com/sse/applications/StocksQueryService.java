package com.sse.applications;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
public class StocksQueryService {

    public SseEmitter getStockInfoList(String appId, String lastEventId) {
        String id = appId + "_" + System.currentTimeMillis();

        // 클라이언트의 sse 연결 요청에 응답하기 위한 SseEmitter 객체 생성
        // 유효시간 지정으로 시간이 지나면 클라이언트에서 자동으로 재연결 요청함(유효시간 10분)
        SseEmitter sseEmitter = new SseEmitter((long) (10 * 60 * 1000));

        // 연결 직후, 데이터 전송이 없을 시 503 에러 발생. 에러 방지 위한 더미데이터 전송
        sendToClient(sseEmitter, id, "연결되었습니다. " + id + "님");

    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {

        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException e) {

        }
    }

}
