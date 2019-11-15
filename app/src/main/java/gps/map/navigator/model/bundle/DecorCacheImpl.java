package gps.map.navigator.model.bundle;

import gps.map.navigator.model.interfaces.IDecorCache;

public class DecorCacheImpl implements IDecorCache {

    private boolean showMeOnMapActive;

    private boolean floatingActionButtonActive;

    private boolean buttomAppBarActive;

    private int floatingActionButtonAlignent;

    public DecorCacheImpl(boolean showMeOnMapActive, boolean floatingActionButtonActive,
                          boolean buttomAppBarActive, int floatingActionButtonAlignent) {
        super();
        this.showMeOnMapActive = showMeOnMapActive;
        this.floatingActionButtonActive = floatingActionButtonActive;
        this.buttomAppBarActive = buttomAppBarActive;
        this.floatingActionButtonAlignent = floatingActionButtonAlignent;
    }

    public DecorCacheImpl() {
        super();
    }

    @Override
    public boolean isShowMeOnMapActive() {
        return showMeOnMapActive;
    }

    @Override
    public void setShowMeOnMapActive(boolean showMeOnMapActive) {
        this.showMeOnMapActive = showMeOnMapActive;
    }

    @Override
    public boolean isFloatingActionButtonActive() {
        return floatingActionButtonActive;
    }

    @Override
    public void setFloatingActionButtonActive(boolean floatingActionButtonActive) {
        this.floatingActionButtonActive = floatingActionButtonActive;
    }

    @Override
    public boolean isButtomAppBarActive() {
        return buttomAppBarActive;
    }

    @Override
    public void setButtomAppBarActive(boolean buttomAppBarActive) {
        this.buttomAppBarActive = buttomAppBarActive;
    }

    @Override
    public int getFloatingActionButtonAlignent() {
        return floatingActionButtonAlignent;
    }

    @Override
    public void setFloatingActionButtonAlignent(int floatingActionButtonAlignent) {
        this.floatingActionButtonAlignent = floatingActionButtonAlignent;
    }

    @Override
    public String toString() {
        return "DecorCacheImpl{" +
                "showMeOnMapActive=" + showMeOnMapActive +
                ", floatingActionButtonActive=" + floatingActionButtonActive +
                ", buttomAppBarActive=" + buttomAppBarActive +
                ", floatingActionButtonAlignent=" + floatingActionButtonAlignent +
                '}';
    }
}
