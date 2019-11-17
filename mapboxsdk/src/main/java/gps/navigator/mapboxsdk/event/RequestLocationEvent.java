package gps.navigator.mapboxsdk.event;

import gps.map.navigator.view.interfaces.IPlaceListener;

public class RequestLocationEvent extends MessageEvent {
    private IPlaceListener listener;

    public RequestLocationEvent(String messageId, IPlaceListener listener) {
        super(messageId);
        this.listener = listener;
    }

    public IPlaceListener getListener() {
        return listener;
    }
}
