package gps.map.navigator.model.interfaces;

import java.io.Serializable;

public interface IDecorCache extends Serializable {

    boolean isShowMeOnMapActive();

    void setShowMeOnMapActive(boolean showMeOnMapActive);

    boolean isFloatingActionButtonActive();

    void setFloatingActionButtonActive(boolean floatingActionButtonActive);

    boolean isButtomAppBarActive();

    void setButtomAppBarActive(boolean buttomAppBarActive);

    int getFloatingActionButtonAlignent();

    void setFloatingActionButtonAlignent(int floatingActionButtonAlignent);
}
