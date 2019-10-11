package gps.map.navigator.view.ui.fragment.controller;

public interface IFragment<T> {

    T getInstance();

    String getFragmentTag();
}
