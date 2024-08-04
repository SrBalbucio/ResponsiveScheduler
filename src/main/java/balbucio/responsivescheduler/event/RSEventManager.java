package balbucio.responsivescheduler.event;

import balbucio.responsivescheduler.ResponsiveScheduler;
import balbucio.responsivescheduler.event.impl.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSEventManager {

    private List<Listener> listeners = new ArrayList<>();

    public RSEventManager() {
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void unregisterAll() {
        listeners.clear();
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    public void sendEvent(Event e) {
        try {
            listeners.forEach(l -> {
                for (Method m : l.getClass().getDeclaredMethods()) {
                    if (Arrays.stream(m.getParameterTypes()).anyMatch(cz -> e.getClass().equals(cz))) {
                        Object[] obj = new Object[m.getParameterTypes().length];
                        for (int i = 0; i < m.getParameterTypes().length; i++) {
                            Class<?> clazz = m.getParameterTypes()[i];
                            if (clazz.equals(e.getClass())) {
                                obj[i] = e;
                            } else {
                                obj[i] = getObjectFromClazz(clazz);
                            }
                        }
                        try {
                            m.invoke(l, obj);
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Object getObjectFromClazz(Class<?> clazz) {
        if (clazz.equals(ResponsiveScheduler.class)) {
            return ResponsiveScheduler.getInstance();
        } else if (clazz.equals(RSEventManager.class)) {
            return this;
        } else {
            return null;
        }
    }
}
