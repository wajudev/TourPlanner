package com.example.tourplanner.business.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventMangerImpl implements EventManager {

    private static EventManager instance;

    public static EventManager getInstance(){
        if (instance == null){
            instance = new EventMangerImpl();
        }
        return instance;
    }
    private final Map<String, List<EventListener>> listeners = new HashMap<>();


    @Override
    public void subscribe(String event, EventListener listener) {
        List<EventListener> events = listeners.getOrDefault(event, new ArrayList<>());
        events.add(listener);
        listeners.put(event, events);
    }

    @Override
    public void unsubscribe(String event, EventListener listener) {
        List<EventListener> events = listeners.getOrDefault(event, new ArrayList<>());
        events.remove(listener);
        listeners.put(event, events);

    }

    @Override
    public void notify(String event, Object data) {
        for (EventListener eventListener : listeners.getOrDefault(event, new ArrayList<>())){
            eventListener.update(event, data);
        }
    }
}
