package demo.settings;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.impl.data.MapSettingImpl;

public class DefaultMapSettings extends MapSettingImpl {

    public DefaultMapSettings() {
        setMapType(MapType.NORMAL_DAY);
        setId("default_map_settings");
    }
}
