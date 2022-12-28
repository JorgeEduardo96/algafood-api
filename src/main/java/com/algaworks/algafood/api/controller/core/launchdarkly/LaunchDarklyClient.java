package com.algaworks.algafood.api.controller.core.launchdarkly;

import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.LDValue;
import com.launchdarkly.sdk.server.LDClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LaunchDarklyClient {

    private final LDClient ldClient;

    public String getFeatureFlagStringValue(String featureFlagKey) {
        return ldClient.stringVariation(featureFlagKey, getContext(), "ses");
    }

    public boolean getFeatureFlagBooleanValue(String featureFlagKey) {
        return ldClient.boolVariation(featureFlagKey, getContext(), false);
    }

    private LDContext getContext() {
        return LDContext.builder("jorge-test-user-key")
                .set("firstName", "Jorge")
                .set("lastName", "Junior")
                .set("email", "jeduardo@somalogic.com")
                .set("groups",
                        LDValue.buildArray().add("Somalogic").build())
                .build();
    }


}
