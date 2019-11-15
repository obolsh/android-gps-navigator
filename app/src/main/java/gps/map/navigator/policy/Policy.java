package gps.map.navigator.policy;

public interface Policy {

    boolean policyAndTermsAccepted();

    void markPolicyAndTermsAsAccepted();
}
