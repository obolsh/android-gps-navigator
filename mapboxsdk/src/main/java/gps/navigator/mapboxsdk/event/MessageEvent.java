package gps.navigator.mapboxsdk.event;


public class MessageEvent {

    public static final String TYPE_SHOW_DEVICE_LOCATION = "show_device_location";
    public static final String TYPE_SHOW_LAST_LOCATION = "show_last_location";
    public static final String TYPE_CHANGE_MAP_SETTINGS = "change_map_settings";

    private String messageId;

    public MessageEvent(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
