package com.sse.interfaces.rest;

import com.sse.applications.StocksQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class StockController {
    private final StocksQueryService stocksQueryService;

    public StockController(StocksQueryService stocksQueryService) {
        this.stocksQueryService = stocksQueryService;
    }

    @GetMapping(value = "/stocks", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> subscribe(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                                @RequestParam String appId) {
        // Last-Event-ID 헤더는 항상 담겨있는 것은 아니고,
        //네트워크 연결 오류 등의 이유로 연결이 끊어졌을 때 클라이언트에 도달하지 못한 알림이 있을 때
        //이를 이용하여 유실된 데이터를 다시 보내줄 수 있다.
        SseEmitter sseEmitter = stocksQueryService.getStockInfoList(appId, lastEventId);
        return ResponseEntity.ok(sseEmitter);
    }

}
