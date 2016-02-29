package com.example.gpy.whiteboard.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.gpy.whiteboard.eventbus.inner.ClassSpecRegistry;
import com.example.gpy.whiteboard.eventbus.inner.base.MethodSpec;
import com.example.gpy.whiteboard.eventbus.inner.bus.ReceiveEventsAnn;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {

    private static final String TAG = "EventBus";
    private static final String ALL = "_all_";

    private static final ConcurrentHashMap<String, ConcurrentHashMap<EventReceiver<Object>, Boolean>> eventNameToReceivers
            = new ConcurrentHashMap<String, ConcurrentHashMap<EventReceiver<Object>, Boolean>>();
    private static final ConcurrentHashMap<String, Object>                                            stickyEvents
            = new ConcurrentHashMap<String, Object>();

    public static void postEvent(String name) {
        postEvent(name, null);
    }

    public static void postEvent(String name, Object data) {
        runOnUiThread(new PostEventRunnable(name, data));
    }

    public static void postEventSticky(String name) {
        postEventSticky(name, new Object());
    }

    public static void postEventSticky(String name, Object data) {
        stickyEvents.put(name, data);
        postEvent(name, data);
    }

    public static void clearStickyEvents(String... eventNames) {
        boolean allEvents = (eventNames.length == 0);
        if (allEvents) {
            stickyEvents.clear();
        } else {
            HashSet<String> nameSet = new HashSet<String>(
                    Arrays.asList(eventNames));
            for (String eventName : stickyEvents.keySet()) {
                if (nameSet.contains(eventName)) {
                    stickyEvents.remove(eventName);
                    break;
                }
            }
        }
    }

    public static void registerReceiver(EventReceiver<?> receiver,
                                        String... eventNames) {
        @SuppressWarnings("unchecked")
        EventReceiver<Object> rec = (EventReceiver<Object>) receiver;
        boolean allEvents = (eventNames.length == 0);
        if (allEvents) {
            for (String name : stickyEvents.keySet()) {
                notifyReceiver(rec, name, stickyEvents.get(name));
            }
            receiversForEventName(ALL).put(rec, Boolean.FALSE);
        } else {
            for (String name : eventNames) {
                Object data = stickyEvents.get(name);
                if (data != null) {
                    notifyReceiver(rec, name, data);
                }
            }
            for (String action : eventNames) {
                receiversForEventName(action).put(rec, Boolean.FALSE);
            }
        }
    }

    public static void unregisterReceiver(EventReceiver<?> receiver) {
        receiversForEventName(ALL).remove(receiver);
        for (String eventName : eventNameToReceivers.keySet()) {
            ConcurrentHashMap<EventReceiver<Object>, Boolean> receivers = eventNameToReceivers
                    .get(eventName);
            receivers.remove(receiver);
            if (receivers.isEmpty()) {
                eventNameToReceivers.remove(eventName);
            }
        }
    }

    public static void registerAnnotatedReceiver(Object obj) {
        MethodSpec<ReceiveEventsAnn>[] specs = ClassSpecRegistry.getReceiveEventsSpecs(obj
                .getClass());
        for (MethodSpec<ReceiveEventsAnn> spec : specs) {
            registerReceiver(new ReflectiveReceiver(obj, spec), spec.ann.names);
        }
    }

    public static void unregisterAnnotatedReceiver(Object obj) {
        for (ConcurrentHashMap<EventReceiver<Object>, Boolean> receivers : eventNameToReceivers
                .values()) {
            for (EventReceiver<Object> receiver : receivers.keySet()) {
                if (receiver instanceof ReflectiveReceiver) {
                    if (obj == ((ReflectiveReceiver) receiver).objectRef.get()) {
                        receivers.remove(receiver);
                    }
                }
            }
        }
    }

    private static ConcurrentHashMap<EventReceiver<Object>, Boolean> receiversForEventName(
            String name) {
        ConcurrentHashMap<EventReceiver<Object>, Boolean> map = eventNameToReceivers
                .get(name);
        if (map == null) {
            map = new ConcurrentHashMap<EventReceiver<Object>, Boolean>();
            eventNameToReceivers.put(name, map);
        }
        return map;
    }

    private static void notifyReceiver(EventReceiver<Object> receiver,
                                       String event, Object data) {
        try {
            receiver.onEvent(event, data);
        } catch (IllegalArgumentException e) {
            String info = String.format("Failed to deliver event %s to %s: %s.", event,
                    receiver.getClass().getName(), e.getMessage());
            Log.wtf(TAG, info);
        } catch (Exception e) {
            Log.wtf(TAG, e);
            Log.wtf(TAG, "Receiver unregistered.");
            unregisterReceiver(receiver);
        }

    }

    private static void runOnUiThread(Runnable r) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        boolean success = handler.post(r);
        // a hack
        while (!success) {
            handler = null;
            runOnUiThread(r);
        }
    }

    private static Handler handler;

    private static class PostEventRunnable implements Runnable {

        private final String name;
        private final Object data;

        public PostEventRunnable(String name, Object data) {
            this.name = name;
            this.data = data;
        }

        @Override
        public void run() {
            HashSet<EventReceiver<Object>> receivers = new HashSet<EventReceiver<Object>>();
            receivers.addAll(receiversForEventName(ALL).keySet());
            receivers.addAll(receiversForEventName(name).keySet());
            for (EventReceiver<Object> rec : receivers) {
                notifyReceiver(rec, name, data);
            }
        }
    }

    private static class ReflectiveReceiver implements EventReceiver<Object> {

        final WeakReference<Object>        objectRef;
        final MethodSpec<ReceiveEventsAnn> spec;

        ReflectiveReceiver(Object object, MethodSpec<ReceiveEventsAnn> spec) {
            objectRef = new WeakReference<Object>(object);
            this.spec = spec;
        }

        @Override
        public void onEvent(String name, Object data) {
            try {
                Object obj = objectRef.get();
                switch (spec.paramTypes.length) {
                    case 0:
                        spec.method.invoke(obj);
                        break;
                    case 1:
                        if (spec.paramTypes[0] == String.class) {
                            spec.method.invoke(obj, name);
                        } else {
                            spec.method.invoke(obj, data);
                        }
                        break;
                    default:
                        spec.method.invoke(obj, name, data);
                }
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

    }

}
