package com.Yegorisa.clusterws;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Egor on 01.10.2017.
 */
public class Emitter {
    public interface Listener {
        void call(String name, Object data);
    }

    private ConcurrentHashMap<String, Listener> mEvents;

    public Emitter() {
        mEvents = new ConcurrentHashMap<>();
    }

    public void on(String event, Listener fn) {
        if (mEvents.containsKey(event)) {
            mEvents.remove(event);
        }
        mEvents.put(event, fn);
    }

    public void emit(String event, Object object) {
        Listener listener = mEvents.get(event);
        if (listener != null) {
            listener.call(event, object);
        }
    }

    public void removeAllEvents() {
        mEvents = new ConcurrentHashMap<>();
    }
}