package com.sse.infrastructers;

import com.sse.models.repositories.EmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {
    //스레드 안전(thread-safe)한 맵
    //동시에 여러 스레드가 접근하더라도 안전하게 데이터를 조작할 수 있도록 보장
    //이 맵을 사용하여 동시성 문제를 해결하고, 맵에 데이터 저장 / 조회가 가능
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) { // emitter를 저장
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) { // 이벤트를 저장
        eventCache.put(eventCacheId, event);
    }

    @Override
    public Map<String, SseEmitter> findEmitterStartWithCustomerId(String customerId) { // 해당 회원과 관련된 모든 이벤트를 찾음
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(customerId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findEventCacheStartWithCustomerId(String customerId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(customerId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllEmitterStartWithCustomerId(String customerId) { // 해당 회원과 관련된 모든 emitter를 지움
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(customerId)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithCustomerId(String customerId) { // 해당 회원과 관련된 모든 이벤트를 지움
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(customerId)) {
                        eventCache.remove(key);
                    }
                }
        );
    }
}
