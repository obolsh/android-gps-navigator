package gps.navigator.mapboxsdk.event;

import gps.map.navigator.model.interfaces.MapSetting;

public class ChangeSettingsEvent extends MessageEvent {
    private MapSetting setting;

    public ChangeSettingsEvent(String messageId, MapSetting setting) {
        super(messageId);
        this.setting = setting;
    }

    public MapSetting getSetting() {
        return setting;
    }
}
