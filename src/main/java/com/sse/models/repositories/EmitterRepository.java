package com.sse.models.repositories;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String eventCacheId, Object event);
    Map<String, SseEmitter> findEmitterStartWithCustomerId(String customerId);
    Map<String, Object> findEventCacheStartWithCustomerId(String customerId);
    void deleteById(String emitterId);
    void deleteAllEmitterStartWithCustomerId(String customerId);
    void deleteAllEventCacheStartWithCustomerId(String customerId);
}
