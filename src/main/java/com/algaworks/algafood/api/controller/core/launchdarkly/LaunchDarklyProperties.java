package com.algaworks.algafood.api.controller.core.launchdarkly;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
@Component
@ConfigurationProperties("algafood.launchdarkly")
@Getter
@Setter
public class LaunchDarklyProperties {

    @NotNull
    private String sdkKey;

}
