package gps.map.navigator.policy;

import android.app.Activity;

import androidx.annotation.Nullable;

import gps.map.navigator.policy.tos.PolicyLegal;
import gps.map.navigator.policy.tos.TosLegal;

public class DialogFactory {

    @Nullable
    private Activity activity;
    @Nullable
    private LegalDialog dialog;

    public DialogFactory(@Nullable Activity activity) {
        this.activity = activity;
    }

    public DialogFactory buildPolicy() {
        dialog = new LegalDialog(activity, getPolicyLegal());
        return this;
    }

    public DialogFactory buildTerms() {
        dialog = new LegalDialog(activity, getTermsLegal());
        return this;
    }

    public void showWithChoice() {
        if (dialog != null) {
            dialog.disableButtons(true);
            dialog.show();
        }
    }

    public void show() {
        if (dialog != null) {
            dialog.disableButtons(false);
            dialog.show();
        }
    }

    private Legal getPolicyLegal() {
        return new PolicyLegal(activity, new LegalListener() {
            @Override
            public void onAgreed() {

            }

            @Override
            public void onDeclined() {

            }
        });
    }

    private Legal getTermsLegal() {
        return new TosLegal(activity, new LegalListener() {
            @Override
            public void onAgreed() {

            }

            @Override
            public void onDeclined() {

            }
        });
    }
}
