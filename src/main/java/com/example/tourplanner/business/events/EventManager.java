package com.example.tourplanner.business.events;

public interface EventManager {
    void subscribe(String event, EventListener listener);
    void unsubscribe(String event, EventListener listener);
    void notify(String event, Object data);
}
