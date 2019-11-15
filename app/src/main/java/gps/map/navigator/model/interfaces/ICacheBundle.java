package gps.map.navigator.model.interfaces;

import android.os.Bundle;

public interface ICacheBundle {

    IDecorCache getDecorCache();

    String getActiveFragmentTag();

    void readBundle(Bundle bundle);
}
