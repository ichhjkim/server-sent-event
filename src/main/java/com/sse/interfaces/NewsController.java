package com.sse.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RestController
@RequestMapping("/news")
public class NewsController {
    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>(); // Thread Safe
    private final Long TIME_OUT = 60L*1000;
    // method for client sucscription
    @CrossOrigin
    @GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter(TIME_OUT);
        try {
            // 기본 데이터를 날리지 않으면 503에러 발생
            sseEmitter.send(
                    SseEmitter.event()
                    .name("init")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        emitters.add(sseEmitter);

        // sseEmitter가 없으면 에러 발생
        sseEmitter.onCompletion(() -> {
            emitters.remove(sseEmitter);
        });

        // timeout 되면 실행
        sseEmitter.onTimeout(() -> {
            emitters.remove(sseEmitter);
        });
        return sseEmitter;
    }

    // method for dispatching event to all clients
    @PostMapping("/dispatch")
    public void dispatchNews(@RequestBody Map<String, String> article) {
        emitters = (emitters==null) ? new CopyOnWriteArrayList<>(): emitters;
        System.out.println(emitters.toString());

        emitters.forEach(emitter -> {
            try {
                // name
                emitter.send(
                        SseEmitter.event()
                                .name("breakingNews")
                                .data(article)
                );
            } catch (IOException e) {
                e.printStackTrace();
                // 에러 발생하면 emitter에서 제거
                emitters.remove(emitter);
            }
        });
    }
}
