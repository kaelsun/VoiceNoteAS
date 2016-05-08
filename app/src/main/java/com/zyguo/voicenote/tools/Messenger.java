package com.zyguo.voicenote.tools;

import android.os.Handler;

import java.util.HashMap;

/**
 * Created by zyguo on 2016/5/7.
 * Class which contains handlers of components, stored in a HashMap<br/>
 */
public class Messenger {
    private static Messenger self;

    private HashMap<String, Handler> mMap = new HashMap<String, Handler>();

    protected void Messenger() {}

    public static Messenger getInstance() {
        if(self == null)
            self = new Messenger();
        return self;
    }

    /**
     * Register the handler of a specific component.
     * @param name Should be the name of the component class.
     * @param handler Handler of the component.
     */
    public synchronized void registerHandler(String name, Handler handler) {
        mMap.put(name, handler);
    }

    /**
     * Get the handler of a specific component.
     * @param name Name of the component class.
     * @return The handler of the component.Or null if not found.
     */
    public synchronized Handler getHandlerByName(String name) {
        Handler handler = mMap.get(name);
        return handler;
    }

    /**
     * Remove the handler by id.
     * @param name name of the class that the handler belong to.
     */
    public synchronized  void unRegisterHandler(String name) {
        mMap.remove(name);
    }

    /**
     * Release the map especially.
     */
    public void release() {
        if(mMap != null)
            mMap.clear();
        mMap = null;
        self = null;
    }
}
