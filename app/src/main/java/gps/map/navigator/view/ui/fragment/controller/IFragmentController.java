package gps.map.navigator.view.ui.fragment.controller;

public interface IFragmentController<T> {

    void openFragment(IFragment<T> fragment);

    void backToLastFragment();
}
