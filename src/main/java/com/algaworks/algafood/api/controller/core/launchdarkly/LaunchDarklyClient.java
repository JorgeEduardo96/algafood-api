package com.algaworks.algafood.api.controller.core.launchdarkly;

import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.server.LDClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LaunchDarklyClient {

    private final LDClient ldClient;

    public String getFeatureFlagStringValue(String featureFlagKey) {
        return ldClient.stringVariation(featureFlagKey,
                LDContext.builder("example-user-key")
                        .name("Jorge").build(), "ses");
    }

}
