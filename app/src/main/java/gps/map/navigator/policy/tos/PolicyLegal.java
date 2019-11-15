package gps.map.navigator.policy.tos;

import android.content.Context;

import gps.map.navigator.R;
import gps.map.navigator.policy.Legal;
import gps.map.navigator.policy.LegalListener;

public class PolicyLegal implements Legal {
    private Context context;
    private LegalListener legalListener;

    public PolicyLegal(Context context, LegalListener legalListener) {
        this.context = context;
        this.legalListener = legalListener;
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.policy_title);
    }

    @Override
    public String getLegalUrl() {
        return "file:///android_asset/privacy_policy.html";
    }

    @Override
    public String getPositiveText() {
        return context.getString(R.string.positive_text);
    }

    @Override
    public String getNegativeText() {
        return context.getString(R.string.negative_text);
    }

    @Override
    public LegalListener getLegalListener() {
        return legalListener;
    }
}
