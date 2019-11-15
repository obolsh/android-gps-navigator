package gps.map.navigator.policy;

public interface Legal {

    String getTitle();

    String getLegalUrl();

    String getPositiveText();

    String getNegativeText();

    LegalListener getLegalListener();
}
