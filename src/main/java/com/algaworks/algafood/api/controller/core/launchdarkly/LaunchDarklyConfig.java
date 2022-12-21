package com.algaworks.algafood.api.controller.core.launchdarkly;

import com.launchdarkly.sdk.server.LDClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LaunchDarklyConfig {

    private final LaunchDarklyProperties launchDarklyProperties;

    @Bean
    public LDClient getLaunchDarklyClient() {
        return new LDClient(launchDarklyProperties.getSdkKey());
    }

}
