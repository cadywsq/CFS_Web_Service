package edu.cmu.webapp.task8.resource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class Action {
    public abstract String getName();
    public abstract Object perform(HttpServletRequest request);
    private static Map<String, Action> hash = new HashMap<String, Action>();
    
    public static void add(Action action) {
        synchronized (hash) {
            hash.put(action.getName(), action);
        }
    }
    public static Object perform(String name) {
        Action action;
        synchronized (hash) {
            action = hash.get(name);
        }
        
        if (action == null) {
            return null;
        }
        return action.perform(name);
    }
}
